package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.QuantityComparator
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class AgeTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val age =
            Age(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                value = Decimal(17.0),
                comparator = QuantityComparator.GREATER_OR_EQUAL_TO.asCode(),
                unit = FHIRString("years"),
                system = CodeSystem.UCUM.uri,
                code = Code("a"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(age)

        val expectedJson =
            """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "value" : 17.0,
            |  "comparator" : ">=",
            |  "unit" : "years",
            |  "system" : "http://unitsofmeasure.org",
            |  "code" : "a"
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)

        val deserializedAge = objectMapper.readValue<Age>(json)
        assertEquals(age, deserializedAge)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val age =
            Age(
                id = FHIRString("12345"),
                extension =
                    listOf(
                        Extension(
                            url = Uri("http://localhost/extension"),
                            value = DynamicValue(DynamicValueType.STRING, FHIRString("Value")),
                        ),
                    ),
                value = Decimal(17.0),
                system = CodeSystem.UCUM.uri,
                code = Code("a"),
            )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(age)

        val expectedJson =
            """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "value" : 17.0,
            |  "system" : "http://unitsofmeasure.org",
            |  "code" : "a"
            |}
            """.trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json =
            """
            |{
            |  "comparator" : ">="
            |}
            """.trimMargin()
        val age = objectMapper.readValue<Age>(json)

        assertNull(age.id)
        assertEquals(listOf<Extension>(), age.extension)
        assertNull(age.value)
        assertEquals(QuantityComparator.GREATER_OR_EQUAL_TO.asCode(), age.comparator)
        assertNull(age.unit)
        assertNull(age.system)
        assertNull(age.code)
    }
}
