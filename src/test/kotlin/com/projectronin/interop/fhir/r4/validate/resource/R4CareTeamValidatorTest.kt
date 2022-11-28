package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.CareTeam
import com.projectronin.interop.fhir.r4.valueset.CareTeamStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4CareTeamValidatorTest {

    @Test
    fun `fails with incorrect status`() {
        val exception = assertThrows<IllegalArgumentException> {
            val careTeam = CareTeam(
                status = Code("blah")
            )
            R4CareTeamValidator.validate(careTeam).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: status is outside of required value set @ CareTeam.status",
            exception.message
        )
    }

    @Test
    fun `validates ok`() {
        val careTeam = CareTeam(
            status = CareTeamStatus.ACTIVE.asCode(),
            id = Id("test")
        )
        R4CareTeamValidator.validate(careTeam).alertIfErrors()
    }
}
