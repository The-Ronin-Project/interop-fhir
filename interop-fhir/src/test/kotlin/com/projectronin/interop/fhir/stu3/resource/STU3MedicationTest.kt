package com.projectronin.interop.fhir.stu3.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.datatype.primitive.Url
import com.projectronin.interop.fhir.r4.resource.Batch
import com.projectronin.interop.fhir.r4.resource.Ingredient
import com.projectronin.interop.fhir.r4.resource.UnknownResource
import com.projectronin.interop.fhir.r4.valueset.MedicationStatus
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

@Suppress("ktlint:standard:max-line-length")
class STU3MedicationTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val medication =
            STU3Medication(
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("RoninSTU3Medication")),
                    ),
                implicitRules = Uri("implicit-rules"),
                language = Code("en-US"),
                text =
                    Narrative(
                        status = NarrativeStatus.GENERATED.asCode(),
                        div = FHIRString("div"),
                    ),
                contained =
                    listOf(
                        STU3UnknownResource(
                            resourceType = "Location",
                            id = Id("1234"),
                            otherData = mapOf("name" to "Contained Location"),
                        ),
                    ),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                code = CodeableConcept(text = FHIRString("Med code")),
                status = MedicationStatus.ACTIVE.asCode(),
                isBrand = FHIRBoolean.FALSE,
                isOverTheCounter = FHIRBoolean.FALSE,
                manufacturer = Reference(display = FHIRString("Manufacturer reference")),
                form = CodeableConcept(text = FHIRString("Med form")),
                ingredient =
                    listOf(
                        STU3MedicationIngredient(
                            item =
                                DynamicValue(
                                    DynamicValueType.CODEABLE_CONCEPT,
                                    CodeableConcept(text = FHIRString("Med ingredient")),
                                ),
                        ),
                    ),
                `package` =
                    STU3MedicationPackage(
                        batch = listOf(STU3MedicationPackageBatch(lotNumber = FHIRString("Batch lot"))),
                    ),
                image = listOf(Attachment(url = Url("attachment"))),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medication)

        val expectedJson =
            """
            {
              "resourceType" : "Medication",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninSTU3Medication" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "text" : {
                "status" : "generated",
                "div" : "div"
              },
              "contained" : [ {
                "resourceType" : "Location",
                "id" : "1234",
                "name" : "Contained Location"
              } ],
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "code" : {
                "text" : "Med code"
              },
              "status" : "active",
              "isBrand" : false,
              "isOverTheCounter" : false,
              "manufacturer" : {
                "display" : "Manufacturer reference"
              },
              "form" : {
                "text" : "Med form"
              },
              "ingredient" : [ {
                "itemCodeableConcept" : {
                  "text" : "Med ingredient"
                }
              } ],
              "package" : {
                "batch" : [ {
                  "lotNumber" : "Batch lot"
                } ]
              },
              "image" : [ {
                "url" : "attachment"
              } ]
            }
            """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedSTU3Medication = objectMapper.readValue<STU3Medication>(json)
        assertEquals(medication, deserializedSTU3Medication)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val medication = STU3Medication()
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medication)

        val expectedJson =
            """
            {
              "resourceType" : "Medication"
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "resourceType" : "Medication"
            }
            """.trimIndent()
        val medication = objectMapper.readValue<STU3Medication>(json)

        assertEquals("Medication", medication.resourceType)
        assertNull(medication.id)
        assertNull(medication.meta)
        assertNull(medication.implicitRules)
        assertNull(medication.language)
        assertNull(medication.text)
        assertEquals(listOf<STU3Resource<*>>(), medication.contained)
        assertEquals(listOf<Extension>(), medication.extension)
        assertEquals(listOf<Extension>(), medication.modifierExtension)
        assertNull(medication.code)
        assertNull(medication.status)
        assertNull(medication.isBrand)
        assertNull(medication.isOverTheCounter)
        assertNull(medication.manufacturer)
        assertNull(medication.form)
        assertEquals(listOf<STU3MedicationIngredient>(), medication.ingredient)
        assertNull(medication.`package`)
        assertEquals(listOf<Attachment>(), medication.image)
    }

    @Test
    fun `transforms full model to R4`() {
        val stu3Medication =
            STU3Medication(
                id = Id("12345"),
                meta =
                    Meta(
                        profile = listOf(Canonical("RoninSTU3Medication")),
                    ),
                implicitRules = Uri("implicit-rules"),
                language = Code("en-US"),
                text =
                    Narrative(
                        status = NarrativeStatus.GENERATED.asCode(),
                        div = FHIRString("div"),
                    ),
                contained =
                    listOf(
                        STU3UnknownResource(
                            resourceType = "Location",
                            id = Id("1234"),
                            otherData = mapOf("name" to "Contained Location"),
                        ),
                    ),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                code = CodeableConcept(text = FHIRString("Med code")),
                status = MedicationStatus.ACTIVE.asCode(),
                isBrand = FHIRBoolean.FALSE,
                isOverTheCounter = FHIRBoolean.FALSE,
                manufacturer = Reference(display = FHIRString("Manufacturer reference")),
                form = CodeableConcept(text = FHIRString("Med form")),
                ingredient =
                    listOf(
                        STU3MedicationIngredient(
                            item =
                                DynamicValue(
                                    DynamicValueType.CODEABLE_CONCEPT,
                                    CodeableConcept(text = FHIRString("Med ingredient")),
                                ),
                        ),
                    ),
                `package` =
                    STU3MedicationPackage(
                        container = CodeableConcept(text = FHIRString("container")),
                        content =
                            listOf(
                                STU3MedicationPackageContent(
                                    item =
                                        DynamicValue(
                                            DynamicValueType.REFERENCE,
                                            Reference(reference = FHIRString("Medication/12345")),
                                        ),
                                    amount = SimpleQuantity(value = Decimal(BigDecimal.ONE)),
                                ),
                            ),
                        batch = listOf(STU3MedicationPackageBatch(lotNumber = FHIRString("Batch lot"))),
                    ),
                image = listOf(Attachment(url = Url("attachment"))),
            )

        val r4Extensions =
            listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                ),
                Extension(
                    url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.isBrand"),
                    value = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.FALSE),
                ),
                Extension(
                    url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-MedicationOTC"),
                    value = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.FALSE),
                ),
                Extension(
                    url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.container"),
                    value =
                        DynamicValue(
                            DynamicValueType.CODEABLE_CONCEPT,
                            CodeableConcept(text = FHIRString("container")),
                        ),
                ),
                Extension(
                    url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content"),
                    extension =
                        listOf(
                            Extension(
                                url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content"),
                                value =
                                    DynamicValue(
                                        DynamicValueType.REFERENCE,
                                        Reference(reference = FHIRString("Medication/12345")),
                                    ),
                            ),
                            Extension(
                                url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content.amount"),
                                value =
                                    DynamicValue(
                                        DynamicValueType.QUANTITY,
                                        SimpleQuantity(value = Decimal(BigDecimal.ONE)),
                                    ),
                            ),
                        ),
                ),
            )

        val r4Medication = stu3Medication.transformToR4()
        assertEquals(stu3Medication.id, r4Medication.id)
        assertEquals(stu3Medication.meta, r4Medication.meta)
        assertEquals(stu3Medication.implicitRules, r4Medication.implicitRules)
        assertEquals(stu3Medication.language, r4Medication.language)
        assertEquals(stu3Medication.text, r4Medication.text)
        assertEquals(
            listOf(
                UnknownResource(
                    resourceType = "Location",
                    id = Id("1234"),
                    otherData = mapOf("name" to "Contained Location"),
                ),
            ),
            r4Medication.contained,
        )
        assertEquals(r4Extensions, r4Medication.extension)
        assertEquals(stu3Medication.modifierExtension, r4Medication.modifierExtension)
        assertEquals(listOf<Identifier>(), r4Medication.identifier)
        assertEquals(stu3Medication.code, r4Medication.code)
        assertEquals(stu3Medication.status, r4Medication.status)
        assertEquals(stu3Medication.manufacturer, r4Medication.manufacturer)
        assertEquals(stu3Medication.form, r4Medication.form)
        assertNull(r4Medication.amount)
        assertEquals(
            listOf(
                Ingredient(
                    item =
                        DynamicValue(
                            DynamicValueType.CODEABLE_CONCEPT,
                            CodeableConcept(text = FHIRString("Med ingredient")),
                        ),
                ),
            ),
            r4Medication.ingredient,
        )
        assertEquals(Batch(lotNumber = FHIRString("Batch lot")), r4Medication.batch)
    }

    @Test
    fun `transforms minimum model to R4`() {
        val stu3Medication = STU3Medication()

        val r4Medication = stu3Medication.transformToR4()
        assertEquals(stu3Medication.id, r4Medication.id)
        assertEquals(stu3Medication.meta, r4Medication.meta)
        assertEquals(stu3Medication.implicitRules, r4Medication.implicitRules)
        assertEquals(stu3Medication.language, r4Medication.language)
        assertEquals(stu3Medication.text, r4Medication.text)
        assertEquals(stu3Medication.contained, r4Medication.contained)
        assertEquals(stu3Medication.extension, r4Medication.extension)
        assertEquals(stu3Medication.modifierExtension, r4Medication.modifierExtension)
        assertEquals(listOf<Identifier>(), r4Medication.identifier)
        assertEquals(stu3Medication.code, r4Medication.code)
        assertEquals(stu3Medication.status, r4Medication.status)
        assertEquals(stu3Medication.manufacturer, r4Medication.manufacturer)
        assertEquals(stu3Medication.form, r4Medication.form)
        assertNull(r4Medication.amount)
        assertEquals(listOf<Ingredient>(), r4Medication.ingredient)
        assertNull(r4Medication.batch)
    }

    @Test
    fun `transforms with no package container to R4`() {
        val stu3Medication =
            STU3Medication(
                `package` =
                    STU3MedicationPackage(
                        content =
                            listOf(
                                STU3MedicationPackageContent(
                                    item =
                                        DynamicValue(
                                            DynamicValueType.REFERENCE,
                                            Reference(reference = FHIRString("Medication/12345")),
                                        ),
                                    amount = SimpleQuantity(value = Decimal(BigDecimal.ONE)),
                                ),
                            ),
                        batch = listOf(STU3MedicationPackageBatch(lotNumber = FHIRString("Batch lot"))),
                    ),
            )

        val r4Extensions =
            listOf(
                Extension(
                    url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content"),
                    extension =
                        listOf(
                            Extension(
                                url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content"),
                                value =
                                    DynamicValue(
                                        DynamicValueType.REFERENCE,
                                        Reference(reference = FHIRString("Medication/12345")),
                                    ),
                            ),
                            Extension(
                                url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content.amount"),
                                value =
                                    DynamicValue(
                                        DynamicValueType.QUANTITY,
                                        SimpleQuantity(value = Decimal(BigDecimal.ONE)),
                                    ),
                            ),
                        ),
                ),
            )

        val r4Medication = stu3Medication.transformToR4()
        assertEquals(stu3Medication.id, r4Medication.id)
        assertEquals(stu3Medication.meta, r4Medication.meta)
        assertEquals(stu3Medication.implicitRules, r4Medication.implicitRules)
        assertEquals(stu3Medication.language, r4Medication.language)
        assertEquals(stu3Medication.text, r4Medication.text)
        assertEquals(stu3Medication.contained, r4Medication.contained)
        assertEquals(r4Extensions, r4Medication.extension)
        assertEquals(stu3Medication.modifierExtension, r4Medication.modifierExtension)
        assertEquals(listOf<Identifier>(), r4Medication.identifier)
        assertEquals(stu3Medication.code, r4Medication.code)
        assertEquals(stu3Medication.status, r4Medication.status)
        assertEquals(stu3Medication.manufacturer, r4Medication.manufacturer)
        assertEquals(stu3Medication.form, r4Medication.form)
        assertNull(r4Medication.amount)
        assertEquals(listOf<Ingredient>(), r4Medication.ingredient)
        assertEquals(Batch(lotNumber = FHIRString("Batch lot")), r4Medication.batch)
    }

    @Test
    fun `transforms with no package content amount to R4`() {
        val stu3Medication =
            STU3Medication(
                `package` =
                    STU3MedicationPackage(
                        content =
                            listOf(
                                STU3MedicationPackageContent(
                                    item =
                                        DynamicValue(
                                            DynamicValueType.REFERENCE,
                                            Reference(reference = FHIRString("Medication/12345")),
                                        ),
                                ),
                            ),
                        batch = listOf(STU3MedicationPackageBatch(lotNumber = FHIRString("Batch lot"))),
                    ),
            )

        val r4Extensions =
            listOf(
                Extension(
                    url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content"),
                    extension =
                        listOf(
                            Extension(
                                url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content"),
                                value =
                                    DynamicValue(
                                        DynamicValueType.REFERENCE,
                                        Reference(reference = FHIRString("Medication/12345")),
                                    ),
                            ),
                        ),
                ),
            )

        val r4Medication = stu3Medication.transformToR4()
        assertEquals(stu3Medication.id, r4Medication.id)
        assertEquals(stu3Medication.meta, r4Medication.meta)
        assertEquals(stu3Medication.implicitRules, r4Medication.implicitRules)
        assertEquals(stu3Medication.language, r4Medication.language)
        assertEquals(stu3Medication.text, r4Medication.text)
        assertEquals(stu3Medication.contained, r4Medication.contained)
        assertEquals(r4Extensions, r4Medication.extension)
        assertEquals(stu3Medication.modifierExtension, r4Medication.modifierExtension)
        assertEquals(listOf<Identifier>(), r4Medication.identifier)
        assertEquals(stu3Medication.code, r4Medication.code)
        assertEquals(stu3Medication.status, r4Medication.status)
        assertEquals(stu3Medication.manufacturer, r4Medication.manufacturer)
        assertEquals(stu3Medication.form, r4Medication.form)
        assertNull(r4Medication.amount)
        assertEquals(listOf<Ingredient>(), r4Medication.ingredient)
        assertEquals(Batch(lotNumber = FHIRString("Batch lot")), r4Medication.batch)
    }
}

