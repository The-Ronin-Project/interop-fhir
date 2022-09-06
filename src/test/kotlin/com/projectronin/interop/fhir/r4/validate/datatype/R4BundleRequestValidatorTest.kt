package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.BundleRequest
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.HttpVerb
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4BundleRequestValidatorTest {
    @Test
    fun `fails if no method provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleRequest = BundleRequest(
                method = null,
                url = Uri("http://www.example.com/post")
            )
            R4BundleRequestValidator.validate(bundleRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: method is a required element @ BundleRequest.method",
            exception.message
        )
    }

    @Test
    fun `fails if status is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleRequest = BundleRequest(
                method = Code("unsupported-method"),
                url = Uri("http://www.example.com/post")
            )
            R4BundleRequestValidator.validate(bundleRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: method is outside of required value set @ BundleRequest.method",
            exception.message
        )
    }

    @Test
    fun `fails if no url provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleRequest = BundleRequest(
                method = HttpVerb.GET.asCode(),
                url = null
            )
            R4BundleRequestValidator.validate(bundleRequest).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: url is a required element @ BundleRequest.url",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val bundleRequest = BundleRequest(
            method = HttpVerb.POST.asCode(),
            url = Uri("http://www.example.com/post")
        )
        R4BundleRequestValidator.validate(bundleRequest).alertIfErrors()
    }
}
