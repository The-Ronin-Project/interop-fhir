package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.r4.valueset.EventTiming
import com.projectronin.interop.fhir.r4.valueset.UnitOfTime
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class TimingTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val timing = Timing(
            id = "12345",
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, "Value")
                )
            ),
            event = listOf(DateTime("2021-11-18")),
            repeat = TimingRepeat(
                id = "67890",
                extension = listOf(
                    Extension(
                        url = Uri("http://localhost/extension"),
                        value = DynamicValue(DynamicValueType.STRING, "Value")
                    )
                ),
                bounds = DynamicValue(
                    DynamicValueType.DURATION,
                    Duration(value = 3.0, code = Code("a"), system = CodeSystem.UCUM.uri)
                ),
                count = PositiveInt(5),
                countMax = PositiveInt(10),
                duration = 14.0,
                durationMax = 30.0,
                durationUnit = UnitOfTime.DAY.asCode(),
                frequency = PositiveInt(2),
                frequencyMax = PositiveInt(10),
                period = 6.0,
                periodMax = 8.0,
                periodUnit = UnitOfTime.HOUR.asCode(),
                dayOfWeek = listOf(DayOfWeek.MONDAY.asCode()),
                `when` = listOf(EventTiming.UPON_WAKING.asCode()),
                offset = UnsignedInt(12)
            ),
            code = CodeableConcept(text = "code-concept")
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(timing)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "event" : [ "2021-11-18" ],
            |  "repeat" : {
            |    "id" : "67890",
            |    "extension" : [ {
            |      "url" : "http://localhost/extension",
            |      "valueString" : "Value"
            |    } ],
            |    "boundsDuration" : {
            |      "value" : 3.0,
            |      "system" : "http://unitsofmeasure.org",
            |      "code" : "a"
            |    },
            |    "count" : 5,
            |    "countMax" : 10,
            |    "duration" : 14.0,
            |    "durationMax" : 30.0,
            |    "durationUnit" : "d",
            |    "frequency" : 2,
            |    "frequencyMax" : 10,
            |    "period" : 6.0,
            |    "periodMax" : 8.0,
            |    "periodUnit" : "h",
            |    "dayOfWeek" : [ "mon" ],
            |    "when" : [ "WAKE" ],
            |    "offset" : 12
            |  },
            |  "code" : {
            |    "text" : "code-concept"
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedTiming = objectMapper.readValue<Timing>(json)
        assertEquals(timing, deserializedTiming)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val timing = Timing(
            repeat = TimingRepeat(
                timeOfDay = listOf(Time("12:00:00"))
            )
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(timing)

        val expectedJson = """
            |{
            |  "repeat" : {
            |    "timeOfDay" : [ "12:00:00" ]
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "repeat" : {
            |    "count" : 3
            |  }
            |}""".trimMargin()
        val timing = objectMapper.readValue<Timing>(json)

        assertNull(timing.id)
        assertEquals(listOf<Extension>(), timing.extension)
        assertEquals(listOf<DateTime>(), timing.event)
        assertEquals(TimingRepeat(count = PositiveInt(3)), timing.repeat)
        assertNull(timing.code)
    }
}
