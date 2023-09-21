package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.Procedure
import com.projectronin.interop.fhir.r4.valueset.EventStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ProcedureValidatorTest {
    @Test
    fun `fails if no status provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val procedure = Procedure(
                status = null,
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4ProcedureValidator.validate(procedure).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ Procedure.status",
            exception.message
        )
    }

    @Test
    fun `fails if status outside required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val procedure = Procedure(
                status = Code("unknown-status"),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4ProcedureValidator.validate(procedure).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unknown-status' is outside of required value set @ Procedure.status",
            exception.message
        )
    }

    @Test
    fun `fails if no subject provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val procedure = Procedure(
                status = EventStatus.COMPLETED.asCode(),
                subject = null
            )
            R4ProcedureValidator.validate(procedure).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: subject is a required element @ Procedure.subject",
            exception.message
        )
    }

    @Test
    fun `fails if performed is not an allowed type`() {
        val exception = assertThrows<IllegalArgumentException> {
            val procedure = Procedure(
                status = EventStatus.COMPLETED.asCode(),
                subject = Reference(reference = FHIRString("Patient/1234")),
                performed = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE)
            )
            R4ProcedureValidator.validate(procedure).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: performed can only be one of the following: DateTime, Period, String, Age, Range @ Procedure.performed",
            exception.message
        )
    }

    @Test
    fun `validates if performed is an allowed type`() {
        val procedure = Procedure(
            status = EventStatus.COMPLETED.asCode(),
            subject = Reference(reference = FHIRString("Patient/1234")),
            performed = DynamicValue(DynamicValueType.STRING, FHIRString("performed"))
        )
        R4ProcedureValidator.validate(procedure).alertIfErrors()
    }

    @Test
    fun `validates successfully`() {
        val procedure = Procedure(
            status = EventStatus.COMPLETED.asCode(),
            subject = Reference(reference = FHIRString("Patient/1234"))
        )
        R4ProcedureValidator.validate(procedure).alertIfErrors()
    }
}
