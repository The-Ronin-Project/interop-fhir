package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MedicationRequestIntentTest {
    @Test
    fun `can find enums by code`() {
        for (enum in MedicationRequestIntent.values()) {
            assertEquals(enum, CodedEnum.byCode<MedicationRequestIntent>(enum.code))
        }
    }
}