class STU3MedicationIngredientTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val ingredient =
            STU3MedicationIngredient(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                item = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = FHIRString("Medication/12345"))),
                isActive = FHIRBoolean.TRUE,
                amount =
                    Ratio(
                        numerator = Quantity(value = Decimal(BigDecimal.ONE)),
                    ),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ingredient)

        val expectedJson =
            """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "itemReference" : {
                "reference" : "Medication/12345"
              },
              "isActive" : true,
              "amount" : {
                "numerator" : {
                  "value" : 1
                }
              }
            }
            """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedIngredient = objectMapper.readValue<STU3MedicationIngredient>(json)
        assertEquals(ingredient, deserializedIngredient)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val ingredient =
            STU3MedicationIngredient(
                item = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = FHIRString("Medication/12345"))),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ingredient)

        val expectedJson =
            """
            {
              "itemReference" : {
                "reference" : "Medication/12345"
              }
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "itemReference" : {
                "reference" : "Medication/12345"
              }
            }
            """.trimIndent()
        val ingredient = objectMapper.readValue<STU3MedicationIngredient>(json)

        assertNull(ingredient.id)
        assertEquals(listOf<Extension>(), ingredient.extension)
        assertEquals(listOf<Extension>(), ingredient.modifierExtension)
        assertEquals(
            DynamicValue(DynamicValueType.REFERENCE, Reference(reference = FHIRString("Medication/12345"))),
            ingredient.item,
        )
        assertNull(ingredient.isActive)
        assertNull(ingredient.amount)
    }

    @Test
    fun `transforms to R4`() {
        val stu3Ingredient =
            STU3MedicationIngredient(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                item = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = FHIRString("Medication/12345"))),
                isActive = FHIRBoolean.TRUE,
                amount =
                    Ratio(
                        numerator = Quantity(value = Decimal(BigDecimal.ONE)),
                    ),
            )

        val r4Ingredient = stu3Ingredient.transformToR4()
        assertEquals(stu3Ingredient.id, r4Ingredient.id)
        assertEquals(stu3Ingredient.extension, r4Ingredient.extension)
        assertEquals(stu3Ingredient.modifierExtension, r4Ingredient.modifierExtension)
        assertEquals(stu3Ingredient.item, r4Ingredient.item)
        assertEquals(stu3Ingredient.isActive, r4Ingredient.isActive)
        assertEquals(stu3Ingredient.amount, r4Ingredient.strength)
    }
}

