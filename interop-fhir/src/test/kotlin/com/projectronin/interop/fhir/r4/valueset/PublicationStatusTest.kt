package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PublicationStatusTest {
    @Test
    fun `can find enums by code`() {
        for (enum in PublicationStatus.values()) {
            assertEquals(enum, CodedEnum.byCode<PublicationStatus>(enum.code))
        }
    }
}
