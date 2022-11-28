package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.BundleResponse
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4BundleResponseValidatorTest {
    @Test
    fun `fails if no status provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleResponse = BundleResponse(status = null)
            R4BundleResponseValidator.validate(bundleResponse).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ BundleResponse.status",
            exception.message
        )
    }

    @Test
    fun `failure includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleResponse = BundleResponse(status = null)
            R4BundleResponseValidator.validate(bundleResponse, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ Test.field.status",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val bundleResponse = BundleResponse(status = FHIRString("status"))
        R4BundleResponseValidator.validate(bundleResponse).alertIfErrors()
    }
}
