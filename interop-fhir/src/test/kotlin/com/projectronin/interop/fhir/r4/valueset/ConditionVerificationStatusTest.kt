package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ConditionVerificationStatusTest {
    @Test
    fun `can find enums by code`() {
        for (enum in ConditionVerificationStatus.values()) {
            Assertions.assertEquals(enum, CodedEnum.byCode<ConditionVerificationStatus>(enum.code))
        }
    }
}
