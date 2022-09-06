package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.resource.Bundle
import com.projectronin.interop.fhir.r4.valueset.BundleType
import com.projectronin.interop.fhir.util.asCode
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
                "ERROR INV_VALUE_SET: type is outside of required value set @ Bundle.type",
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
