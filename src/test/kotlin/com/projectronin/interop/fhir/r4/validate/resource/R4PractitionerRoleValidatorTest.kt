package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.resource.PractitionerRole
import org.junit.jupiter.api.Test

class R4PractitionerRoleValidatorTest {
    @Test
    fun `passes validation`() {
        val practitionerRole = PractitionerRole(
            identifier = listOf(Identifier(value = "id"))
        )
        R4PractitionerRoleValidator.validate(practitionerRole).alertIfErrors()
    }
}
