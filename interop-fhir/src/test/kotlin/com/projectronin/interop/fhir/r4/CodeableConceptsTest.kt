package com.projectronin.interop.fhir.r4

import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class CodeableConceptsTest {
    @Test
    fun `codecov for CodeableConcept TENANT`() {
        val coding = CodeableConcepts.RONIN_TENANT.coding.first()
        assertEquals("TID", coding.code!!.value)
        assertEquals("Ronin-specified Tenant Identifier".asFHIR(), coding.display)
        assertNotNull(coding.system)
    }

    @Test
    fun `codecov for CodeableConcept MRN`() {
        val coding = CodeableConcepts.RONIN_MRN.coding.first()
        assertEquals("MRN", coding.code!!.value)
        assertEquals("Medical Record Number".asFHIR(), coding.display)
        assertNotNull(coding.system)
    }

    @Test
    fun `codecov for CodeableConcept FHIR_ID`() {
        val coding = CodeableConcepts.RONIN_FHIR_ID.coding.first()
        assertEquals("FHIR ID", coding.code!!.value)
        assertEquals("FHIR Identifier".asFHIR(), coding.display)
        assertNotNull(coding.system)
    }

    @Test
    fun `codecov for CodeableConcept DATA_AUTHORITY_ID`() {
        val coding = CodeableConcepts.RONIN_DATA_AUTHORITY_ID.coding.first()
        assertEquals("DAID", coding.code!!.value)
        assertEquals("Data Authority Identifier".asFHIR(), coding.display)
        assertNotNull(coding.system)
    }
}
