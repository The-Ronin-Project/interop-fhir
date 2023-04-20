package com.projectronin.interop.fhir.validate

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ValidationTest {
    private val error = FHIRError(
        code = "INT-001",
        severity = ValidationIssueSeverity.ERROR,
        description = "There was a really bad issue thing that happened.",
        location = LocationContext("Resource", "identifier.system")
    )
    private val warning = FHIRError(
        code = "INT-002",
        severity = ValidationIssueSeverity.WARNING,
        description = "There was an issue thing that happened.",
        location = LocationContext("Resource", "status")
    )

    private val validationError = ValidationIssue(
        code = "INT-001",
        description = "There was a really bad issue thing that happened.",
        location = LocationContext("Resource", "identifier.system"),
        severity = ValidationIssueSeverity.ERROR
    )

    private val validationWarning = ValidationIssue(
        code = "INT-002",
        description = "There was an issue thing that happened.",
        location = LocationContext("Resource", "status"),
        severity = ValidationIssueSeverity.WARNING
    )

    @Test
    fun `check with true value does not create an error`() {
        val validation = Validation()
        validation.checkTrue(true, error, null)

        val issues = validation.issues()
        assertEquals(listOf<Exception>(), issues)
        assertEquals(listOf<ValidationIssue>(), issues)
    }

    @Test
    fun `check with false value creates an error`() {
        val validation = Validation()
        validation.checkTrue(false, error, null)

        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(validationError.toString(), issues[0].toString())
    }

    @Test
    fun `checkCodedEnum with valid code value passes`() {
        val validation = Validation()
        val gender = AdministrativeGender.FEMALE.asCode()
        validation.checkCodedEnum<AdministrativeGender>(
            gender,
            InvalidValueSetError(Patient::gender, gender.value),
            LocationContext("Patient", "")
        )

        val issues = validation.issues()
        assertEquals(0, issues.size)
    }

    @Test
    fun `checkCodedEnum with invalid code value creates an InvalidValueSetError`() {
        val validation = Validation()
        val gender = Code("I do not know")
        val message = "ERROR INV_VALUE_SET: 'I do not know' is outside of required value set @ Patient.gender"
        validation.checkCodedEnum<AdministrativeGender>(
            gender,
            InvalidValueSetError(Patient::gender, gender.value),
            LocationContext("Patient", "")
        )

        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(message, issues[0].toString())
    }

    @Test
    fun `checkCodedEnum with null code creates an InvalidValueSetError`() {
        val validation = Validation()
        val gender = null
        val message = "ERROR INV_VALUE_SET: 'null' is outside of required value set @ Patient.gender"
        validation.checkNotNull(
            gender,
            InvalidValueSetError(Patient::gender, gender),
            LocationContext("Patient", "")
        )

        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(message, issues[0].toString())
    }

    @Test
    fun `checkCodedEnum with null code value creates an InvalidValueSetError`() {
        val validation = Validation()
        val gender = Code(null)
        val message = "ERROR INV_VALUE_SET: 'null' is outside of required value set @ Patient.gender"
        validation.checkCodedEnum<AdministrativeGender>(
            gender,
            InvalidValueSetError(Patient::gender, gender.value),
            LocationContext("Patient", "")
        )

        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(message, issues[0].toString())
    }

    @Test
    fun `notNull with null value creates an error`() {
        val validation = Validation()
        validation.checkNotNull(null, warning, null)

        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(validationWarning.toString(), issues[0].toString())
    }

    @Test
    fun `notNull with non-null value does not create an error`() {
        val validation = Validation()
        validation.checkNotNull("NotNull", warning, null)

        val issues = validation.issues()
        assertEquals(listOf<ValidationIssue>(), issues)
    }

    @Test
    fun `ifNotNull ignores block if value is null`() {
        val validation = Validation()
        validation.ifNotNull(null) {
            checkNotNull(null, error, null)
        }

        val issues = validation.issues()
        assertEquals(listOf<ValidationIssue>(), issues)
    }

    @Test
    fun `ifNotNull applies block if value is not null`() {
        val validation = Validation()
        validation.ifNotNull("notNull") {
            checkNotNull(null, error, null)
        }

        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(error.toValidationIssue(), issues[0])
    }

    @Test
    fun `merge combines 2 validations`() {
        val validation1 = Validation()
        validation1.checkNotNull(null, warning, null)

        val validation2 = Validation()
        validation2.checkNotNull(null, error, null)

        validation1.merge(validation2)

        val issues = validation1.issues()
        assertEquals(2, issues.size)
        assertEquals(warning.toValidationIssue(), issues[0])
        assertEquals(error.toValidationIssue(), issues[1])
    }

    @Test
    fun `alertIfErrors does nothing if no errors exist`() {
        val validation = Validation()
        validation.checkTrue(true, error, null)
        validation.alertIfErrors()
    }

    @Test
    fun `alertIfErrors works with issues`() {
        val validation = Validation()
        validation.checkTrue(false, warning, null)
        validation.checkTrue(false, error, null)
        val exception = assertThrows<IllegalArgumentException> {
            validation.alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INT-001: There was a really bad issue thing that happened. @ Resource.identifier.system",
            exception.message
        )
    }

    @Test
    fun `getErrorString returns null if no errors exist`() {
        val validation = Validation()
        validation.checkTrue(true, error, null)
        val errorString = validation.getErrorString()
        assertNull(errorString)
    }

    @Test
    fun `getErrorString reports errors`() {
        val validation = Validation()
        validation.checkTrue(false, error, null)
        val errorString = validation.getErrorString()
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INT-001: There was a really bad issue thing that happened. @ Resource.identifier.system",
            errorString
        )
    }

    @Test
    fun `getErrorString ignores issues when errors present`() {
        val validation = Validation()
        validation.checkTrue(false, warning, null)
        validation.checkTrue(false, error, null)
        val errorString = validation.getErrorString()
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INT-001: There was a really bad issue thing that happened. @ Resource.identifier.system",
            errorString
        )
    }

    @Test
    fun `getErrorString ignores issues when no errors present`() {
        val validation = Validation()
        validation.checkTrue(false, warning, null)
        val errorString = validation.getErrorString()
        assertNull(errorString)
    }

    @Test
    fun `validation function creates an instance`() {
        val validation = validation {
            checkTrue(false, warning, null)
        }

        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(warning.toValidationIssue(), issues[0])
    }

    @Test
    fun `validateAndAlert function creates an instance and does nothing if no errors exist`() {
        validateAndAlert {
            checkTrue(true, warning, null)
        }
    }

    @Test
    fun `validateAndAlert function creates an instance and throws an exception if errors exist`() {
        val exception = assertThrows<IllegalArgumentException> {
            validateAndAlert {
                checkTrue(false, error, null)
            }
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INT-001: There was a really bad issue thing that happened. @ Resource.identifier.system",
            exception.message
        )
    }

    @Test
    fun `hasIssues returns false when no issues recorded`() {
        val validation = validation {
            checkTrue(true, warning, null)
        }

        assertFalse(validation.hasIssues())
    }

    @Test
    fun `hasIssues returns true when issues recorded`() {
        val validation = validation {
            checkTrue(false, warning, null)
        }

        assertTrue(validation.hasIssues())
    }

    @Test
    fun `hasErrors returns false when no issues recorded`() {
        val validation = validation {
            checkTrue(true, warning, null)
        }

        assertFalse(validation.hasErrors())
    }

    @Test
    fun `hasErrors returns false when no errors recorded`() {
        val validation = validation {
            checkTrue(false, warning, null)
        }

        assertFalse(validation.hasErrors())
    }

    @Test
    fun `hasErrors returns true when an error recorded`() {
        val validation = validation {
            checkTrue(false, error, null)
        }

        assertTrue(validation.hasErrors())
    }
}
