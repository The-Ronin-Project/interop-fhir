package com.projectronin.interop.fhir.stu3.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.r4.resource.Batch
import com.projectronin.interop.fhir.r4.resource.Ingredient
import com.projectronin.interop.fhir.stu3.element.STU3BackboneElement
import com.projectronin.interop.fhir.r4.resource.Medication as R4Medication

@JsonSerialize(using = STU3MedicationSerializer::class)
@JsonDeserialize(using = STU3MedicationDeserializer::class)
@JsonTypeName("Medication")
data class STU3Medication(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<STU3Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val code: CodeableConcept? = null,
    val status: Code? = null,
    val isBrand: FHIRBoolean? = null,
    val isOverTheCounter: FHIRBoolean? = null,
    val manufacturer: Reference? = null,
    val form: CodeableConcept? = null,
    val ingredient: List<STU3MedicationIngredient> = listOf(),
    val `package`: STU3MedicationPackage? = null,
    val image: List<Attachment>? = null
) : STU3DomainResource<STU3Medication> {
    override val resourceType: String = "Medication"

    override fun transformToR4(): R4Medication {
        val newExtensions = mutableListOf<Extension>()
        isBrand?.let {
            newExtensions.add(
                Extension(
                    url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.isBrand"),
                    value = DynamicValue(DynamicValueType.BOOLEAN, it)
                )
            )
        }
        isOverTheCounter?.let {
            newExtensions.add(
                Extension(
                    url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-MedicationOTC"),
                    value = DynamicValue(DynamicValueType.BOOLEAN, it)
                )
            )
        }
        `package`?.let {
            it.container?.let { container ->
                newExtensions.add(
                    Extension(
                        url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.container"),
                        value = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, container)
                    )
                )
            }
            it.content.map { content ->
                val contentExtensions = mutableListOf<Extension>()
                contentExtensions.add(
                    Extension(
                        url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content"),
                        value = content.item
                    )
                )
                content.amount?.let { amount ->
                    contentExtensions.add(
                        Extension(
                            url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content.amount"),
                            value = DynamicValue(DynamicValueType.QUANTITY, amount)
                        )
                    )
                }

                newExtensions.add(
                    Extension(
                        url = Uri("http://hl7.org/fhir/3.0/StructureDefinition/extension-Medication.package.content"),
                        extension = contentExtensions
                    )
                )
            }
        }

        return R4Medication(
            id = id,
            meta = meta,
            implicitRules = implicitRules,
            language = language,
            text = text,
            contained = contained.map { it.transformToR4() },
            extension = extension + newExtensions,
            modifierExtension = modifierExtension,
            code = code,
            status = status,
            manufacturer = manufacturer,
            form = form,
            ingredient = ingredient.map { it.transformToR4() },
            batch = `package`?.batch?.firstOrNull()?.transformToR4()
        )
    }
}

class STU3MedicationSerializer : BaseFHIRSerializer<STU3Medication>(STU3Medication::class.java)
class STU3MedicationDeserializer : BaseFHIRDeserializer<STU3Medication>(STU3Medication::class.java)

@JsonSerialize(using = STU3MedicationIngredientSerializer::class)
@JsonDeserialize(using = STU3MedicationIngredientDeserializer::class)
data class STU3MedicationIngredient(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val item: DynamicValue<Any>,
    val isActive: FHIRBoolean? = null,
    val amount: Ratio? = null
) : STU3BackboneElement<STU3MedicationIngredient> {
    override fun transformToR4(): Ingredient {
        return Ingredient(
            id = id,
            extension = extension,
            modifierExtension = modifierExtension,
            item = item,
            isActive = isActive,
            strength = amount
        )
    }
}

class STU3MedicationIngredientSerializer :
    BaseFHIRSerializer<STU3MedicationIngredient>(STU3MedicationIngredient::class.java)

class STU3MedicationIngredientDeserializer :
    BaseFHIRDeserializer<STU3MedicationIngredient>(STU3MedicationIngredient::class.java)

@JsonSerialize(using = STU3MedicationPackageSerializer::class)
@JsonDeserialize(using = STU3MedicationPackageDeserializer::class)
data class STU3MedicationPackage(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val container: CodeableConcept? = null,
    val content: List<STU3MedicationPackageContent> = listOf(),
    val batch: List<STU3MedicationPackageBatch> = listOf()
) : STU3BackboneElement<STU3MedicationPackage> {
    override fun transformToR4(): Element<*> {
        throw IllegalStateException("No suitable transformation exists")
    }
}

class STU3MedicationPackageSerializer : BaseFHIRSerializer<STU3MedicationPackage>(STU3MedicationPackage::class.java)
class STU3MedicationPackageDeserializer : BaseFHIRDeserializer<STU3MedicationPackage>(STU3MedicationPackage::class.java)

@JsonSerialize(using = STU3MedicationPackageContentSerializer::class)
@JsonDeserialize(using = STU3MedicationPackageContentDeserializer::class)
data class STU3MedicationPackageContent(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val item: DynamicValue<Any>,
    val amount: SimpleQuantity? = null
) : STU3BackboneElement<STU3MedicationPackageContent> {
    override fun transformToR4(): Element<*> {
        throw IllegalStateException("No suitable transformation exists")
    }
}

class STU3MedicationPackageContentSerializer :
    BaseFHIRSerializer<STU3MedicationPackageContent>(STU3MedicationPackageContent::class.java)

class STU3MedicationPackageContentDeserializer :
    BaseFHIRDeserializer<STU3MedicationPackageContent>(STU3MedicationPackageContent::class.java)

@JsonSerialize(using = STU3MedicationPackageBatchSerializer::class)
@JsonDeserialize(using = STU3MedicationPackageBatchDeserializer::class)
data class STU3MedicationPackageBatch(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val lotNumber: FHIRString? = null,
    val expirationDate: DateTime? = null
) : STU3BackboneElement<STU3MedicationPackageBatch> {
    override fun transformToR4(): Batch {
        return Batch(
            id = id,
            extension = extension,
            modifierExtension = modifierExtension,
            lotNumber = lotNumber,
            expirationDate = expirationDate
        )
    }
}

class STU3MedicationPackageBatchSerializer :
    BaseFHIRSerializer<STU3MedicationPackageBatch>(STU3MedicationPackageBatch::class.java)

class STU3MedicationPackageBatchDeserializer :
    BaseFHIRDeserializer<STU3MedicationPackageBatch>(STU3MedicationPackageBatch::class.java)
