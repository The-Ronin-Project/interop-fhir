package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ReferenceTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val reference = Reference(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            reference = FHIRString("Patient/123"),
            type = Uri("Patient"),
            identifier = Identifier(value = FHIRString("123")),
            display = FHIRString("Patient 123")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reference)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "reference" : "Patient/123",
            |  "type" : "Patient",
            |  "identifier" : {
            |    "value" : "123"
            |  },
            |  "display" : "Patient 123"
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedReference = objectMapper.readValue<Reference>(json)
        assertEquals(reference, deserializedReference)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val reference = Reference(type = Uri("Patient"), display = FHIRString("any"))
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reference)

        val expectedJson = """
            |{
            |  "type" : "Patient",
            |  "display" : "any"
            |}
        """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "reference" : "Patient/123"
            |}
        """.trimMargin()
        val reference = objectMapper.readValue<Reference>(json)

        assertNull(reference.id)
        assertEquals(listOf<Extension>(), reference.extension)
        assertEquals(FHIRString("Patient/123"), reference.reference)
        assertNull(reference.type)
        assertNull(reference.identifier)
        assertNull(reference.display)
    }

    @Test
    fun `isForType - works when reference present`() {
        val reference = Reference(reference = (FHIRString("Patient/")))
        assertTrue(reference.isForType("Patient"))
        assertFalse(reference.isForType("Location"))
    }

    @Test
    fun `isForType -  works when reference not present`() {
        val reference = Reference(reference = (FHIRString("HotGarbage!")))
        assertFalse(reference.isForType("Patient"))
    }

    @Test
    fun `isForType -  works when reference present but bad`() {
        val reference = Reference()
        assertFalse(reference.isForType("Patient"))
    }

    @Test
    fun `decomposedID - works when id is present`() {
        val reference = Reference(id = FHIRString("123"))
        assertEquals("123", reference.decomposedId())
    }

    @Test
    fun `decomposedID - works when no is present but reference is valid`() {
        val reference = Reference(reference = (FHIRString("Patient/123")))
        assertEquals("123", reference.decomposedId())
    }

    @Test
    fun `decomposedID - returns null when no id and reference has no ID`() {
        val reference = Reference(reference = (FHIRString("Patient/")))
        assertNull(reference.decomposedId())
    }

    @Test
    fun `decomposedID - returns null when no id and reference is bad`() {
        val reference = Reference(reference = (FHIRString("HotGarbage!")))
        assertNull(reference.decomposedId())
    }

    @Test
    fun `decomposedType - returns type when present`() {
        val reference = Reference(type = Uri("Patient"))
        assertEquals("Patient", reference.decomposedType())
    }

    @Test
    fun `decomposedType - returns type when reference present`() {
        val reference = Reference(reference = (FHIRString("Patient/123")))
        assertEquals("Patient", reference.decomposedType())
    }

    @Test
    fun `decomposedType - returns null when reference present but no type`() {
        val reference = Reference(reference = (FHIRString("123")))
        assertNull(reference.decomposedType())
    }

    @Test
    fun `decomposedType - returns null when reference is bad`() {
        val reference = Reference(reference = (FHIRString("HotGarbage!")))
        assertNull(reference.decomposedType())
    }

    @Test
    fun `static getType returns null when non-reference provided`() {
        val type = Reference.getType("http://www.google.com")
        assertNull(type)
    }

    @Test
    fun `static getType returns type from full reference`() {
        val type =
            Reference.getType("https://fhir-ehr.cerner.com/r4/ec2458f2-1e24-41c8-b71b-0e701af7583d/Communication/472894781.0.-4.0")
        assertEquals("Communication", type)
    }

    @Test
    fun `static getType returns type from partial reference`() {
        val type =
            Reference.getType("Communication/472894781.0.-4.0")
        assertEquals("Communication", type)
    }

    @Test
    fun `static getId returns null when non-reference provided`() {
        val id = Reference.getId("http://www.google.com/1234")
        assertNull(id)
    }

    @Test
    fun `static getId returns type from full reference`() {
        val id =
            Reference.getId("https://apporchard.epic.com/interconnect-aocurprd-oauth/api/FHIR/R4/PractitionerRole/e.TaXco-8ZEq63hw9riexz13ynhjloQIZHYC70uq-52zzam5A3iL3sq4D036f3.9u3")
        assertEquals("e.TaXco-8ZEq63hw9riexz13ynhjloQIZHYC70uq-52zzam5A3iL3sq4D036f3.9u3", id)
    }

    @Test
    fun `static getId returns type from partial reference`() {
        val id =
            Reference.getId("PractitionerRole/e.TaXco-8ZEq63hw9riexz13ynhjloQIZHYC70uq-52zzam5A3iL3sq4D036f3.9u3")
        assertEquals("e.TaXco-8ZEq63hw9riexz13ynhjloQIZHYC70uq-52zzam5A3iL3sq4D036f3.9u3", id)
    }
}
