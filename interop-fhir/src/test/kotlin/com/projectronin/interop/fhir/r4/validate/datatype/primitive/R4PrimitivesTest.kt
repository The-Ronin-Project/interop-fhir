package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Oid
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.datatype.primitive.Uuid
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class R4PrimitivesTest {
    private val expectedIssues = listOf(RequiredFieldError(Patient::name).toValidationIssue())
    private val mockedValidation = mockk<Validation> {
        every { issues() } returns expectedIssues
    }
    private val locationContext = LocationContext("Sample", "field")

    @Test
    fun `can validate Base64Binary`() {
        val base64Binary = mockk<Base64Binary>()
        every { base64Binary.validate(R4Base64BinaryValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(base64Binary, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate Code`() {
        val code = mockk<Code>()
        every { code.validate(R4CodeValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(code, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate Date`() {
        val date = mockk<Date>()
        every { date.validate(R4DateValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(date, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate DateTime`() {
        val dateTime = mockk<DateTime>()
        every { dateTime.validate(R4DateTimeValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(dateTime, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate Id`() {
        val id = mockk<Id>()
        every { id.validate(R4IdValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(id, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate Instant`() {
        val instant = mockk<Instant>()
        every { instant.validate(R4InstantValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(instant, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate Oid`() {
        val oid = mockk<Oid>()
        every { oid.validate(R4OidValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(oid, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate PositiveInt`() {
        val positiveInt = mockk<PositiveInt>()
        every { positiveInt.validate(R4PositiveIntValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(positiveInt, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate Time`() {
        val time = mockk<Time>()
        every { time.validate(R4TimeValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(time, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate UnsignedInt`() {
        val unsignedInt = mockk<UnsignedInt>()
        every { unsignedInt.validate(R4UnsignedIntValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(unsignedInt, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate Uri`() {
        val uri = mockk<Uri>()
        every { uri.validate(R4UriValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(uri, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate Uuid`() {
        val uuid = mockk<Uuid>()
        every { uuid.validate(R4UuidValidator, locationContext) } returns mockedValidation

        val validation = validatePrimitive(uuid, locationContext)
        assertEquals(expectedIssues, validation.issues())
    }

    @Test
    fun `can validate other primitive with no constraints`() {
        val canonical = mockk<Canonical>()

        val validation = validatePrimitive(canonical, locationContext)
        assertEquals(0, validation.issues().size)
    }
}
