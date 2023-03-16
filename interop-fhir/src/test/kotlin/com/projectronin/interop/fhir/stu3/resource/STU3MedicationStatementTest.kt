package com.projectronin.interop.fhir.stu3.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.Timing
import com.projectronin.interop.fhir.r4.datatype.TimingRepeat
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.AppointmentStatus
import com.projectronin.interop.fhir.r4.valueset.MedicationStatementStatus
import com.projectronin.interop.fhir.stu3.element.STU3Dosage
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class STU3MedicationStatementTest {
    private val goodSTU3MedicationStatement = STU3MedicationStatement(
        id = Id("e7ysJK2meg5wvYmtVYL.XUA3"),
        extension = listOf(
            Extension(
                url = Uri("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse-AsAgreedIndicator"),
                value = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE)
            ),
            Extension(
                url = Uri("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse-Prescriber"),
                value = DynamicValue(
                    DynamicValueType.REFERENCE,
                    Reference(
                        reference = FHIRString("https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Practitioner/eNylgW31R0pwKW3KahakeYg3"),
                        display = FHIRString("Md Builder Family Medicine, MD")
                    )
                )
            )
        ),
        identifier = listOf(
            Identifier(
                use = Code("usual"),
                system = Uri("urn:oid:1.2.840.114350.1.13.0.1.7.2.798268"),
                value = FHIRString("935495")
            )
        ),
        basedOn = listOf(
            Reference(
                reference = FHIRString("https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/MedicationRequest/eY0Avl7TVGnvJSEzD8rV8ow3"),
                identifier = Identifier(
                    use = Code("usual"),
                    system = Uri("urn:oid:1.2.840.114350.1.13.0.1.7.2.798268"),
                    value = FHIRString("935495")
                ),
                display = FHIRString("adalimumab (HUMIRA) injection 40 mg")
            )
        ),
        status = MedicationStatementStatus.ACTIVE.asCode(),
        category = CodeableConcept(
            coding = listOf(
                Coding(
                    system = Uri("http://hl7.org/fhir/medication-statement-category"),
                    code = Code("outpatient"),
                    display = FHIRString("Outpatient")
                )
            )
        ),
        medication = DynamicValue(
            DynamicValueType.REFERENCE,
            Reference(
                reference = FHIRString("https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Medication/e0bQzmhE9eqNm.WV2C41u14sSlxSOcSAgK0yokCPngLbC9AU0Zdrl3CecTBcFGI3t3"),
                display = FHIRString("adalimumab (HUMIRA) 40 mg/0.8 mL injection")
            )
        ),
        effective = DynamicValue(
            DynamicValueType.PERIOD,
            Period(
                start = DateTime("2019-12-30T14:30:00Z")
            )
        ),
        dateAsserted = DateTime("2019-12-30T14:26:01Z"),
        informationSource = Reference(
            reference = FHIRString("https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Practitioner/eNylgW31R0pwKW3KahakeYg3"),
            display = FHIRString("Md Builder Family Medicine, MD")
        ),
        subject = Reference(
            reference = FHIRString("https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Patient/enh2Q1c0oNRtWzXArnG4tKw3"),
            display = FHIRString("M, Damon")
        ),
        reasonCode = listOf(
            CodeableConcept(
                coding = listOf(
                    Coding(
                        code = Code("177007"),
                        system = CodeSystem.SNOMED_CT.uri,
                        display = FHIRString("Poisoning by sawfly larvae")
                    )
                ),
                text = FHIRString("Poisoning by sawfly larvae")
            )
        ),
        taken = "unk",
        dosage = listOf(
            STU3Dosage(
                extension = listOf(
                    Extension(
                        url = Uri("https://open.epic.com/fhir/extensions/admin-amount"),
                        value = DynamicValue(
                            type = DynamicValueType.QUANTITY,
                            value = Quantity(
                                value = Decimal(0.8),
                                unit = FHIRString("mL"),
                                system = Uri("http://unitsofmeasure.org"),
                                code = Code("mL")
                            )
                        )
                    ),
                    Extension(
                        url = Uri("https://open.epic.com/fhir/extensions/ordered-dose"),
                        value = DynamicValue(
                            type = DynamicValueType.QUANTITY,
                            value = Quantity(
                                value = Decimal(40.0),
                                unit = FHIRString("mg"),
                                system = Uri("http://unitsofmeasure.org"),
                                code = Code("mg")
                            )
                        )
                    )
                ),
                timing = Timing(
                    repeat = TimingRepeat(
                        bounds = DynamicValue(
                            DynamicValueType.PERIOD,
                            Period(
                                start = DateTime("2019-12-30T14:30:00Z")
                            )
                        ),
                        count = PositiveInt(1)
                    )
                ),
                asNeeded = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.FALSE),
                route = CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("urn:oid:1.2.840.114350.1.13.0.1.7.4.798268.7025"),
                            code = Code("18"),
                            display = FHIRString("Subcutaneous")
                        )
                    ),
                    text = FHIRString("Subcutaneous")
                ),
                dose = DynamicValue(
                    type = DynamicValueType.QUANTITY,
                    value = Quantity(
                        value = Decimal(40.0),
                        unit = FHIRString("mg"),
                        system = Uri("http://unitsofmeasure.org"),
                        code = Code("mg")
                    )
                )
            ) // STU3Dosage
        ) // listOf()
    )

    @Test
    fun `can serialize and deserialize JSON`() {
        val json =
            JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(goodSTU3MedicationStatement)

        val expectedJson = """
          {
            "resourceType" : "MedicationStatement",
            "id" : "e7ysJK2meg5wvYmtVYL.XUA3",
            "extension" : [ {
              "url" : "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse-AsAgreedIndicator",
              "valueBoolean" : true
            }, {
              "url" : "http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse-Prescriber",
              "valueReference" : {
                "reference" : "https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Practitioner/eNylgW31R0pwKW3KahakeYg3",
                "display" : "Md Builder Family Medicine, MD"
              }
            } ],
            "identifier" : [ {
              "use" : "usual",
              "system" : "urn:oid:1.2.840.114350.1.13.0.1.7.2.798268",
              "value" : "935495"
            } ],
            "basedOn" : [ {
              "reference" : "https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/MedicationRequest/eY0Avl7TVGnvJSEzD8rV8ow3",
              "identifier" : {
                "use" : "usual",
                "system" : "urn:oid:1.2.840.114350.1.13.0.1.7.2.798268",
                "value" : "935495"
              },
              "display" : "adalimumab (HUMIRA) injection 40 mg"
            } ],
            "status" : "active",
            "category" : {
              "coding" : [ {
                "system" : "http://hl7.org/fhir/medication-statement-category",
                "code" : "outpatient",
                "display" : "Outpatient"
              } ]
            },
            "medicationReference" : {
              "reference" : "https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Medication/e0bQzmhE9eqNm.WV2C41u14sSlxSOcSAgK0yokCPngLbC9AU0Zdrl3CecTBcFGI3t3",
              "display" : "adalimumab (HUMIRA) 40 mg/0.8 mL injection"
            },
            "subject" : {
              "reference" : "https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Patient/enh2Q1c0oNRtWzXArnG4tKw3",
              "display" : "M, Damon"
            },
            "effectivePeriod" : {
              "start" : "2019-12-30T14:30:00Z"
            },
            "dateAsserted" : "2019-12-30T14:26:01Z",
            "informationSource" : {
              "reference" : "https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Practitioner/eNylgW31R0pwKW3KahakeYg3",
              "display" : "Md Builder Family Medicine, MD"
            },
            "taken" : "unk",
            "reasonCode" : [ {
              "coding" : [ {
                "system" : "http://snomed.info/sct",
                "code" : "177007",
                "display" : "Poisoning by sawfly larvae"
              } ],
              "text" : "Poisoning by sawfly larvae"
            } ],
            "dosage" : [ {
              "extension" : [ {
                "url" : "https://open.epic.com/fhir/extensions/admin-amount",
                "valueQuantity" : {
                  "value" : 0.8,
                  "unit" : "mL",
                  "system" : "http://unitsofmeasure.org",
                  "code" : "mL"
                }
              }, {
                "url" : "https://open.epic.com/fhir/extensions/ordered-dose",
                "valueQuantity" : {
                  "value" : 40.0,
                  "unit" : "mg",
                  "system" : "http://unitsofmeasure.org",
                  "code" : "mg"
                }
              } ],
              "timing" : {
                "repeat" : {
                  "boundsPeriod" : {
                    "start" : "2019-12-30T14:30:00Z"
                  },
                  "count" : 1
                }
              },
              "asNeededBoolean" : false,
              "route" : {
                "coding" : [ {
                  "system" : "urn:oid:1.2.840.114350.1.13.0.1.7.4.798268.7025",
                  "code" : "18",
                  "display" : "Subcutaneous"
                } ],
                "text" : "Subcutaneous"
              },
              "doseQuantity" : {
                "value" : 40.0,
                "unit" : "mg",
                "system" : "http://unitsofmeasure.org",
                "code" : "mg"
              }
            } ]
          }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedMedicationStatement = JacksonManager.objectMapper.readValue<STU3MedicationStatement>(json)
        assertEquals(goodSTU3MedicationStatement, deserializedMedicationStatement)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val medicationStatement = STU3MedicationStatement(
            status = AppointmentStatus.CANCELLED.asCode(),
            subject = Reference(display = FHIRString("x")),
            medication = DynamicValue(
                DynamicValueType.REFERENCE,
                Reference(display = FHIRString("y"))
            )
        )
        val json = JacksonManager.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medicationStatement)

        val expectedJson = """
            {
              "resourceType" : "MedicationStatement",
              "status" : "cancelled",
              "medicationReference" : {
                "display" : "y"
              },
              "subject" : {
                "display" : "x"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "MedicationStatement",
              "status" : "cancelled",
              "medicationReference" : {
                "reference" : "https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Medication/e0bQzmhE9eqNm.WV2C41u14sSlxSOcSAgK0yokCPngLbC9AU0Zdrl3CecTBcFGI3t3",
                "display" : "adalimumab (HUMIRA) 40 mg/0.8 mL injection"
              },
              "subject" : {
                "reference" : "https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Patient/enh2Q1c0oNRtWzXArnG4tKw3",
                "display" : "M, Damon"
              }
            }
        """.trimIndent()
        val medicationStatement = JacksonManager.objectMapper.readValue<STU3MedicationStatement>(json)

        // nulls
        assertNull(medicationStatement.id)
        assertNull(medicationStatement.meta)
        assertNull(medicationStatement.implicitRules)
        assertNull(medicationStatement.language)
        assertNull(medicationStatement.text)
        assertEquals(listOf<STU3Resource<Nothing>>(), medicationStatement.contained)
        assertEquals(listOf<Extension>(), medicationStatement.extension)
        assertEquals(listOf<Extension>(), medicationStatement.modifierExtension)
        assertEquals(listOf<Identifier>(), medicationStatement.identifier)

        // non-nulls
        assertEquals(
            Code("cancelled"),
            medicationStatement.status
        )
        assertEquals(
            DynamicValue(
                DynamicValueType.REFERENCE,
                Reference(
                    reference = FHIRString("https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Medication/e0bQzmhE9eqNm.WV2C41u14sSlxSOcSAgK0yokCPngLbC9AU0Zdrl3CecTBcFGI3t3"),
                    display = FHIRString("adalimumab (HUMIRA) 40 mg/0.8 mL injection")
                )
            ),
            medicationStatement.medication
        )
        assertEquals(
            Reference(
                reference = FHIRString("https://appmarket.epic.com/interconnect-amcurprd-oauth/api/FHIR/STU3/Patient/enh2Q1c0oNRtWzXArnG4tKw3"),
                display = FHIRString("M, Damon")
            ),
            medicationStatement.subject
        )
    }

    @Test
    fun `transform to R4 - works - taken unk`() {
        val r4MedicationStatement = goodSTU3MedicationStatement.transformToR4()

        assertEquals(Code(MedicationStatementStatus.UNKNOWN.code), r4MedicationStatement.status)
        assertEquals(goodSTU3MedicationStatement.reasonCode, r4MedicationStatement.reasonCode)

        assertEquals(
            goodSTU3MedicationStatement.dosage.first().rate,
            r4MedicationStatement.dosage.first().doseAndRate.first().rate
        )
        assertNull(r4MedicationStatement.dosage.first().doseAndRate.first().type)

        assertEquals(goodSTU3MedicationStatement.medication, r4MedicationStatement.medication)
        assertEquals(goodSTU3MedicationStatement.subject, r4MedicationStatement.subject)
        assertEquals(emptyList<CodeableConcept>(), r4MedicationStatement.statusReason)
        assertEquals(goodSTU3MedicationStatement.reasonCode, r4MedicationStatement.reasonCode)
    }

    @Test
    fun `transform to R4 - works - taken n with no reasonNotTaken`() {
        val medicationStatement = goodSTU3MedicationStatement.copy(taken = "n")
        val r4MedicationStatement = medicationStatement.transformToR4()

        assertEquals(MedicationStatementStatus.NOT_TAKEN.asCode(), r4MedicationStatement.status)
        assertEquals(emptyList<CodeableConcept>(), r4MedicationStatement.statusReason)
        assertEquals(medicationStatement.reasonCode, r4MedicationStatement.reasonCode)
    }

    @Test
    fun `transform to R4 - works - taken n with reasonNotTaken - reasonNotTaken and reasonCode both survive`() {
        val medicationStatement = goodSTU3MedicationStatement.copy(
            taken = "n",
            reasonNotTaken = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            code = Code("182869005"),
                            system = CodeSystem.SNOMED_CT.uri,
                            display = FHIRString("Drug not taken - patient lost tablets")
                        )
                    ),
                    text = FHIRString("Drug not taken - patient lost tablets")
                )
            )
        )
        val r4MedicationStatement = medicationStatement.transformToR4()

        assertEquals(MedicationStatementStatus.NOT_TAKEN.asCode(), r4MedicationStatement.status)
        assertEquals(medicationStatement.reasonNotTaken, r4MedicationStatement.statusReason)
        assertEquals(medicationStatement.reasonCode, r4MedicationStatement.reasonCode)
    }

    @Test
    fun `transform to R4 - works - taken is neither n nor unk - Dosage has dose`() {
        val medicationStatement = goodSTU3MedicationStatement.copy(taken = "y")
        val r4MedicationStatement = medicationStatement.transformToR4()

        assertEquals(medicationStatement.status, r4MedicationStatement.status)
        assertEquals(emptyList<CodeableConcept>(), r4MedicationStatement.statusReason)
        assertEquals(medicationStatement.reasonCode, r4MedicationStatement.reasonCode)
    }
}
