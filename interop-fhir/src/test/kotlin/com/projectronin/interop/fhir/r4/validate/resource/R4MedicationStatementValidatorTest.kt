package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.MedicationStatement
import com.projectronin.interop.fhir.r4.valueset.MedicationStatementStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4MedicationStatementValidatorTest {
    @Test
    fun `status is not provided`() {
        val medication = DynamicValue(
            type = DynamicValueType.CODEABLE_CONCEPT,
            value = CodeableConcept(text = FHIRString("Medication Category"))
        )
        val ex = assertThrows<IllegalArgumentException> {
            val medicationStatement = MedicationStatement(
                status = null,
                medication = medication,
                subject = Reference(display = FHIRString("reference")),
            )
            R4MedicationStatementValidator.validate(medicationStatement).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ MedicationStatement.status",
            ex.message
        )
    }

    @Test
    fun `status is outside of required value set`() {
        val medication = DynamicValue(
            type = DynamicValueType.CODEABLE_CONCEPT,
            value = CodeableConcept(text = FHIRString("Medication Category"))
        )

        val ex = assertThrows<IllegalArgumentException> {
            val medicationStatement = MedicationStatement(
                status = Code("unsupported-status"),
                medication = medication,
                subject = Reference(display = FHIRString("reference")),
            )
            R4MedicationStatementValidator.validate(medicationStatement).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-status' is outside of required value set @ MedicationStatement.status",
            ex.message
        )
    }

    @Test
    fun `medication is required`() {
        val ex = assertThrows<IllegalArgumentException> {
            val medicationStatement = MedicationStatement(
                status = MedicationStatementStatus.COMPLETED.asCode(),
                medication = null,
                subject = Reference(display = FHIRString("reference")),
            )
            R4MedicationStatementValidator.validate(medicationStatement).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: medication is a required element @ MedicationStatement.medication",
            ex.message
        )
    }

    @Test
    fun `medication is unsupported dynamic type`() {
        val medication = DynamicValue(type = DynamicValueType.DATE, value = Date("2021"))
        val ex = assertThrows<IllegalArgumentException> {
            val medicationStatement = MedicationStatement(
                status = MedicationStatementStatus.COMPLETED.asCode(),
                medication = medication,
                subject = Reference(display = FHIRString("reference")),
            )
            R4MedicationStatementValidator.validate(medicationStatement).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: medication can only be one of the following: CodeableConcept, Reference @ MedicationStatement.medication",
            ex.message
        )
    }

    @Test
    fun `effective is unsupported dynamic type`() {
        val medication = DynamicValue(
            type = DynamicValueType.CODEABLE_CONCEPT,
            value = CodeableConcept(text = FHIRString("Medication Category"))
        )
        val effective = DynamicValue(type = DynamicValueType.DATE, value = Date("2022"))

        val ex = assertThrows<IllegalArgumentException> {
            val medicationStatement = MedicationStatement(
                status = MedicationStatementStatus.COMPLETED.asCode(),
                medication = medication,
                subject = Reference(display = FHIRString("reference")),
                effective = effective
            )
            R4MedicationStatementValidator.validate(medicationStatement).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: effective can only be one of the following: DateTime, Period @ MedicationStatement.effective",
            ex.message
        )
    }

    @Test
    fun `subject is required`() {
        val medication = DynamicValue(
            type = DynamicValueType.CODEABLE_CONCEPT,
            value = CodeableConcept(text = FHIRString("Medication Category"))
        )
        val ex = assertThrows<IllegalArgumentException> {
            val medicationStatement = MedicationStatement(
                status = MedicationStatementStatus.COMPLETED.asCode(),
                medication = medication,
                subject = null
            )
            R4MedicationStatementValidator.validate(medicationStatement).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: subject is a required element @ MedicationStatement.subject",
            ex.message
        )
    }

    @Test
    fun `works when all is good`() {
        val medication = DynamicValue(
            type = DynamicValueType.CODEABLE_CONCEPT,
            value = CodeableConcept(text = FHIRString("Medication Category"))
        )
        val medicationStatement = MedicationStatement(
            status = MedicationStatementStatus.COMPLETED.asCode(),
            medication = medication,
            subject = Reference(display = FHIRString("reference")),
        )
        R4MedicationStatementValidator.validate(medicationStatement).alertIfErrors()
    }
}
