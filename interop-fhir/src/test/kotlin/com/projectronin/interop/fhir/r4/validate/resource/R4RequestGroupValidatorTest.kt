package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.interop.fhir.r4.resource.RequestGroup
import com.projectronin.interop.fhir.r4.resource.RequestGroupAction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4RequestGroupValidatorTest {
    @Test
    fun `validates successfully`() {
        val requestGroup =
            RequestGroup(
                status = Code("draft"),
                intent = Code("proposal"),
                priority = Code("routine"),
            )
        R4RequestGroupValidator.validate(requestGroup).alertIfErrors()
    }

    @Test
    fun `fails if status is null`() {
        val requestGroup =
            RequestGroup(
                status = null,
                intent = Code("proposal"),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupValidator.validate(requestGroup).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ RequestGroup.status",
            exception.message,
        )
    }

    @Test
    fun `fails if status is outside range`() {
        val requestGroup =
            RequestGroup(
                status = Code("wrong"),
                intent = Code("proposal"),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupValidator.validate(requestGroup).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'wrong' is outside of required value set @ RequestGroup.status",
            exception.message,
        )
    }

    @Test
    fun `fails if intent is null`() {
        val requestGroup =
            RequestGroup(
                status = Code("draft"),
                intent = null,
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupValidator.validate(requestGroup).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: intent is a required element @ RequestGroup.intent",
            exception.message,
        )
    }

    @Test
    fun `fails if intent is outside range`() {
        val requestGroup =
            RequestGroup(
                status = Code("draft"),
                intent = Code("wrong"),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupValidator.validate(requestGroup).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'wrong' is outside of required value set @ RequestGroup.intent",
            exception.message,
        )
    }

    @Test
    fun `fails if priority is outside range`() {
        val requestGroup =
            RequestGroup(
                status = Code("draft"),
                intent = Code("proposal"),
                priority = Code("wrong"),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupValidator.validate(requestGroup).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'wrong' is outside of required value set @ RequestGroup.priority",
            exception.message,
        )
    }
}

@Suppress("ktlint:standard:max-line-length")
class R4RequestGroupActionValidatorTest {
    @Test
    fun `validates successfully`() {
        val action =
            RequestGroupAction(
                priority = Code("routine"),
                timing = DynamicValue(DynamicValueType.DATE_TIME, value = DateTime("2023-05-02")),
                groupingBehavior = Code("visual-group"),
                selectionBehavior = Code("any"),
                requiredBehavior = Code("must"),
                precheckBehavior = Code("yes"),
                cardinalityBehavior = Code("single"),
                action = listOf(RequestGroupAction(resource = Reference(display = "action2".asFHIR()))),
            )
        R4RequestGroupActionValidator.validate(action).alertIfErrors()
    }

    @Test
    fun `fails if priority is outside range`() {
        val action =
            RequestGroupAction(
                priority = Code("wrong"),
                action = listOf(RequestGroupAction(resource = Reference(display = "action2".asFHIR()))),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupActionValidator.validate(action).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'wrong' is outside of required value set @ RequestGroupAction.priority",
            exception.message,
        )
    }

    @Test
    fun `fails if groupingBehavior is outside range`() {
        val action =
            RequestGroupAction(
                groupingBehavior = Code("wrong"),
                action = listOf(RequestGroupAction(resource = Reference(display = "action2".asFHIR()))),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupActionValidator.validate(action).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'wrong' is outside of required value set @ RequestGroupAction.groupingBehavior",
            exception.message,
        )
    }

    @Test
    fun `fails if selectionBehavior is outside range`() {
        val action =
            RequestGroupAction(
                selectionBehavior = Code("wrong"),
                action = listOf(RequestGroupAction(resource = Reference(display = "action2".asFHIR()))),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupActionValidator.validate(action).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'wrong' is outside of required value set @ RequestGroupAction.selectionBehavior",
            exception.message,
        )
    }

    @Test
    fun `fails if precheckBehavior is outside range`() {
        val action =
            RequestGroupAction(
                precheckBehavior = Code("wrong"),
                action = listOf(RequestGroupAction(resource = Reference(display = "action2".asFHIR()))),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupActionValidator.validate(action).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'wrong' is outside of required value set @ RequestGroupAction.precheckBehavior",
            exception.message,
        )
    }

    @Test
    fun `fails if requiredBehavior is outside range`() {
        val action =
            RequestGroupAction(
                requiredBehavior = Code("wrong"),
                action = listOf(RequestGroupAction(resource = Reference(display = "action2".asFHIR()))),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupActionValidator.validate(action).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'wrong' is outside of required value set @ RequestGroupAction.requiredBehavior",
            exception.message,
        )
    }

    @Test
    fun `fails if cardinalityBehavior is outside range`() {
        val action =
            RequestGroupAction(
                cardinalityBehavior = Code("wrong"),
                action = listOf(RequestGroupAction(resource = Reference(display = "action2".asFHIR()))),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupActionValidator.validate(action).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'wrong' is outside of required value set @ RequestGroupAction.cardinalityBehavior",
            exception.message,
        )
    }

    @Test
    fun `fails if timing is wrong type`() {
        val action =
            RequestGroupAction(
                timing = DynamicValue(DynamicValueType.BOOLEAN, value = false.asFHIR()),
                action = listOf(RequestGroupAction(resource = Reference(display = "action2".asFHIR()))),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupActionValidator.validate(action).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: timing can only be one of the following: DateTime, Age, Period, Duration, Range, Timing @ RequestGroupAction.timing",
            exception.message,
        )
    }

    @Test
    fun `fails if action and resource are passed`() {
        val action =
            RequestGroupAction(
                resource = Reference(display = "cash money".asFHIR()),
                action = listOf(RequestGroupAction(resource = Reference(display = "action2".asFHIR()))),
            )
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupActionValidator.validate(action).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_REQGRAC_001: Must have resource or action but not both @ RequestGroupAction",
            exception.message,
        )
    }

    @Test
    fun `fails if neither action and resource are passed`() {
        val action = RequestGroupAction()
        val exception =
            assertThrows<IllegalArgumentException> {
                R4RequestGroupActionValidator.validate(action).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_REQGRAC_001: Must have resource or action but not both @ RequestGroupAction",
            exception.message,
        )
    }

    @Test
    fun `succeeds if just resource passed`() {
        val action =
            RequestGroupAction(
                resource = Reference(display = "cash money".asFHIR()),
            )

        R4RequestGroupActionValidator.validate(action).alertIfErrors()
    }
}
