package com.projectronin.interop.fhir.validate

import com.projectronin.interop.fhir.r4.datatype.Age
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import io.github.classgraph.ClassGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmErasure

class FHIRErrorTest {
    @Test
    fun `ensure unique codes`() {
        val fhirErrors = ClassGraph().acceptPackages("com.projectronin.interop.fhir").scan().use {
            it.getClassesImplementing(ProfileValidator::class.java).standardClasses.flatMap { c ->
                if (c.isFinal && !c.isInnerClass) {
                    val clazz = c.loadClass()
                    clazz.kotlin.memberProperties.filter { p ->
                        // This intentionally excludes the common types built on FHIRError, such as RequiredFieldError, since those codes will be repeated across validators
                        p.returnType.jvmErasure.isSuperclassOf(FHIRError::class)
                    }.map { p ->
                        p.isAccessible = true
                        "${clazz.name}.${p.name}" to (p.getter.call() as FHIRError)
                    }
                } else {
                    emptyList()
                }
            }
        }

        val fhirErrorLocationsByCode = fhirErrors.groupBy { it.second.code }.mapValues { it.value.map { p -> p.first } }
        val repeatCodes = fhirErrorLocationsByCode.filter { it.value.size > 1 }
        if (repeatCodes.isNotEmpty()) {
            fail<Nothing> { "Repeat FHIR error codes found: $repeatCodes" }
        }
    }

    @Test
    fun `FHIRError creates a ValidationIssue`() {
        val fhirError = FHIRError(
            code = "1",
            severity = ValidationIssueSeverity.ERROR,
            description = "description",
            location = LocationContext("FHIR", "value")
        )

        val validationIssue = fhirError.toValidationIssue()
        assertEquals("1", validationIssue.code)
        assertEquals(ValidationIssueSeverity.ERROR, validationIssue.severity)
        assertEquals("description", validationIssue.description)
        assertEquals(LocationContext("FHIR", "value"), validationIssue.location)
    }

    @Test
    fun `FHIRError creates a ValidationIssue with overidden description`() {
        val fhirError = FHIRError(
            code = "1",
            severity = ValidationIssueSeverity.ERROR,
            description = "description",
            location = LocationContext("FHIR", "value")
        )

        val validationIssue = fhirError.toValidationIssue(overriddenDescription = "new description")
        assertEquals("1", validationIssue.code)
        assertEquals(ValidationIssueSeverity.ERROR, validationIssue.severity)
        assertEquals("new description", validationIssue.description)
        assertEquals(LocationContext("FHIR", "value"), validationIssue.location)
    }

    @Test
    fun `FHIRError creates a ValidationIssue with parent context`() {
        val fhirError = FHIRError(
            code = "1",
            severity = ValidationIssueSeverity.ERROR,
            description = "description",
            location = LocationContext("FHIR", "value")
        )

        val validationIssue = fhirError.toValidationIssue(parentContext = LocationContext("Parent", "value1"))
        assertEquals("1", validationIssue.code)
        assertEquals(ValidationIssueSeverity.ERROR, validationIssue.severity)
        assertEquals("description", validationIssue.description)
        assertEquals(LocationContext("Parent", "value1.value"), validationIssue.location)
    }

    @Test
    fun `FHIRError without location creates a ValidationIssue`() {
        val fhirError = FHIRError(
            code = "1",
            severity = ValidationIssueSeverity.ERROR,
            description = "description",
            location = null
        )

        val validationIssue = fhirError.toValidationIssue()
        assertEquals("1", validationIssue.code)
        assertEquals(ValidationIssueSeverity.ERROR, validationIssue.severity)
        assertEquals("description", validationIssue.description)
        assertNull(validationIssue.location)
    }

