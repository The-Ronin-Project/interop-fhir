package com.projectronin.interop.fhir.util

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.CodeableConcepts
import com.projectronin.interop.fhir.r4.datatype.Identifier
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
                value = null
            ),
            identifier
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
                value = FHIRString("1234")
            ),
            identifier
        )
    }
}
