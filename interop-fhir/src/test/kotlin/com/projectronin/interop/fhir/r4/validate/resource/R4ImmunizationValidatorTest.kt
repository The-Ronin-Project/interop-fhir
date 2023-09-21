package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Immunization
import com.projectronin.interop.fhir.r4.resource.ImmunizationEducation
import com.projectronin.interop.fhir.r4.resource.ImmunizationProtocolApplied
import com.projectronin.interop.fhir.r4.valueset.ImmunizationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ImmunizationValidatorTest {
    @Test
    fun `fails if no status`() {
        val exception = assertThrows<IllegalArgumentException> {
            val immunization = Immunization(
                status = null,
                vaccineCode = CodeableConcept(text = FHIRString("vaccine code")),
                patient = Reference(display = FHIRString("patient")),
                occurrence = DynamicValue(DynamicValueType.STRING, FHIRString("yesterday"))
            )
            R4ImmunizationValidator.validate(immunization).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ Immunization.status",
            exception.message
        )
    }

    @Test
    fun `fails if status is outside value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val immunization = Immunization(
                status = Code("unknown-status"),
                vaccineCode = CodeableConcept(text = FHIRString("vaccine code")),
                patient = Reference(display = FHIRString("patient")),
                occurrence = DynamicValue(DynamicValueType.STRING, FHIRString("yesterday"))
            )
            R4ImmunizationValidator.validate(immunization).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unknown-status' is outside of required value set @ Immunization.status",
            exception.message
        )
    }

    @Test
    fun `fails if no vaccine code`() {
        val exception = assertThrows<IllegalArgumentException> {
            val immunization = Immunization(
                status = ImmunizationStatus.COMPLETED.asCode(),
                vaccineCode = null,
                patient = Reference(display = FHIRString("patient")),
                occurrence = DynamicValue(DynamicValueType.STRING, FHIRString("yesterday"))
            )
            R4ImmunizationValidator.validate(immunization).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: vaccineCode is a required element @ Immunization.vaccineCode",
            exception.message
        )
    }

    @Test
    fun `fails if no patient`() {
        val exception = assertThrows<IllegalArgumentException> {
            val immunization = Immunization(
                status = ImmunizationStatus.COMPLETED.asCode(),
                vaccineCode = CodeableConcept(text = FHIRString("vaccine code")),
                patient = null,
                occurrence = DynamicValue(DynamicValueType.STRING, FHIRString("yesterday"))
            )
            R4ImmunizationValidator.validate(immunization).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: patient is a required element @ Immunization.patient",
            exception.message
        )
    }

    @Test
    fun `fails if no occurrence`() {
        val exception = assertThrows<IllegalArgumentException> {
            val immunization = Immunization(
                status = ImmunizationStatus.COMPLETED.asCode(),
                vaccineCode = CodeableConcept(text = FHIRString("vaccine code")),
                patient = Reference(display = FHIRString("patient")),
                occurrence = null
            )
            R4ImmunizationValidator.validate(immunization).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: occurrence is a required element @ Immunization.occurrence",
            exception.message
        )
    }

    @Test
    fun `fails if occurrence outside accepted types`() {
        val exception = assertThrows<IllegalArgumentException> {
            val immunization = Immunization(
                status = ImmunizationStatus.COMPLETED.asCode(),
                vaccineCode = CodeableConcept(text = FHIRString("vaccine code")),
                patient = Reference(display = FHIRString("patient")),
                occurrence = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.FALSE)
            )
            R4ImmunizationValidator.validate(immunization).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: occurrence can only be one of the following: DateTime, String @ Immunization.occurrence",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val immunization = Immunization(
            status = ImmunizationStatus.COMPLETED.asCode(),
            vaccineCode = CodeableConcept(text = FHIRString("vaccine code")),
            patient = Reference(display = FHIRString("patient")),
            occurrence = DynamicValue(DynamicValueType.STRING, FHIRString("yesterday"))
        )
        R4ImmunizationValidator.validate(immunization).alertIfErrors()
    }
}

class R4ImmunizationEducationTest {
    @Test
    fun `fails if no document type or reference`() {
        val exception = assertThrows<IllegalArgumentException> {
            val education = ImmunizationEducation(
                documentType = null,
                reference = null
            )
            R4ImmunizationEducationValidator.validate(education).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_IMMEDU_001: One of documentType or reference SHALL be present @ ImmunizationEducation",
            exception.message
        )
    }

    @Test
    fun `validates successfully with document type`() {
        val education = ImmunizationEducation(
            documentType = FHIRString("PDF")
        )
        R4ImmunizationEducationValidator.validate(education).alertIfErrors()
    }

    @Test
    fun `validates successfully with reference`() {
        val education = ImmunizationEducation(
            reference = Uri("reference")
        )
        R4ImmunizationEducationValidator.validate(education).alertIfErrors()
    }
}

class R4ImmunizationProtocolAppliedTest {
    @Test
    fun `fails if no dose number`() {
        val exception = assertThrows<IllegalArgumentException> {
            val protocolApplied = ImmunizationProtocolApplied(
                doseNumber = null
            )
            R4ImmunizationProtocolAppliedValidator.validate(protocolApplied).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: doseNumber is a required element @ ImmunizationProtocolApplied.doseNumber",
            exception.message
        )
    }

    @Test
    fun `fails if dose number outside of accepted types`() {
        val exception = assertThrows<IllegalArgumentException> {
            val protocolApplied = ImmunizationProtocolApplied(
                doseNumber = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE)
            )
            R4ImmunizationProtocolAppliedValidator.validate(protocolApplied).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: doseNumber can only be one of the following: PositiveInt, String @ ImmunizationProtocolApplied.doseNumber",
            exception.message
        )
    }

    @Test
    fun `fails if series doses outside of accepted types`() {
        val exception = assertThrows<IllegalArgumentException> {
            val protocolApplied = ImmunizationProtocolApplied(
                doseNumber = DynamicValue(DynamicValueType.POSITIVE_INT, PositiveInt(1)),
                seriesDoses = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE)
            )
            R4ImmunizationProtocolAppliedValidator.validate(protocolApplied).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: seriesDoses can only be one of the following: PositiveInt, String @ ImmunizationProtocolApplied.seriesDoses",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val protocolApplied = ImmunizationProtocolApplied(
            doseNumber = DynamicValue(DynamicValueType.POSITIVE_INT, PositiveInt(1))
        )
        R4ImmunizationProtocolAppliedValidator.validate(protocolApplied).alertIfErrors()
    }
}
