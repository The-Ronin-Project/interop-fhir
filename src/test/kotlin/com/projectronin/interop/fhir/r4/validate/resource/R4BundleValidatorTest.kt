package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Bundle
import com.projectronin.interop.fhir.r4.resource.BundleLink
import com.projectronin.interop.fhir.r4.resource.BundleRequest
import com.projectronin.interop.fhir.r4.resource.BundleResponse
import com.projectronin.interop.fhir.r4.resource.BundleSearch
import com.projectronin.interop.fhir.r4.valueset.BundleType
import com.projectronin.interop.fhir.util.asCode
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4BundleValidatorTest {
    @Test
    fun `no type provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundle = Bundle(
                type = null
            )
            R4BundleValidator.validate(bundle).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: type is a required element @ Bundle.type",
            exception.message
        )
    }

    @Test
    fun `type is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundle = Bundle(
                type = Code("unsupported-type")
            )
            R4BundleValidator.validate(bundle).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-type' is outside of required value set @ Bundle.type",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val bundle = Bundle(
            type = BundleType.BATCH.asCode()
        )
        R4BundleValidator.validate(bundle).alertIfErrors()
    }
}

class R4BundleLinkValidatorTest {
    @Test
    fun `fails if no relation provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleLink = BundleLink(
                relation = null,
                url = Uri("http://www.example.com/prev")
            )
            R4BundleLinkValidator.validate(bundleLink).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: relation is a required element @ BundleLink.relation",
            exception.message
        )
    }

    @Test
    fun `fails if no url provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleLink = BundleLink(
                relation = FHIRString("prev"),
                url = null
            )
            R4BundleLinkValidator.validate(bundleLink).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: url is a required element @ BundleLink.url",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val bundleLink = BundleLink(
            relation = FHIRString("prev"),
            url = Uri("http://www.example.com/prev")
        )
        R4BundleLinkValidator.validate(bundleLink).alertIfErrors()
    }
}

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
        Assertions.assertEquals(
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
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-method' is outside of required value set @ BundleRequest.method",
            exception.message
        )
    }

    @Test
    fun `fails if no url provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleRequest = BundleRequest(
                method = com.projectronin.interop.fhir.r4.valueset.HttpVerb.GET.asCode(),
                url = null
            )
            R4BundleRequestValidator.validate(bundleRequest).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: url is a required element @ BundleRequest.url",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val bundleRequest = BundleRequest(
            method = com.projectronin.interop.fhir.r4.valueset.HttpVerb.POST.asCode(),
            url = Uri("http://www.example.com/post")
        )
        R4BundleRequestValidator.validate(bundleRequest).alertIfErrors()
    }
}

class R4BundleResponseValidatorTest {
    @Test
    fun `fails if no status provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleResponse = BundleResponse(status = null)
            R4BundleResponseValidator.validate(bundleResponse).alertIfErrors()
        }
        Assertions.assertEquals(
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
        Assertions.assertEquals(
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

class R4BundleSearchValidatorTest {
    @Test
    fun `fails if mode is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val bundleSearch = BundleSearch(mode = Code("unsupported-mode"))
            R4BundleSearchValidator.validate(bundleSearch).alertIfErrors()
        }
        Assertions.assertEquals(
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
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-mode' is outside of required value set @ Test.field.mode",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val bundleSearch = BundleSearch(mode = com.projectronin.interop.fhir.r4.valueset.SearchEntryMode.OUTCOME.asCode())
        R4BundleSearchValidator.validate(bundleSearch).alertIfErrors()
    }
}
