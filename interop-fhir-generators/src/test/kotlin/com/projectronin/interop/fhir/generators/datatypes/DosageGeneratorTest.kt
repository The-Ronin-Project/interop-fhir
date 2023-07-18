package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DosageGeneratorTest {
    @Test
    fun `function works with default`() {
        val dosage = dosage {}
        assertNull(dosage.id)
        assertEquals(emptyList<Extension>(), dosage.extension)
        assertEquals(emptyList<Extension>(), dosage.modifierExtension)
        assertNull(dosage.sequence)
        assertNull(dosage.text)
        assertEquals(emptyList<Extension>(), dosage.additionalInstruction)
        assertNull(dosage.patientInstruction)
        assertNull(dosage.timing)
        assertNull(dosage.asNeeded)
        assertNull(dosage.site)
        assertNull(dosage.route)
        assertNull(dosage.method)
        assertEquals(emptyList<Extension>(), dosage.doseAndRate)
        assertNull(dosage.maxDosePerPeriod)
        assertNull(dosage.maxDosePerAdministration)
        assertNull(dosage.maxDosePerLifetime)
    }

    @Test
    fun `function works with parameters`() {
        val dosage = dosage {
            sequence of FHIRInteger(3)
            text of "instruction"
            additionalInstruction of listOf(
                codeableConcept { text of "additional" }
            )
            patientInstruction of "patient step"
            timing of timing {
                code of codeableConcept { text of "code" }
            }
            asNeeded of DynamicValues.boolean(true)
            site of codeableConcept { text of "site" }
            route of codeableConcept { text of "route" }
            method of codeableConcept { text of "method" }
            doseAndRate of listOf(
                doseAndRate {
                    type of codeableConcept { text of "type 1" }
                },
                doseAndRate {
                    type of codeableConcept { text of "type 2" }
                }
            )
            maxDosePerPeriod of Ratio(
                numerator = Quantity(
                    value = Decimal(BigDecimal(1.0)),
                    unit = "cm".asFHIR()
                ),
                denominator = Quantity(
                    value = Decimal(BigDecimal(2.0)),
                    unit = "cm".asFHIR()
                )
            )
            maxDosePerAdministration of SimpleQuantity(
                value = Decimal(BigDecimal(2.0)),
                unit = "cm".asFHIR()
            )
            maxDosePerLifetime of SimpleQuantity(
                value = Decimal(BigDecimal(20.0)),
                unit = "cm".asFHIR()
            )
        }
        assertEquals(3, dosage.sequence?.value)
        assertEquals("instruction", dosage.text?.value)
        assertEquals("additional", dosage.additionalInstruction.first().text?.value)
        assertEquals("patient step", dosage.patientInstruction?.value)
        assertEquals("code", dosage.timing?.code?.text?.value)
        val actualAsNeeded = dosage.asNeeded?.value as FHIRBoolean
        assertEquals(true, actualAsNeeded.value)
        assertEquals("site", dosage.site?.text?.value)
        assertEquals("route", dosage.route?.text?.value)
        assertEquals("method", dosage.method?.text?.value)
        assertEquals(2, dosage.doseAndRate.size)
        assertEquals("type 1", dosage.doseAndRate[0].type?.text?.value)
        assertEquals("type 2", dosage.doseAndRate[1].type?.text?.value)
        assertEquals(
            Ratio(
                numerator = Quantity(
                    value = Decimal(BigDecimal(1.0)),
                    unit = "cm".asFHIR()
                ),
                denominator = Quantity(
                    value = Decimal(BigDecimal(2.0)),
                    unit = "cm".asFHIR()
                )
            ),
            dosage.maxDosePerPeriod
        )
        assertEquals(
            SimpleQuantity(
                value = Decimal(BigDecimal(2.0)),
                unit = "cm".asFHIR()
            ),
            dosage.maxDosePerAdministration
        )
        assertEquals(
            SimpleQuantity(
                value = Decimal(BigDecimal(20.0)),
                unit = "cm".asFHIR()
            ),
            dosage.maxDosePerLifetime
        )
    }
}

class DoseAndRateGeneratorTest {
    @Test
    fun `function works with default`() {
        val doseAndRate = doseAndRate {}
        assertNull(doseAndRate.id)
        assertEquals(emptyList<Extension>(), doseAndRate.extension)
        assertNull(doseAndRate.type)
        assertNull(doseAndRate.dose)
        assertNull(doseAndRate.rate)
    }

    @Test
    fun `function works with parameters`() {
        val doseAndRate = doseAndRate {
            type of codeableConcept { text of "type" }
            dose of DynamicValues.range(
                Range(
                    low = SimpleQuantity(
                        value = Decimal(BigDecimal(2.0)),
                        unit = "cm".asFHIR()
                    ),
                    high = SimpleQuantity(
                        value = Decimal(BigDecimal(3.0)),
                        unit = "cm".asFHIR()
                    )
                )
            )
            rate of DynamicValues.quantity(
                Quantity(
                    value = Decimal(BigDecimal(7.0)),
                    unit = "cm".asFHIR()
                )
            )
        }
        assertEquals("type", doseAndRate.type?.text?.value)
        val actualDose = doseAndRate.dose?.value as Range
        assertEquals(
            Range(
                low = SimpleQuantity(
                    value = Decimal(BigDecimal(2.0)),
                    unit = "cm".asFHIR()
                ),
                high = SimpleQuantity(
                    value = Decimal(BigDecimal(3.0)),
                    unit = "cm".asFHIR()
                )
            ),
            actualDose
        )
        val actualRate = doseAndRate.rate?.value as Quantity
        assertEquals(
            Quantity(
                value = Decimal(BigDecimal(7.0)),
                unit = "cm".asFHIR()
            ),
            actualRate
        )
    }
}
