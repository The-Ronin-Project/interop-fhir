package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.BundleSearch
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.valueset.SearchEntryMode
import com.projectronin.interop.fhir.util.asCode
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4BundleSearchValidatorTest {
    @Test
    fun `fails if mode is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleSearch = BundleSearch(mode = Code("unsupported-mode"))
            R4BundleSearchValidator.validate(bundleSearch).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-mode' is outside of required value set @ BundleSearch.mode",
            exception.message
        )
    }

    @Test
    fun `failure includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleSearch = BundleSearch(mode = Code("unsupported-mode"))
            R4BundleSearchValidator.validate(bundleSearch, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-mode' is outside of required value set @ Test.field.mode",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val bundleSearch = BundleSearch(mode = SearchEntryMode.OUTCOME.asCode())
        R4BundleSearchValidator.validate(bundleSearch).alertIfErrors()
    }
}