class STU3MedicationPackageTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val medPackage =
            STU3MedicationPackage(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                container = CodeableConcept(text = FHIRString("container")),
                content =
                    listOf(
                        STU3MedicationPackageContent(
                            item =
                                DynamicValue(
                                    DynamicValueType.REFERENCE,
                                    Reference(reference = FHIRString("Medication/12345")),
                                ),
                        ),
                    ),
                batch =
                    listOf(
                        STU3MedicationPackageBatch(
                            lotNumber = FHIRString("1"),
                        ),
                    ),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medPackage)

        val expectedJson =
            """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "container" : {
                "text" : "container"
              },
              "content" : [ {
                "itemReference" : {
                  "reference" : "Medication/12345"
                }
              } ],
              "batch" : [ {
                "lotNumber" : "1"
              } ]
            }
            """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedPackage = objectMapper.readValue<STU3MedicationPackage>(json)
        assertEquals(medPackage, deserializedPackage)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val medPackage =
            STU3MedicationPackage(
                container = CodeableConcept(text = FHIRString("container")),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medPackage)

        val expectedJson =
            """
            {
              "container" : {
                "text" : "container"
              }
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
              "container" : {
                "text" : "container"
              }
            }
            """.trimIndent()
        val medPackage = objectMapper.readValue<STU3MedicationPackage>(json)

        assertNull(medPackage.id)
        assertEquals(listOf<Extension>(), medPackage.extension)
        assertEquals(listOf<Extension>(), medPackage.modifierExtension)
        assertEquals(
            CodeableConcept(text = FHIRString("container")),
            medPackage.container,
        )
        assertEquals(listOf<STU3MedicationPackageContent>(), medPackage.content)
        assertEquals(listOf<STU3MedicationIngredient>(), medPackage.batch)
    }

    @Test
    fun `transform to R4 throws exception`() {
        val stu3Package =
            STU3MedicationPackage(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                container = CodeableConcept(text = FHIRString("container")),
                content =
                    listOf(
                        STU3MedicationPackageContent(
                            item =
                                DynamicValue(
                                    DynamicValueType.REFERENCE,
                                    Reference(reference = FHIRString("Medication/12345")),
                                ),
                        ),
                    ),
                batch =
                    listOf(
                        STU3MedicationPackageBatch(
                            lotNumber = FHIRString("1"),
                        ),
                    ),
            )

        assertThrows<IllegalStateException> { stu3Package.transformToR4() }
    }
}

