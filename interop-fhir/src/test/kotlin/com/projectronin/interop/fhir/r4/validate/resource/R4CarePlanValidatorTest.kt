package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.CarePlan
import com.projectronin.interop.fhir.r4.resource.CarePlanActivity
import com.projectronin.interop.fhir.r4.resource.CarePlanDetail
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CarePlanValidatorTest {
    @Test
    fun `validates successfully`() {
        val carePlan = CarePlan(
            status = Code("active"),
            intent = Code("plan"),
            subject = Reference(reference = FHIRString("Patient/XYZ123"))
        )
        R4CarePlanValidator.validate(carePlan).alertIfErrors()
    }

    @Test
    fun `fails on null intent`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlan = CarePlan(
                status = Code("active"),
                intent = null,
                subject = Reference(reference = FHIRString("Patient/XYZ123"))
            )
            R4CarePlanValidator.validate(carePlan).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: intent is a required element @ CarePlan.intent",
            exception.message
        )
    }

    @Test
    fun `fails on null status`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlan = CarePlan(
                status = null,
                intent = Code("plan"),
                subject = Reference(reference = FHIRString("Patient/XYZ123"))
            )
            R4CarePlanValidator.validate(carePlan).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ CarePlan.status",
            exception.message
        )
    }

    @Test
    fun `fails on null subject`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlan = CarePlan(
                status = Code("active"),
                intent = Code("plan"),
                subject = null
            )
            R4CarePlanValidator.validate(carePlan).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: subject is a required element @ CarePlan.subject",
            exception.message
        )
    }

    @Test
    fun `fails on invalid code for intent`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlan = CarePlan(
                status = Code("active"),
                intent = Code("potato"),
                subject = Reference(reference = FHIRString("Patient/XYZ123"))
            )
            R4CarePlanValidator.validate(carePlan).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'potato' is outside of required value set @ CarePlan.intent",
            exception.message
        )
    }

    @Test
    fun `fails on invalid code for status`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlan = CarePlan(
                status = Code("potato"),
                intent = Code("plan"),
                subject = Reference(reference = FHIRString("Patient/XYZ123"))
            )
            R4CarePlanValidator.validate(carePlan).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'potato' is outside of required value set @ CarePlan.status",
            exception.message
        )
    }

    @Test
    fun `warns if subject is not a supported type`() {
        val validation = R4CarePlanValidator.validate(
            CarePlan(
                status = Code("active"),
                intent = Code("plan"),
                subject = Reference(reference = FHIRString("Practitioner/XYZ123"))
            )
        )
        assertEquals(1, validation.issues().size)
        val issue = validation.issues().first()
        assertEquals(
            "WARNING INV_REF_TYPE: reference can only be one of the following: Patient, Group @ CarePlan.subject.reference",
            issue.toString()
        )
    }

    @Test
    fun `validates if subject is a supported type`() {
        val validation = R4CarePlanValidator.validate(
            CarePlan(
                status = Code("active"),
                intent = Code("plan"),
                subject = Reference(reference = FHIRString("Patient/XYZ123"))
            )
        )
        Assertions.assertFalse(validation.hasIssues())
    }
}

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

class R4CarePlanDetailValidatorTest {
    @Test
    fun `validates successfully`() {
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
            scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
            product = DynamicValue(DynamicValueType.CODEABLE_CONCEPT, CodeableConcept(text = FHIRString("product"))),
            description = FHIRString("Description")
        )
        R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
    }

    @Test
    fun `fails on wrong dynamic type values for scheduled`() {
        val exception = assertThrows<IllegalArgumentException> {
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
                scheduled = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE),
                product = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("product"))
                ),
                description = FHIRString("Description")
            )
            R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: scheduled can only be one of the following: Timing, Period, String @ CarePlanDetail.scheduled",
            exception.message
        )
    }

    @Test
    fun `fails on wrong dynamic type values for product`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlanDetail = CarePlanDetail(
                id = FHIRString("12345"),
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, "Value")
                    )
                ),
                modifierExtension = listOf(
                    Extension(
                        url = Uri("http://localhost/modifier-extension"),
                        value = DynamicValue(DynamicValueType.INTEGER, 1)
                    )
                ),
                kind = Code("Appointment"),
                goal = listOf(
                    Reference(reference = FHIRString("ABC123"))
                ),
                status = Code("scheduled"),
                scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
                product = DynamicValue(DynamicValueType.STRING, "product"),
                description = FHIRString("Description")
            )
            R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: product can only be one of the following: CodeableConcept, Reference @ CarePlanDetail.product",
            exception.message
        )
    }

    @Test
    fun `fails on invalid code for kind`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlanDetail = CarePlanDetail(
                id = FHIRString("12345"),
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, "Value")
                    )
                ),
                modifierExtension = listOf(
                    Extension(
                        url = Uri("http://localhost/modifier-extension"),
                        value = DynamicValue(DynamicValueType.INTEGER, 1)
                    )
                ),
                kind = Code("potato"),
                goal = listOf(
                    Reference(reference = FHIRString("ABC123"))
                ),
                status = Code("scheduled"),
                scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
                product = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("product"))
                ),
                description = FHIRString("Description")
            )
            R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'potato' is outside of required value set @ CarePlanDetail.kind",
            exception.message
        )
    }

    @Test
    fun `fails on null status`() {
        val exception = assertThrows<IllegalArgumentException> {
            val carePlanDetail = CarePlanDetail(
                id = FHIRString("12345"),
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, "Value")
                    )
                ),
                modifierExtension = listOf(
                    Extension(
                        url = Uri("http://localhost/modifier-extension"),
                        value = DynamicValue(DynamicValueType.INTEGER, 1)
                    )
                ),
                kind = Code("Appointment"),
                goal = listOf(
                    Reference(reference = FHIRString("ABC123"))
                ),
                status = null,
                scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
                product = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("product"))
                ),
                description = FHIRString("Description")
            )
            R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ CarePlanDetail.status",
            exception.message
        )
    }

    @Test
    fun `fails on invalid code for status`() {
        val exception = assertThrows<IllegalArgumentException> {
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
                status = Code("potato"),
                scheduled = DynamicValue(DynamicValueType.STRING, "Value"),
                product = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("product"))
                ),
                description = FHIRString("Description")
            )
            R4CarePlanDetailValidator.validate(carePlanDetail).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'potato' is outside of required value set @ CarePlanDetail.status",
            exception.message
        )
    }
}
