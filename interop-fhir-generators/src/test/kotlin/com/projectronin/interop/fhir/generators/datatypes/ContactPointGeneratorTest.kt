package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ContactPointGeneratorTest {
    @Test
    fun `function works with default`() {
        val contactPoint = contactPoint { }
        assertNull(contactPoint.id)
        assertEquals(0, contactPoint.extension.size)
        assertNotNull(contactPoint.system)
        assertNotNull(contactPoint.value)
        assertNotNull(contactPoint.use)
        assertNull(contactPoint.rank)
        assertNull(contactPoint.period)
    }

    @Test
    fun `function works with parameters`() {
        val contactPoint = contactPoint {
            system of ContactPointSystem.EMAIL.asCode()
            value of "josh@projectronin.com"
        }
        assertEquals(Code("email"), contactPoint.system)
        assertEquals(FHIRString("josh@projectronin.com"), contactPoint.value)
    }
}
