package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MedicationRequestStatusTest {
    @Test
    fun `can find enums by code`() {
        for (enum in MedicationRequestStatus.values()) {
            assertEquals(enum, CodedEnum.byCode<MedicationRequestStatus>(enum.code))
        }
    }
}