class STU3MedicationPackageContentTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val content =
            STU3MedicationPackageContent(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                item = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = FHIRString("Medication/12345"))),
                amount = SimpleQuantity(value = Decimal(BigDecimal.TEN)),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(content)

        val expectedJson =
            """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "itemReference" : {
                "reference" : "Medication/12345"
              },
              "amount" : {
                "value" : 10
              }
            }
            """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedContent = objectMapper.readValue<STU3MedicationPackageContent>(json)
        assertEquals(content, deserializedContent)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val content =
            STU3MedicationPackageContent(
                item = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = FHIRString("Medication/67890"))),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(content)

        val expectedJson =
            """
            {
              "itemReference" : {
                "reference" : "Medication/67890"
              }
            }
            """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            {
               "itemReference" : {
                 "reference" : "Medication/67890"
               }
            }
            """.trimIndent()
        val content = objectMapper.readValue<STU3MedicationPackageContent>(json)

        assertNull(content.id)
        assertEquals(listOf<Extension>(), content.extension)
        assertEquals(listOf<Extension>(), content.modifierExtension)
        assertEquals(
            DynamicValue(DynamicValueType.REFERENCE, Reference(reference = FHIRString("Medication/67890"))),
            content.item,
        )
        assertNull(content.amount)
    }

    @Test
    fun `transform to R4 throws exception`() {
        val stu3Content =
            STU3MedicationPackageContent(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                item = DynamicValue(DynamicValueType.REFERENCE, Reference(reference = FHIRString("Medication/12345"))),
                amount = SimpleQuantity(value = Decimal(BigDecimal.TEN)),
            )

        assertThrows<IllegalStateException> { stu3Content.transformToR4() }
    }
}

class STU3MedicationPackageBatchTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val batch =
            STU3MedicationPackageBatch(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                lotNumber = FHIRString("lot-number"),
                expirationDate = DateTime("2025"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(batch)

        val expectedJson =
            """
            {
              "id" : "12345",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "lotNumber" : "lot-number",
              "expirationDate" : "2025"
            }
            """.trimIndent()

        assertEquals(expectedJson, json)

        val deserializedBatch = objectMapper.readValue<STU3MedicationPackageBatch>(json)
        assertEquals(batch, deserializedBatch)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val batch = STU3MedicationPackageBatch()
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(batch)

        val expectedJson = "{ }"
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = "{ }"
        val batch = objectMapper.readValue<STU3MedicationPackageBatch>(json)

        assertNull(batch.id)
        assertEquals(listOf<Extension>(), batch.extension)
        assertEquals(listOf<Extension>(), batch.modifierExtension)
        assertNull(batch.lotNumber)
        assertNull(batch.expirationDate)
    }

    @Test
    fun `transforms to R4`() {
        val stu3Batch =
            STU3MedicationPackageBatch(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                modifierExtension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/modifier-extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                lotNumber = FHIRString("lot-number"),
                expirationDate = DateTime("2025"),
            )

        val r4Batch = stu3Batch.transformToR4()
        assertEquals(stu3Batch.id, r4Batch.id)
        assertEquals(stu3Batch.extension, r4Batch.extension)
        assertEquals(stu3Batch.modifierExtension, r4Batch.modifierExtension)
        assertEquals(stu3Batch.lotNumber, r4Batch.lotNumber)
        assertEquals(stu3Batch.expirationDate, r4Batch.expirationDate)
    }
}
