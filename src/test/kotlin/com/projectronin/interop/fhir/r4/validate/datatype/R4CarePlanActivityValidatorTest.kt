package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CarePlanActivity
import com.projectronin.interop.fhir.r4.datatype.CarePlanDetail
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CarePlanActivityValidatorTest {
    @Test
    fun `validates successfully`() {
        val carePlanActivity = CarePlanActivity(
            id = FHIRString("67890"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1))
                )
            ),
            outcomeCodeableConcept = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                            code = Code("DD"),
                            display = FHIRString("Discharge diagnosis")
                        )
                    )
                )
            ),
            outcomeReference = listOf(
                Reference(reference = FHIRString("outcome"))
            ),
            progress = listOf(
                Annotation(text = Markdown("123"))
            ),
            reference = Reference(reference = FHIRString("ABC123"))
        )
        R4CarePlanActivityValidator.validate(carePlanActivity).alertIfErrors()
    }

    @Test
    fun `fails if both reference and detail exists`() {
        val carePlanDetail = CarePlanDetail(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1))
                )
            ),
            kind = Code("Appointment"),
            goal = listOf(
                Reference(reference = FHIRString("ABC123"))
            ),
            status = Code("scheduled"),
            description = FHIRString("Description")
        )
        val exception = assertThrows<IllegalArgumentException> {
            val carePlanActivity = CarePlanActivity(
                id = FHIRString("67890"),
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                    )
                ),
                modifierExtension = listOf(
                    Extension(
                        url = Uri("http://localhost/modifier-extension"),
                        value = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1))
                    )
                ),
                outcomeCodeableConcept = listOf(
                    CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                code = Code("DD"),
                                display = FHIRString("Discharge diagnosis")
                            )
                        )
                    )
                ),
                outcomeReference = listOf(
                    Reference(reference = FHIRString("outcome"))
                ),
                progress = listOf(
                    Annotation(text = Markdown("123"))
                ),
                reference = Reference(reference = FHIRString("ABC123")),
                detail = carePlanDetail
            )
            R4CarePlanActivityValidator.validate(carePlanActivity).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CRPLNACT_001: Provide a reference or detail, not both @ CarePlanActivity",
            exception.message
        )
    }
}
