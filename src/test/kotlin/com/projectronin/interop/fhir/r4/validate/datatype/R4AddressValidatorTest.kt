package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.valueset.AddressType
import com.projectronin.interop.fhir.util.asCode
import com.projectronin.interop.fhir.validate.LocationContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4AddressValidatorTest {
    @Test
    fun `fails if use is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val address = Address(
                use = Code("unsupported-use")
            )
            R4AddressValidator.validate(address).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-use' is outside of required value set @ Address.use",
            exception.message
        )
    }

    @Test
    fun `fails if type is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val address = Address(
                type = Code("unsupported-type")
            )
            R4AddressValidator.validate(address).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-type' is outside of required value set @ Address.type",
            exception.message
        )
    }

    @Test
    fun `failure includes parent context`() {
        val exception = assertThrows<IllegalArgumentException> {
            val address = Address(
                type = Code("unsupported-type")
            )
            R4AddressValidator.validate(address, LocationContext("Test", "field")).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-type' is outside of required value set @ Test.field.type",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val address = Address(type = AddressType.POSTAL.asCode())
        R4AddressValidator.validate(address).alertIfErrors()
    }
}
