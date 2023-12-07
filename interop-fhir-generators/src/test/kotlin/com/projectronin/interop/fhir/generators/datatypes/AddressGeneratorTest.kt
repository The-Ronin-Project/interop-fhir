package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AddressGeneratorTest {
    @Test
    fun `function works with default`() {
        val address = address {}
        assertNull(address.id)
        assertEquals(0, address.extension.size)
        assertNotNull(address.use)
        assertNotNull(address.type)
        assertNull(address.text)
        assertTrue(address.line.isNotEmpty())
        assertNotNull(address.city)
        assertNull(address.district)
        assertNotNull(address.state)
        assertNotNull(address.postalCode)
        assertNotNull(address.country)
        assertNull(address.period)
    }

    @Test
    fun `function works with parameters`() {
        val address =
            address {
                city of "Kansas City"
                state of "KS"
            }
        assertEquals(FHIRString("Kansas City"), address.city)
        assertEquals(FHIRString("KS"), address.state)
    }
}