    @Test
    fun `FHIRError without location creates a ValidationIssue with overidden description`() {
        val fhirError = FHIRError(
            code = "1",
            severity = ValidationIssueSeverity.ERROR,
            description = "description",
            location = null
        )

        val validationIssue = fhirError.toValidationIssue(overriddenDescription = "new description")
        assertEquals("1", validationIssue.code)
        assertEquals(ValidationIssueSeverity.ERROR, validationIssue.severity)
        assertEquals("new description", validationIssue.description)
        assertNull(validationIssue.location)
    }

    @Test
    fun `FHIRError without location creates a ValidationIssue with parent context`() {
        val fhirError = FHIRError(
            code = "1",
            severity = ValidationIssueSeverity.ERROR,
            description = "description",
            location = null
        )

        val validationIssue = fhirError.toValidationIssue(parentContext = LocationContext("Parent", "value1"))
        assertEquals("1", validationIssue.code)
        assertEquals(ValidationIssueSeverity.ERROR, validationIssue.severity)
        assertEquals("description", validationIssue.description)
        assertEquals(LocationContext("Parent", "value1"), validationIssue.location)
    }

    @Test
    fun `RequiredFieldError creates FHIRError from LocationContext`() {
        val error = RequiredFieldError(LocationContext("FHIR", "value"))
        assertEquals("REQ_FIELD", error.code)
        assertEquals(ValidationIssueSeverity.ERROR, error.severity)
        assertEquals("value is a required element", error.description)
        assertEquals(LocationContext("FHIR", "value"), error.location)
    }

    @Test
    fun `RequiredFieldError creates FHIRError from KProperty`() {
        val error = RequiredFieldError(Age::id)
        assertEquals("REQ_FIELD", error.code)
        assertEquals(ValidationIssueSeverity.ERROR, error.severity)
        assertEquals("id is a required element", error.description)
        assertEquals(LocationContext("Age", "id"), error.location)
    }

    @Test
    fun `InvalidPrimitiveError creates FHIRError from Primitive class`() {
        val error = InvalidPrimitiveError(Code::class)
        assertEquals("R4_INV_PRIM", error.code)
        assertEquals(ValidationIssueSeverity.ERROR, error.severity)
        assertEquals("Supplied value is not valid for a Code", error.description)
        assertNull(error.location)
    }

    @Test
    fun `InvalidDynamicValueError creates FHIRError from LocationContext`() {
        val error = InvalidDynamicValueError(
            LocationContext("FHIR", "value"),
            listOf(DynamicValueType.STRING, DynamicValueType.DECIMAL)
        )
        assertEquals("INV_DYN_VAL", error.code)
        assertEquals(ValidationIssueSeverity.ERROR, error.severity)
        assertEquals("value can only be one of the following: String, Decimal", error.description)
        assertEquals(LocationContext("FHIR", "value"), error.location)
    }

    @Test
    fun `InvalidDynamicValueError creates FHIRError from KProperty`() {
        val error = InvalidDynamicValueError(
            Annotation::author,
            listOf(DynamicValueType.STRING, DynamicValueType.DECIMAL)
        )
        assertEquals("INV_DYN_VAL", error.code)
        assertEquals(ValidationIssueSeverity.ERROR, error.severity)
        assertEquals("author can only be one of the following: String, Decimal", error.description)
        assertEquals(LocationContext("Annotation", "author"), error.location)
    }

    @Test
    fun `InvalidValueSetError creates FHIRError from LocationContext`() {
        val error = InvalidValueSetError(LocationContext("FHIR", "value"))
        assertEquals("INV_VALUE_SET", error.code)
        assertEquals(ValidationIssueSeverity.ERROR, error.severity)
        assertEquals("value is outside of required value set", error.description)
        assertEquals(LocationContext("FHIR", "value"), error.location)
    }

    @Test
    fun `InvalidValueSetError creates FHIRError from KProperty`() {
        val error = InvalidValueSetError(Attachment::creation)
        assertEquals("INV_VALUE_SET", error.code)
        assertEquals(ValidationIssueSeverity.ERROR, error.severity)
        assertEquals("creation is outside of required value set", error.description)
        assertEquals(LocationContext("Attachment", "creation"), error.location)
    }
}
