package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.BundleLink
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
        assertEquals(
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
        assertEquals(
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
