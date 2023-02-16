package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DataRequirement
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4DataRequirementValidatorTest {
    @Test
    fun `fails for no type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val dataRequirement = DataRequirement(
                    type = null
                )
                R4DataRequirementValidator.validate(dataRequirement).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: type is a required element @ DataRequirement.type",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported subject dynamic value type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val dataRequirement = DataRequirement(
                    type = Code("type"),
                    subject = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1))
                )
                R4DataRequirementValidator.validate(dataRequirement).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: subject can only be one of the following: CodeableConcept, Reference @ DataRequirement.subject",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val dataRequirement = DataRequirement(
            type = Code("type")
        )
        R4DataRequirementValidator.validate(dataRequirement).alertIfErrors()
    }
}
