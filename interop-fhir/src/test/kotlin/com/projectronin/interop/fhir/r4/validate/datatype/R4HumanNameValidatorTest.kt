package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4HumanNameValidatorTest {
    @Test
    fun `fails if use is outside of required value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val humanName = HumanName(
                use = Code("unsupported-use")
            )
            R4HumanNameValidator.validate(humanName).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-use' is outside of required value set @ HumanName.use",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val humanName = HumanName(
            text = FHIRString("Name")
        )
        R4HumanNameValidator.validate(humanName).alertIfErrors()
    }
}