package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.DynamicValues.dateTime
import com.projectronin.interop.fhir.generators.datatypes.annotation
import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.coding
import com.projectronin.interop.fhir.generators.primitives.markdown
import com.projectronin.interop.fhir.generators.primitives.of
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ProcedureGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val procedure = procedure {}
        assertNull(procedure.id)
        assertNull(procedure.meta)
        assertNull(procedure.implicitRules)
        assertNull(procedure.language)
        assertNull(procedure.text)
        assertEquals(0, procedure.contained.size)
        assertEquals(0, procedure.extension.size)
        assertEquals(0, procedure.modifierExtension.size)
        assertEquals(0, procedure.identifier.size)
        assertEquals(0, procedure.instantiatesCanonical.size)
        assertEquals(0, procedure.instantiatesUri.size)
        assertEquals(0, procedure.basedOn.size)
        assertEquals(0, procedure.partOf.size)
        assertNotNull(procedure.status)
        assertNull(procedure.statusReason)
        assertNull(procedure.category)
        assertNull(procedure.code)
        assertNotNull(procedure.subject)
        assertNull(procedure.encounter)
        assertNull(procedure.performed)
        assertNull(procedure.recorder)
        assertNull(procedure.asserter)
        assertEquals(0, procedure.performer.size)
        assertNull(procedure.location)
        assertEquals(0, procedure.reasonCode.size)
        assertEquals(0, procedure.reasonReference.size)
        assertEquals(0, procedure.bodySite.size)
        assertNull(procedure.outcome)
        assertEquals(0, procedure.report.size)
        assertEquals(0, procedure.complication.size)
        assertEquals(0, procedure.complicationDetail.size)
        assertEquals(0, procedure.followUp.size)
        assertEquals(0, procedure.note.size)
        assertEquals(0, procedure.focalDevice.size)
        assertEquals(0, procedure.usedReference.size)
        assertEquals(0, procedure.usedCode.size)
    }

    @Test
    fun `function works with parameters`() {
        val procedure =
            procedure {
                category of
                    codeableConcept {
                        coding plus
                            coding {
                                system of "http://snomed.info/sct"
                                code of "103693007"
                                display of "Diagnostic procedure (procedure)"
                            }
                    }
                code of
                    codeableConcept {
                        coding plus
                            coding {
                                system of "http://snomed.info/sct"
                                code of "90105005"
                                display of "Biopsy of soft tissue of forearm (Procedure)"
                            }
                    }
                performed of dateTime("2014-02-03")
                reasonCode plus
                    codeableConcept {
                        text of "Dark lesion l) forearm. getting darker last 3 months."
                    }
                bodySite plus
                    codeableConcept {
                        coding plus
                            coding {
                                system of "http://snomed.info/sct"
                                code of "368225008"
                                display of "Entire Left Forearm"
                            }
                    }
                followUp plus
                    codeableConcept {
                        text of "Review in clinic"
                    }
                note plus
                    annotation {
                        text of
                            markdown {
                                value of "Standard Biopsy"
                            }
                    }
            }

        assertNull(procedure.id)
        assertNull(procedure.meta)
        assertNull(procedure.implicitRules)
        assertNull(procedure.language)
        assertNull(procedure.text)
        assertEquals(0, procedure.contained.size)
        assertEquals(0, procedure.extension.size)
        assertEquals(0, procedure.modifierExtension.size)
        assertEquals(0, procedure.identifier.size)
        assertEquals(0, procedure.instantiatesCanonical.size)
        assertEquals(0, procedure.instantiatesUri.size)
        assertEquals(0, procedure.basedOn.size)
        assertEquals(0, procedure.partOf.size)
        assertNotNull(procedure.status)
        assertNull(procedure.statusReason)
        assertNotNull(procedure.category)
        assertNotNull(procedure.code)
        assertNotNull(procedure.subject)
        assertNull(procedure.encounter)
        assertNotNull(procedure.performed)
        assertNull(procedure.recorder)
        assertNull(procedure.asserter)
        assertEquals(0, procedure.performer.size)
        assertNull(procedure.location)
        assertEquals(1, procedure.reasonCode.size)
        assertEquals(0, procedure.reasonReference.size)
        assertEquals(1, procedure.bodySite.size)
        assertNull(procedure.outcome)
        assertEquals(0, procedure.report.size)
        assertEquals(0, procedure.complication.size)
        assertEquals(0, procedure.complicationDetail.size)
        assertEquals(1, procedure.followUp.size)
        assertEquals(1, procedure.note.size)
        assertEquals(0, procedure.focalDevice.size)
        assertEquals(0, procedure.usedReference.size)
        assertEquals(0, procedure.usedCode.size)
    }
}

class ProcedurePerformerGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val performer = procedurePerformer {}
        assertNull(performer.id)
        assertEquals(0, performer.extension.size)
        assertEquals(0, performer.modifierExtension.size)
        assertNull(performer.function)
        assertNotNull(performer.actor)
        assertNull(performer.onBehalfOf)
    }

    @Test
    fun `function works with parameters`() {
        val performer =
            procedurePerformer {
                function of codeableConcept { }
            }

        assertNull(performer.id)
        assertEquals(0, performer.extension.size)
        assertEquals(0, performer.modifierExtension.size)
        assertNotNull(performer.function)
        assertNotNull(performer.actor)
        assertNull(performer.onBehalfOf)
    }
}

class ProcedureFocalDeviceGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val focalDevice = procedureFocalDevice {}
        assertNull(focalDevice.id)
        assertEquals(0, focalDevice.extension.size)
        assertEquals(0, focalDevice.modifierExtension.size)
        assertNull(focalDevice.action)
        assertNotNull(focalDevice.manipulated)
    }

    @Test
    fun `function works with parameters`() {
        val focalDevice =
            procedureFocalDevice {
                action of codeableConcept { }
            }

        assertNull(focalDevice.id)
        assertEquals(0, focalDevice.extension.size)
        assertEquals(0, focalDevice.modifierExtension.size)
        assertNotNull(focalDevice.action)
        assertNotNull(focalDevice.manipulated)
    }
}
