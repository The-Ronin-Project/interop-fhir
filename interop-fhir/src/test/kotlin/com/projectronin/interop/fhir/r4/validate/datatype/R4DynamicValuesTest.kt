package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.validate.datatype.primitive.validatePrimitive
import com.projectronin.interop.fhir.r4.validate.element.validateElement
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.validation
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class R4DynamicValuesTest {
    private val failedValidation = validation {
        checkNotNull(null, RequiredFieldError(Patient::name), null)
    }
    private val locationContext = LocationContext("Sample", "field")

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `can validate DynamicValue of Primitive`() {
        val unsignedInt = UnsignedInt(1)

        mockkStatic("com.projectronin.interop.fhir.r4.validate.datatype.primitive.R4PrimitivesKt")
        every { validatePrimitive(unsignedInt, locationContext) } returns failedValidation

        val dynamicValue = DynamicValue(DynamicValueType.UNSIGNED_INT, unsignedInt)

        val validation = validateDynamicValue(dynamicValue, locationContext)
        assertEquals(1, validation.issues().size)

        unmockkStatic("com.projectronin.interop.fhir.r4.validate.datatype.primitive.R4PrimitivesKt")
    }

    @Test
    fun `can validate DynamicValue of Element`() {
        val address = mockk<Address>()

        mockkStatic("com.projectronin.interop.fhir.r4.validate.element.R4ElementsKt")
        every { validateElement(address, locationContext) } returns failedValidation

        val dynamicValue = DynamicValue(DynamicValueType.ADDRESS, address)

        val validation = validateDynamicValue(dynamicValue, locationContext)
        assertEquals(1, validation.issues().size)

        unmockkStatic("com.projectronin.interop.fhir.r4.validate.element.R4ElementsKt")
    }

    @Test
    fun `can validate DynamicValue of other type`() {
        val dynamicValue = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE)

        val validation = validateDynamicValue(dynamicValue, locationContext)
        assertEquals(0, validation.issues().size)
    }
}
