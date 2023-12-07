package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MedicationAdministrationGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val medicationAdmin = medicationAdministration {}
        assertNull(medicationAdmin.id)
        assertNull(medicationAdmin.meta)
        assertNull(medicationAdmin.implicitRules)
        assertNull(medicationAdmin.language)
        assertNull(medicationAdmin.text)
        assertEquals(0, medicationAdmin.contained.size)
        assertEquals(0, medicationAdmin.extension.size)
        assertEquals(0, medicationAdmin.modifierExtension.size)
        assertTrue(medicationAdmin.identifier.isEmpty())
        assertTrue(medicationAdmin.instantiates.isEmpty())
        assertTrue(medicationAdmin.partOf.isEmpty())
        assertNotNull(medicationAdmin.status)
        assertTrue(medicationAdmin.statusReason.isEmpty())
        assertNull(medicationAdmin.category)
        assertNotNull(medicationAdmin.medication)
        assertNotNull(medicationAdmin.subject)
        assertNull(medicationAdmin.context)
        assertTrue(medicationAdmin.supportingInformation.isEmpty())
        assertNotNull(medicationAdmin.effective)
        assertTrue(medicationAdmin.performer.isEmpty())
        assertTrue(medicationAdmin.reasonCode.isEmpty())
        assertTrue(medicationAdmin.reasonReference.isEmpty())
        assertNull(medicationAdmin.request)
        assertTrue(medicationAdmin.device.isEmpty())
        assertTrue(medicationAdmin.note.isEmpty())
        assertNull(medicationAdmin.dosage)
        assertTrue(medicationAdmin.eventHistory.isEmpty())
    }

    @Test
    fun `function works with parameters`() {
        val medicationAdmin =
            medicationAdministration {
                id of Id("id")
                identifier of
                    listOf(
                        identifier {},
                    )
                instantiates of
                    listOf(
                        Uri("Uri"),
                    )
                partOf of
                    listOf(
                        reference("Part", "123"),
                    )
                status of "Code"
                statusReason of
                    listOf(
                        codeableConcept {
                            text of "code"
                        },
                    )
                category of
                    codeableConcept {
                        text of "code"
                    }
                medication of DynamicValues.reference(reference("Substance", "5678"))
                subject of reference("Subject", "123")
                context of reference("Context", "123")
                supportingInformation of
                    listOf(
                        reference("Information", "123"),
                    )
                effective of DynamicValues.period(Period(FHIRString("Period")))
                performer of
                    listOf(
                        medAdminPerformer {
                            id of FHIRString("id")
                        },
                    )
                reasonCode of
                    listOf(
                        codeableConcept {
                            text of "code"
                        },
                    )
                reasonReference of
                    listOf(
                        reference("Reason", "123"),
                    )
                request of reference("Request", "123")
                device of
                    listOf(
                        reference("Device", "123"),
                    )
                note of
                    listOf(
                        Annotation(text = Markdown("text")),
                    )
                dosage of
                    medAdminDosage {
                        id of FHIRString("id")
                    }
                eventHistory of
                    listOf(
                        reference("History", "123"),
                    )
            }
        assertEquals("id", medicationAdmin.id?.value)
        assertEquals(1, medicationAdmin.identifier.size)
        assertEquals(1, medicationAdmin.instantiates.size)
        assertEquals("Uri", medicationAdmin.instantiates.first().value)
        assertEquals(1, medicationAdmin.partOf.size)
        assertEquals(reference("Part", "123"), medicationAdmin.partOf.first())
        assertEquals("Code", medicationAdmin.status?.value)
        assertEquals(1, medicationAdmin.statusReason.size)
        assertEquals("code", medicationAdmin.category?.text?.value)
        assertEquals(DynamicValueType.REFERENCE, medicationAdmin.medication?.type)
        assertEquals(reference("Substance", "5678"), medicationAdmin.medication?.value)
        assertEquals(reference("Subject", "123"), medicationAdmin.subject)
        assertEquals(reference("Context", "123"), medicationAdmin.context)
        assertEquals(1, medicationAdmin.supportingInformation.size)
        assertEquals(DynamicValueType.PERIOD, medicationAdmin.effective?.type)
        assertEquals(Period(FHIRString("Period")), medicationAdmin.effective?.value)
        assertEquals(1, medicationAdmin.performer.size)
        assertEquals(FHIRString("id"), medicationAdmin.performer.first().id)
        assertEquals(1, medicationAdmin.reasonCode.size)
        assertEquals("code", medicationAdmin.reasonCode.first().text?.value)
        assertEquals(1, medicationAdmin.reasonReference.size)
        assertEquals(reference("Reason", "123"), medicationAdmin.reasonReference.first())
        assertEquals(reference("Request", "123"), medicationAdmin.request)
        assertEquals(1, medicationAdmin.device.size)
        assertEquals(reference("Device", "123"), medicationAdmin.device.first())
        assertEquals(1, medicationAdmin.note.size)
        assertEquals("text", medicationAdmin.note.first().text?.value)
        assertEquals(FHIRString("id"), medicationAdmin.dosage?.id)
        assertEquals(1, medicationAdmin.eventHistory.size)
        assertEquals(reference("History", "123"), medicationAdmin.eventHistory.first())
    }
}

class MedAdminPerformerGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val medAdminPerformer = medAdminPerformer {}
        assertNull(medAdminPerformer.id)
        assertEquals(0, medAdminPerformer.extension.size)
        assertEquals(0, medAdminPerformer.modifierExtension.size)
        assertNull(medAdminPerformer.function)
        assertNotNull(medAdminPerformer.actor)
    }

    @Test
    fun `function works with parameters`() {
        val medAdminPerformer =
            medAdminPerformer {
                function of
                    codeableConcept {
                        text of "code"
                    }
                actor of reference("Actor", "123")
            }

        assertEquals("code", medAdminPerformer.function?.text?.value)
        assertEquals(reference("Actor", "123"), medAdminPerformer.actor)
    }
}

class MedAdminDosageGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val medAdminDosage = medAdminDosage {}
        assertNull(medAdminDosage.id)
        assertEquals(0, medAdminDosage.extension.size)
        assertEquals(0, medAdminDosage.modifierExtension.size)
        assertNull(medAdminDosage.text)
        assertNull(medAdminDosage.site)
        assertNull(medAdminDosage.route)
        assertNull(medAdminDosage.method)
        assertNull(medAdminDosage.dose)
        assertNull(medAdminDosage.rate)
    }

    @Test
    fun `function works with parameters`() {
        val medAdminDosage =
            medAdminDosage {
                text of "123232345"
                site of
                    codeableConcept {
                        text of "site"
                    }
                route of
                    codeableConcept {
                        text of "route"
                    }
                method of
                    codeableConcept {
                        text of "method"
                    }
                dose of Quantity(value = Decimal(BigDecimal(4.0)))
                rate of DynamicValues.reference(reference("Rate", "1234"))
            }
        assertEquals("123232345", medAdminDosage.text?.value)
        assertEquals("site", medAdminDosage.site?.text?.value)
        assertEquals("route", medAdminDosage.route?.text?.value)
        assertEquals("method", medAdminDosage.method?.text?.value)
        assertEquals(BigDecimal(4.0), medAdminDosage.dose?.value?.value)
        assertEquals(DynamicValueType.REFERENCE, medAdminDosage.rate?.type)
        assertEquals(reference("Rate", "1234"), medAdminDosage.rate?.value)
    }
}
