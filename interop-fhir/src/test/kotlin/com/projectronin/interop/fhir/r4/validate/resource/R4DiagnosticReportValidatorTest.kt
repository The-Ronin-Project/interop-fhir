package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.DiagnosticReport
import com.projectronin.interop.fhir.r4.valueset.DiagnosticReportStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4DiagnosticReportValidatorTest {
    @Test
    fun `fails if no status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                R4DiagnosticReportValidator.validate(
                    DiagnosticReport(
                        status = null,
                        code = CodeableConcept(text = FHIRString("code")),
                    ),
                ).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ DiagnosticReport.status",
            exception.message,
        )
    }

    @Test
    fun `fails for invalid status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                R4DiagnosticReportValidator.validate(
                    DiagnosticReport(
                        status = Code("invalid-status"),
                        code = CodeableConcept(text = FHIRString("code")),
                    ),
                ).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-status' is outside of required value set @ DiagnosticReport.status",
            exception.message,
        )
    }

    @Test
    fun `fails if no code`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                R4DiagnosticReportValidator.validate(
                    DiagnosticReport(
                        status = DiagnosticReportStatus.CANCELLED.asCode(),
                        code = null,
                    ),
                ).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: code is a required element @ DiagnosticReport.code",
            exception.message,
        )
    }

    @Test
    fun `fails if effective outside available types`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                R4DiagnosticReportValidator.validate(
                    DiagnosticReport(
                        status = DiagnosticReportStatus.CANCELLED.asCode(),
                        code = CodeableConcept(text = FHIRString("code")),
                        effective = DynamicValue(DynamicValueType.STRING, FHIRString("yes")),
                    ),
                ).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: effective can only be one of the following: DateTime, Period @ DiagnosticReport.effective",
            exception.message,
        )
    }

    @Test
    fun `validates successfully`() {
        R4DiagnosticReportValidator.validate(
            DiagnosticReport(
                status = DiagnosticReportStatus.CANCELLED.asCode(),
                code = CodeableConcept(text = FHIRString("code")),
            ),
        ).alertIfErrors()
    }
}
