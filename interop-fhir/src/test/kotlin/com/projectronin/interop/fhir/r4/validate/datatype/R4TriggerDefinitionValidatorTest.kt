package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DataRequirement
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Expression
import com.projectronin.interop.fhir.r4.datatype.TriggerDefinition
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.valueset.TriggerType
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4TriggerDefinitionValidatorTest {
    @Test
    fun `fails for no type provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = null,
                    name = FHIRString("any"),
                    data = listOf(DataRequirement(type = Code("data-type-code")))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: type is a required element @ TriggerDefinition.type",
            exception.message
        )
    }

    @Test
    fun `fails for type is outside of required value set`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = Code("unsupported-type"),
                    name = FHIRString("any"),
                    data = listOf(DataRequirement(type = Code("data-type-code")))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unsupported-type' is outside of required value set @ TriggerDefinition.type",
            exception.message
        )
    }

    @Test
    fun `fails when both timing and data requirement are provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.PERIODIC.asCode(),
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22")),
                    data = listOf(DataRequirement(type = Code("type")))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_001: Either timing, or a data requirement, but not both @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `fails when neither timing nor data requirement are provided`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.NAMED_EVENT.asCode(),
                    name = FHIRString("any")
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_001: Either timing, or a data requirement, but not both @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported dynamic value type`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.PERIODIC.asCode(),
                    timing = DynamicValue(DynamicValueType.INTEGER, FHIRInteger(1))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: timing can only be one of the following: Timing, Reference, Date, DateTime @ TriggerDefinition.timing",
            exception.message
        )
    }

    @Test
    fun `fails for condition only if there is a data requirement`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.PERIODIC.asCode(),
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22")),
                    condition = Expression(expression = FHIRString("where"), language = Code("py"))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_002: A condition only if there is a data requirement @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `fails for named event without a name`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.NAMED_EVENT.asCode(),
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22"))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_003: A named event requires a name @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `fails for periodic event without a timing`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.PERIODIC.asCode(),
                    data = listOf(DataRequirement(type = Code("type")))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_004: A periodic event requires timing @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `fails for data accessed without data`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.DATA_ACCESSED.asCode(),
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22"))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_005: A data event requires data @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `fails for data added without data`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.DATA_ADDED.asCode(),
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22"))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_005: A data event requires data @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `fails for data changed without data`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.DATA_CHANGED.asCode(),
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22"))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_005: A data event requires data @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `fails for data access ended without data`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.DATA_ACCESS_ENDED.asCode(),
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22"))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_005: A data event requires data @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `fails for data modified without data`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.DATA_MODIFIED.asCode(),
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22"))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_005: A data event requires data @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `fails for data removed without data`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val triggerDefinition = TriggerDefinition(
                    type = TriggerType.DATA_REMOVED.asCode(),
                    timing = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2022-02-22"))
                )
                R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_TRGDEF_005: A data event requires data @ TriggerDefinition",
            exception.message
        )
    }

    @Test
    fun `validates successfully with data`() {
        val triggerDefinition = TriggerDefinition(
            type = TriggerType.NAMED_EVENT.asCode(),
            name = FHIRString("any"),
            data = listOf(DataRequirement(type = Code("data-type-code")))
        )
        R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
    }

    @Test
    fun `validates successfully with condition`() {
        val triggerDefinition = TriggerDefinition(
            type = TriggerType.NAMED_EVENT.asCode(),
            name = FHIRString("any"),
            data = listOf(DataRequirement(type = Code("data-type-code"))),
            condition = Expression(expression = FHIRString("where"), language = Code("py"))
        )
        R4TriggerDefinitionValidator.validate(triggerDefinition).alertIfErrors()
    }
}
