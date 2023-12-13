package com.projectronin.interop.fhir.util

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.CodeableConcepts
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class IdUtil {
    @Test
    fun `creates identifier from null id`() {
        val id: Id? = null
        val identifier = id.toFhirIdentifier()
        assertNull(identifier)
    }

    @Test
    fun `creates identifier from non-null id with null value`() {
        val id = Id(null)
        val identifier = id.toFhirIdentifier()
        assertEquals(
            Identifier(
                type = CodeableConcepts.RONIN_FHIR_ID,
                system = CodeSystem.RONIN_FHIR_ID.uri,
                value = null,
            ),
            identifier,
        )
    }

    @Test
    fun `creates identifier from non-null id with non-null value`() {
        val id = Id("1234")
        val identifier = id.toFhirIdentifier()
        assertEquals(
            Identifier(
                type = CodeableConcepts.RONIN_FHIR_ID,
                system = CodeSystem.RONIN_FHIR_ID.uri,
                value = FHIRString("1234"),
            ),
            identifier,
        )
    }

    @Test
    fun `localizes I with no value`() {
        val id = Id(null)
        val localizedId = id.localize("test")
        assertEquals(id, localizedId)
    }

    @Test
    fun `localizes Id with value`() {
        val id = Id("1234")
        val localizedId = id.localize("test")
        assertEquals(Id("test-1234"), localizedId)
    }

    @Test
    fun `localizes Id with value, id and extension`() {
        val extension = Extension()
        val id = Id("1234", FHIRString("9876"), listOf(extension))
        val localizedId = id.localize("test")
        assertEquals(Id("test-1234", FHIRString("9876"), listOf(extension)), localizedId)
    }

    @Test
    fun `localizeFhirId for prefixed string`() {
        assertEquals("test-1234", "test-1234".localizeFhirId("test"))
    }

    @Test
    fun `localizeFhirId for non-prefixed string`() {
        assertEquals("test-1234", "1234".localizeFhirId("test"))
    }

    @Test
    fun `unlocalizeFhirId for prefixed string`() {
        assertEquals("1234", "test-1234".unlocalizeFhirId("test"))
    }

    @Test
    fun `unlocalizeFhirId for non-prefixed string`() {
        assertEquals("1234", "1234".unlocalizeFhirId("test"))
    }

    @Test
    fun `localize reference with no reference`() {
        val reference = Reference()
        val localizedReference = reference.localize("test")
        assertEquals(reference, localizedReference)
    }

    @Test
    fun `localize reference with reference with no value`() {
        val reference = Reference(reference = FHIRString(null))
        val localizedReference = reference.localize("test")
        assertEquals(reference, localizedReference)
    }

    @Test
    fun `localize reference with reference with no id or extension`() {
        val reference = Reference(reference = FHIRString("Patient/1234"))
        val localizedReference = reference.localize("test")
        assertEquals(Reference(reference = FHIRString("Patient/test-1234")), localizedReference)
    }

    @Test
    fun `localize reference with reference with id and extension`() {
        val extension = Extension()
        val reference = Reference(reference = FHIRString("Patient/1234", FHIRString("9876"), listOf(extension)))
        val localizedReference = reference.localize("test")
        assertEquals(
            Reference(reference = FHIRString("Patient/test-1234", FHIRString("9876"), listOf(extension))),
            localizedReference,
        )
    }
}
