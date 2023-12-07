package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.ServiceRequest
import com.projectronin.interop.fhir.r4.valueset.RequestIntent
import com.projectronin.interop.fhir.r4.valueset.RequestPriority
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class R4ServiceRequestValidatorTest {
    @Test
    fun `fails if no status provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val request =
                    ServiceRequest(
                        status = null,
                        intent = RequestIntent.ORDER.asCode(),
                        subject = Reference(reference = FHIRString("Patient/1234")),
                    )

                R4ServiceRequestValidator.validate(request).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ ServiceRequest.status",
            exception.message,
        )
    }

    @Test
    fun `fails if status outside required value set`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val request =
                    ServiceRequest(
                        status = Code("invalid-status"),
                        intent = RequestIntent.ORDER.asCode(),
                        subject = Reference(reference = FHIRString("Patient/1234")),
                    )

                R4ServiceRequestValidator.validate(request).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-status' is outside of required value set @ ServiceRequest.status",
            exception.message,
        )
    }

    @Test
    fun `fails if no intent provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val request =
                    ServiceRequest(
                        status = RequestStatus.COMPLETED.asCode(),
                        intent = null,
                        subject = Reference(reference = FHIRString("Patient/1234")),
                    )

                R4ServiceRequestValidator.validate(request).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: intent is a required element @ ServiceRequest.intent",
            exception.message,
        )
    }

    @Test
    fun `fails if intent outside required value set`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val request =
                    ServiceRequest(
                        status = RequestStatus.COMPLETED.asCode(),
                        intent = Code("invalid-intent"),
                        subject = Reference(reference = FHIRString("Patient/1234")),
                    )

                R4ServiceRequestValidator.validate(request).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-intent' is outside of required value set @ ServiceRequest.intent",
            exception.message,
        )
    }

    @Test
    fun `fails if priority outside required value set`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val request =
                    ServiceRequest(
                        status = RequestStatus.COMPLETED.asCode(),
                        intent = RequestIntent.ORDER.asCode(),
                        subject = Reference(reference = FHIRString("Patient/1234")),
                        priority = Code("invalid-priority"),
                    )

                R4ServiceRequestValidator.validate(request).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-priority' is outside of required value set @ ServiceRequest.priority",
            exception.message,
        )
    }

    @Test
    fun `validates if priority in required value set`() {
        val request =
            ServiceRequest(
                status = RequestStatus.COMPLETED.asCode(),
                intent = RequestIntent.ORDER.asCode(),
                subject = Reference(reference = FHIRString("Patient/1234")),
                priority = RequestPriority.ASAP.asCode(),
            )

        R4ServiceRequestValidator.validate(request).alertIfErrors()
    }

    @Test
    fun `fails if quantity is not an allowed type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val request =
                    ServiceRequest(
                        status = RequestStatus.COMPLETED.asCode(),
                        intent = RequestIntent.ORDER.asCode(),
                        subject = Reference(reference = FHIRString("Patient/1234")),
                        quantity = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE),
                    )

                R4ServiceRequestValidator.validate(request).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: quantity can only be one of the following: Quantity, Ratio, Range @ ServiceRequest.quantity",
            exception.message,
        )
    }

    @Test
    fun `validates if quantity is an allowed type`() {
        val request =
            ServiceRequest(
                status = RequestStatus.COMPLETED.asCode(),
                intent = RequestIntent.ORDER.asCode(),
                subject = Reference(reference = FHIRString("Patient/1234")),
                quantity = DynamicValue(DynamicValueType.QUANTITY, Quantity(value = Decimal(BigDecimal.TEN))),
            )

        R4ServiceRequestValidator.validate(request).alertIfErrors()
    }

    @Test
    fun `fails if no subject provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val request =
                    ServiceRequest(
                        status = RequestStatus.COMPLETED.asCode(),
                        intent = RequestIntent.ORDER.asCode(),
                        subject = null,
                    )

                R4ServiceRequestValidator.validate(request).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: subject is a required element @ ServiceRequest.subject",
            exception.message,
        )
    }

    @Test
    fun `fails if occurrence is not an allowed type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val request =
                    ServiceRequest(
                        status = RequestStatus.COMPLETED.asCode(),
                        intent = RequestIntent.ORDER.asCode(),
                        subject = Reference(reference = FHIRString("Patient/1234")),
                        occurrence = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE),
                    )

                R4ServiceRequestValidator.validate(request).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: occurrence can only be one of the following: DateTime, Period, Timing @ ServiceRequest.occurrence",
            exception.message,
        )
    }

    @Test
    fun `validates if occurrence is an allowed type`() {
        val request =
            ServiceRequest(
                status = RequestStatus.COMPLETED.asCode(),
                intent = RequestIntent.ORDER.asCode(),
                subject = Reference(reference = FHIRString("Patient/1234")),
                occurrence = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2023")),
            )

        R4ServiceRequestValidator.validate(request).alertIfErrors()
    }

    @Test
    fun `fails if asNeeded is not an allowed type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val request =
                    ServiceRequest(
                        status = RequestStatus.COMPLETED.asCode(),
                        intent = RequestIntent.ORDER.asCode(),
                        subject = Reference(reference = FHIRString("Patient/1234")),
                        asNeeded = DynamicValue(DynamicValueType.STRING, FHIRString("true")),
                    )

                R4ServiceRequestValidator.validate(request).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: asNeeded can only be one of the following: Boolean, CodeableConcept @ ServiceRequest.asNeeded",
            exception.message,
        )
    }

    @Test
    fun `fails if orderDetail is present and code is not`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val request =
                    ServiceRequest(
                        status = RequestStatus.COMPLETED.asCode(),
                        intent = RequestIntent.ORDER.asCode(),
                        subject = Reference(reference = FHIRString("Patient/1234")),
                        orderDetail =
                            listOf(
                                CodeableConcept(text = FHIRString("order-detail")),
                            ),
                    )

                R4ServiceRequestValidator.validate(request).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_PRR_001: orderDetail SHALL only be present if code is present @ ServiceRequest.orderDetail",
            exception.message,
        )
    }

    @Test
    fun `validates if orderDetail and code are present`() {
        val request =
            ServiceRequest(
                status = RequestStatus.COMPLETED.asCode(),
                intent = RequestIntent.ORDER.asCode(),
                subject = Reference(reference = FHIRString("Patient/1234")),
                orderDetail =
                    listOf(
                        CodeableConcept(text = FHIRString("order-detail")),
                    ),
                code = CodeableConcept(text = FHIRString("code")),
            )

        R4ServiceRequestValidator.validate(request).alertIfErrors()
    }

    @Test
    fun `validates if asNeeded is an allowed type`() {
        val request =
            ServiceRequest(
                status = RequestStatus.COMPLETED.asCode(),
                intent = RequestIntent.ORDER.asCode(),
                subject = Reference(reference = FHIRString("Patient/1234")),
                asNeeded = DynamicValue(DynamicValueType.BOOLEAN, FHIRBoolean.TRUE),
            )

        R4ServiceRequestValidator.validate(request).alertIfErrors()
    }

    @Test
    fun `validates successfully`() {
        val request =
            ServiceRequest(
                status = RequestStatus.COMPLETED.asCode(),
                intent = RequestIntent.ORDER.asCode(),
                subject = Reference(reference = FHIRString("Patient/1234")),
            )

        R4ServiceRequestValidator.validate(request).alertIfErrors()
    }
}
