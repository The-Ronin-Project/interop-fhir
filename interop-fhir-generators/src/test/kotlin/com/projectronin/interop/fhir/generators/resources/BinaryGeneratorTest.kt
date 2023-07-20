package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.generators.primitives.ofLength
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.Base64

class BinaryGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val binary = binary {}
        assertNull(binary.id)
        assertNull(binary.meta)
        assertNull(binary.implicitRules)
        assertNull(binary.language)
        assertNotNull(binary.contentType)
        assertNull(binary.securityContent)
        assertNotNull(binary.data)
    }

    @Test
    fun `function works with parameters`() {
        val binary = binary {
            id of "id"
            contentType of Code("text/plain")
            securityContent of reference("Mime", "123")
            data of "base64string"
        }
        val binary2 = binary {
            id of "id"
            contentType of Code("text/plain")
            securityContent of reference("Mime", "123")
            data ofLength 3063
        }
        assertEquals("id", binary.id?.value)
        assertEquals("text/plain", binary.contentType?.value)
        assertEquals(
            "Mime/123",
            binary.securityContent?.reference?.value
        )
        assertEquals(Base64.getEncoder().encodeToString("base64string".toByteArray()), binary.data?.value)
        assertEquals(3063, binary2.data?.value?.length)
    }
}
