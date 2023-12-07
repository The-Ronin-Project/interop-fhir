package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.valueset.ContactPointUse
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.interop.fhir.util.asCode
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RequiredValueSetValidatorTest {
    private val validator = RequiredValueSetValidator()

    @Test
    fun `ignores if called with no annotations`() {
        Validation().apply {
            validator.validateAnnotations(
                listOf(),
                CodeElement::code,
                Code::class,
                CodeElement(RequestStatus.COMPLETED.asCode()),
                "CodeElement",
                LocationContext(CodeElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `ignores if sent multiple annotations`() {
        Validation().apply {
            validator.validateAnnotations(
                listOf(RequiredValueSet(RequestStatus::class), RequiredValueSet(ContactPointUse::class)),
                CodeElement::code,
                Code::class,
                CodeElement(RequestStatus.COMPLETED.asCode()),
                "CodeElement",
                LocationContext(CodeElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `ignores non-Code types`() {
        Validation().apply {
            validator.validateAnnotations(
                listOf(RequiredValueSet(RequestStatus::class)),
                NonCodeElement::code,
                FHIRString::class,
                NonCodeElement(FHIRString("completed")),
                "NonCodeElement",
                LocationContext(NonCodeElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `ignores null Code`() {
        Validation().apply {
            validator.validateAnnotations(
                listOf(RequiredValueSet(RequestStatus::class)),
                CodeElement::code,
                Code::class,
                CodeElement(null),
                "CodeElement",
                LocationContext(CodeElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `fails validation when code outside value set`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Validation().apply {
                    validator.validateAnnotations(
                        listOf(RequiredValueSet(RequestStatus::class)),
                        CodeElement::code,
                        Code::class,
                        CodeElement(Code("unknown-code")),
                        "CodeElement",
                        LocationContext(CodeElement::class),
                        this,
                    )
                }.alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unknown-code' is outside of required value set @ CodeElement.code",
            exception.message,
        )
    }

    @Test
    fun `passes validation when code within value set`() {
        Validation().apply {
            validator.validateAnnotations(
                listOf(RequiredValueSet(RequestStatus::class)),
                CodeElement::code,
                Code::class,
                CodeElement(RequestStatus.COMPLETED.asCode()),
                "CodeElement",
                LocationContext(CodeElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `passes when provided a Collection of non-Codes`() {
        Validation().apply {
            validator.validateAnnotations(
                listOf(RequiredValueSet(RequestStatus::class)),
                CollectionNonCodeElement::codes,
                List::class,
                CollectionNonCodeElement(listOf(FHIRString("other"), FHIRString("another"))),
                "CollectionNonCodeElement",
                LocationContext(CollectionNonCodeElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `passes when all values in a Collection of Codes are within value set`() {
        Validation().apply {
            validator.validateAnnotations(
                listOf(RequiredValueSet(RequestStatus::class)),
                CollectionCodeElement::codes,
                List::class,
                CollectionCodeElement(listOf(RequestStatus.COMPLETED.asCode(), RequestStatus.ACTIVE.asCode())),
                "CollectionCodeElement",
                LocationContext(CollectionCodeElement::class),
                this,
            )
        }.alertIfErrors()
    }

    @Test
    fun `fails when one value in a Collection of Codes is outside value set`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Validation().apply {
                    validator.validateAnnotations(
                        listOf(RequiredValueSet(RequestStatus::class)),
                        CollectionCodeElement::codes,
                        List::class,
                        CollectionCodeElement(listOf(RequestStatus.COMPLETED.asCode(), Code("unknown-code"))),
                        "CollectionCodeElement",
                        LocationContext(CollectionCodeElement::class),
                        this,
                    )
                }.alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unknown-code' is outside of required value set @ CollectionCodeElement.codes[1]",
            exception.message,
        )
    }

    @Test
    fun `fails when multiple values in a Collection of Codes are outside value set`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Validation().apply {
                    validator.validateAnnotations(
                        listOf(RequiredValueSet(RequestStatus::class)),
                        CollectionCodeElement::codes,
                        List::class,
                        CollectionCodeElement(listOf(Code("unknown-code"), Code("also-unknown"))),
                        "CollectionCodeElement",
                        LocationContext(CollectionCodeElement::class),
                        this,
                    )
                }.alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unknown-code' is outside of required value set @ CollectionCodeElement.codes[0]\n" +
                "ERROR INV_VALUE_SET: 'also-unknown' is outside of required value set @ CollectionCodeElement.codes[1]",
            exception.message,
        )
    }

    data class CodeElement(
        val code: Code?,
    ) : Validatable<CodeElement>

    data class NonCodeElement(
        val code: FHIRString?,
    ) : Validatable<NonCodeElement>

    data class CollectionCodeElement(
        val codes: List<Code>,
    ) : Validatable<CollectionCodeElement>

    data class CollectionNonCodeElement(
        val codes: List<FHIRString>,
    ) : Validatable<CollectionNonCodeElement>
}
