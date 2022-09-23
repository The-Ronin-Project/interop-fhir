package com.projectronin.interop.fhir.stu3.datatype

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DoseAndRate
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.Timing
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class STU3DosageTest {
    private val validSTU3DosageDoseAndRate = STU3Dosage(
        id = "12345",
        extension = listOf(
            Extension(
                url = Uri("http://localhost/extension"),
                value = DynamicValue(DynamicValueType.STRING, "Value")
            )
        ),
        sequence = 2,
        text = "Dosage 2",
        additionalInstruction = listOf(CodeableConcept(text = "additional instruction")),
        patientInstruction = "Take BID",
        timing = Timing(event = listOf(DateTime("2021-12-25"))),
        asNeeded = DynamicValue(DynamicValueType.BOOLEAN, false),
        site = CodeableConcept(text = "dosage site"),
        route = CodeableConcept(text = "dosage route"),
        method = CodeableConcept(text = "dosage method"),
        dose = DynamicValue(
            type = DynamicValueType.QUANTITY,
            value = Quantity(
                value = 40.0,
                unit = "mg",
                system = Uri("http://unitsofmeasure.org"),
                code = Code("mg")
            )
        ),
        rate = DynamicValue(
            type = DynamicValueType.QUANTITY,
            value = Quantity(value = 2.0),
        ),
        maxDosePerPeriod = Ratio(numerator = Quantity(value = 2.0), denominator = Quantity(value = 5.0)),
        maxDosePerAdministration = SimpleQuantity(value = 20.0),
        maxDosePerLifetime = SimpleQuantity(value = 120.0)
    )

    @Test
    fun `can serialize and deserialize JSON`() {
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(validSTU3DosageDoseAndRate)

        val expectedJson = """
            |{
            |  "id" : "12345",
            |  "extension" : [ {
            |    "url" : "http://localhost/extension",
            |    "valueString" : "Value"
            |  } ],
            |  "sequence" : 2,
            |  "text" : "Dosage 2",
            |  "additionalInstruction" : [ {
            |    "text" : "additional instruction"
            |  } ],
            |  "patientInstruction" : "Take BID",
            |  "timing" : {
            |    "event" : [ "2021-12-25" ]
            |  },
            |  "asNeededBoolean" : false,
            |  "site" : {
            |    "text" : "dosage site"
            |  },
            |  "route" : {
            |    "text" : "dosage route"
            |  },
            |  "method" : {
            |    "text" : "dosage method"
            |  },
            |  "doseQuantity" : {
            |    "value" : 40.0,
            |    "unit" : "mg",
            |    "system" : "http://unitsofmeasure.org",
            |    "code" : "mg"
            |  },
            |  "rateQuantity" : {
            |    "value" : 2.0
            |  },
            |  "maxDosePerPeriod" : {
            |    "numerator" : {
            |      "value" : 2.0
            |    },
            |    "denominator" : {
            |      "value" : 5.0
            |    }
            |  },
            |  "maxDosePerAdministration" : {
            |    "value" : 20.0
            |  },
            |  "maxDosePerLifetime" : {
            |    "value" : 120.0
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)

        val deserializedDosage = objectMapper.readValue<STU3Dosage>(json)
        assertEquals(validSTU3DosageDoseAndRate, deserializedDosage)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val dosage = STU3Dosage(
            dose = DynamicValue(DynamicValueType.QUANTITY, Quantity(value = 3.0))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dosage)

        val expectedJson = """
            |{
            |  "doseQuantity" : {
            |    "value" : 3.0
            |  }
            |}""".trimMargin()
        assertEquals(expectedJson, json)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            |{
            |  "asNeededBoolean" : true
            |}""".trimMargin()
        val dosage = objectMapper.readValue<STU3Dosage>(json)

        assertNull(dosage.id)
        assertEquals(listOf<Extension>(), dosage.extension)
        assertNull(dosage.sequence)
        assertNull(dosage.text)
        assertEquals(listOf<CodeableConcept>(), dosage.additionalInstruction)
        assertNull(dosage.patientInstruction)
        assertNull(dosage.timing)
        assertEquals(DynamicValue(DynamicValueType.BOOLEAN, true), dosage.asNeeded)
        assertNull(dosage.site)
        assertNull(dosage.route)
        assertNull(dosage.method)
        assertNull(dosage.dose)
        assertNull(dosage.rate)
        assertNull(dosage.maxDosePerPeriod)
        assertNull(dosage.maxDosePerAdministration)
        assertNull(dosage.maxDosePerLifetime)
    }

    @Test
    fun `transform to R4 - works - Dosage has dose and rate`() {
        val r4Dosage = validSTU3DosageDoseAndRate.transformToR4()

        assertNull(r4Dosage.doseAndRate.first().type)
        assertEquals(validSTU3DosageDoseAndRate.dose, r4Dosage.doseAndRate.first().dose)
        assertEquals(validSTU3DosageDoseAndRate.rate, r4Dosage.doseAndRate.first().rate)
    }

    @Test
    fun `transform to R4 - works - Dosage has dose only`() {
        val stu3Dosage = validSTU3DosageDoseAndRate.copy(rate = null)
        val r4Dosage = stu3Dosage.transformToR4()

        assertNull(r4Dosage.doseAndRate.first().type)
        assertEquals(stu3Dosage.dose, r4Dosage.doseAndRate.first().dose)
        assertNull(r4Dosage.doseAndRate.first().rate)
    }

    @Test
    fun `transform to R4 - works - Dosage has rate only`() {
        val stu3Dosage = validSTU3DosageDoseAndRate.copy(dose = null)
        val r4Dosage = stu3Dosage.transformToR4()

        assertNull(r4Dosage.doseAndRate.first().type)
        assertNull(r4Dosage.doseAndRate.first().dose)
        assertEquals(stu3Dosage.rate, r4Dosage.doseAndRate.first().rate)
    }

    @Test
    fun `transform to R4 - works - Dosage has neither dose nor rate`() {
        val stu3Dosage = validSTU3DosageDoseAndRate.copy(dose = null, rate = null)
        val r4Dosage = stu3Dosage.transformToR4()

        assertEquals(emptyList<DoseAndRate>(), r4Dosage.doseAndRate)
    }
}
