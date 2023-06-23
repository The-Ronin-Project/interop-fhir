package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.annotation
import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.coding
import com.projectronin.interop.fhir.generators.datatypes.identifier
import com.projectronin.interop.fhir.generators.datatypes.period
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.datatypes.simpleQuantity
import com.projectronin.interop.fhir.generators.primitives.dateTime
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CarePlanGeneratorTest {
    @Test
    fun `function works with defaults`() {
        val carePlan = carePlan {}
        assertNull(carePlan.id)
        assertNull(carePlan.meta)
        assertNull(carePlan.implicitRules)
        assertNull(carePlan.language)
        assertNull(carePlan.text)
        assertEquals(0, carePlan.contained.size)
        assertEquals(0, carePlan.extension.size)
        assertEquals(0, carePlan.modifierExtension.size)
        assertTrue(carePlan.identifier.isEmpty())
        assertTrue(carePlan.instantiatesCanonical.isEmpty())
        assertTrue(carePlan.instantiatesUri.isEmpty())
        assertTrue(carePlan.basedOn.isEmpty())
        assertTrue(carePlan.replaces.isEmpty())
        assertTrue(carePlan.partOf.isEmpty())
        assertNotNull(carePlan.status)
        assertTrue(carePlan.status?.value?.isNotEmpty() == true)
        assertNotNull(carePlan.intent)
        assertTrue(carePlan.intent?.value?.isNotEmpty() == true)
        assertTrue(carePlan.category.isEmpty())
        assertNull(carePlan.title)
        assertNull(carePlan.description)
        assertNotNull(carePlan.subject)
        assertNull(carePlan.encounter)
        assertNull(carePlan.period)
        assertNull(carePlan.created)
        assertNull(carePlan.author)
        assertTrue(carePlan.contributor.isEmpty())
        assertTrue(carePlan.careTeam.isEmpty())
        assertTrue(carePlan.addresses.isEmpty())
        assertTrue(carePlan.supportingInfo.isEmpty())
        assertTrue(carePlan.goal.isEmpty())
        assertTrue(carePlan.activity.isEmpty())
        assertTrue(carePlan.note.isEmpty())
    }

    @Test
    fun `function works with parameters`() {
        val carePlan = carePlan {
            id of Id("id")
            identifier of listOf(
                identifier {
                    value of "identifier"
                }
            )
            instantiatesCanonical of listOf(
                Canonical("url")
            )
            instantiatesUri of listOf(
                Uri("uri")
            )
            basedOn of listOf(
                reference("CarePlan", "1234")
            )
            replaces of listOf(
                reference("CarePlan", "5678")
            )
            partOf of listOf(
                reference("CarePlan", "9012")
            )
            status of "status"
            intent of "intent"
            category of listOf(
                codeableConcept {
                    coding of listOf(
                        coding {
                            system of "categorySystem"
                            code of "categoryCode"
                        }
                    )
                }
            )
            title of "titleText"
            description of "descriptionText"
            subject of reference("Patient", "123")
            encounter of reference("Encounter", "1234")
            period of period {
                start of dateTime {
                    year of 1990
                    day of 9
                    month of 4
                }
                end of dateTime {
                    year of 1990
                    day of 10
                    month of 4
                }
            }
            created of dateTime {
                year of 1990
                day of 8
                month of 4
            }
            author of reference("Practitioner", "1234")
            contributor of listOf(
                reference("Practitioner", "5678")
            )
            careTeam of listOf(
                reference("Practitioner", "5678"),
                reference("Practitioner", "9012")
            )
            addresses of listOf(
                reference("Condition", "1234")
            )
            supportingInfo of listOf(
                reference("Observation", "1234")
            )
            goal of listOf(
                reference("Goal", "1234")
            )
            activity of listOf(
                carePlanActivity {
                    outcomeReference of listOf(
                        reference("Procedure", "1234"),
                        reference("Appointment", "5678")
                    )
                    progress of listOf(
                        annotation { text of Markdown("progressText") }
                    )
                    reference of reference("Appointment", "1234")
                }
            )
            note of listOf(
                annotation {
                    text of Markdown("noteText")
                }
            )
        }
        assertEquals("id", carePlan.id?.value)
        assertEquals(1, carePlan.identifier.size)
        assertEquals("identifier", carePlan.identifier.first().value?.value)
        assertEquals(Canonical("url"), carePlan.instantiatesCanonical.first())
        assertEquals(Uri("uri"), carePlan.instantiatesUri.first())
        assertEquals("CarePlan/1234", carePlan.basedOn.first().reference?.value)
        assertEquals(
            "CarePlan/5678",
            carePlan.replaces.first().reference?.value
        )
        assertEquals("CarePlan/9012", carePlan.partOf.first().reference?.value)
        assertEquals("status", carePlan.status?.value)
        assertEquals("intent", carePlan.intent?.value)
        assertEquals(
            Coding(code = Code("categoryCode"), system = Uri("categorySystem")),
            carePlan.category.first().coding.first()
        )
        assertEquals("titleText", carePlan.title?.value)
        assertEquals("descriptionText", carePlan.description?.value)
        assertEquals("Patient/123", carePlan.subject?.reference?.value)
        assertEquals("Encounter/1234", carePlan.encounter?.reference?.value)
        assertEquals(
            Period(
                start = DateTime(value = "1990-04-09"),
                end = DateTime(value = "1990-04-10")
            ),
            carePlan.period
        )
        assertEquals(DateTime(value = "1990-04-08"), carePlan.created)
        assertEquals("Practitioner/1234", carePlan.author?.reference?.value)
        assertEquals(
            "Practitioner/5678",
            carePlan.contributor.first().reference?.value
        )
        assertEquals(
            listOf(
                Reference(reference = "Practitioner/5678".asFHIR()),
                Reference(reference = "Practitioner/9012".asFHIR())
            ),
            carePlan.careTeam
        )
        assertEquals(
            "Condition/1234",
            carePlan.addresses.first().reference?.value
        )
        assertEquals(
            "Observation/1234",
            carePlan.supportingInfo.first().reference?.value
        )
        assertEquals(
            "Goal/1234",
            carePlan.goal.first().reference?.value
        )
        assertEquals(
            listOf(
                Reference(reference = "Procedure/1234".asFHIR()),
                Reference(reference = "Appointment/5678".asFHIR())
            ),
            carePlan.activity.first().outcomeReference
        )
        assertEquals(
            Annotation(text = Markdown("progressText")),
            carePlan.activity.first().progress.first()
        )
        assertEquals(
            Reference(reference = "Appointment/1234".asFHIR()),
            carePlan.activity.first().reference
        )
        assertEquals(
            Annotation(text = Markdown("noteText")),
            carePlan.note.first()
        )
    }
}

class CarePlanActivityGeneratorTest {
    @Test
    fun `activity function works with defaults`() {
        val activity = carePlanActivity { }
        activity!!
        assertNull(activity.id)
        assertTrue(activity.extension.isEmpty())
        assertTrue(activity.modifierExtension.isEmpty())
        assertTrue(activity.outcomeCodeableConcept.isEmpty())
        assertTrue(activity.outcomeReference.isEmpty())
        assertTrue(activity.progress.isEmpty())
        assertNull(activity.reference)
        assertNull(activity.detail)
    }

    @Test
    fun `activity function works with parameters`() {
        val activity = carePlanActivity {
            outcomeCodeableConcept of listOf(
                codeableConcept {
                    coding of listOf(
                        coding {
                            system of "system"
                            code of "code"
                        }
                    )
                }
            )
            outcomeReference of listOf(
                reference("Procedure", "1234"),
                reference("Appointment", "5678")
            )
            progress of listOf(
                annotation { text of Markdown("text") }
            )
            reference of reference("Appointment", "1234")
            detail of carePlanDetail {
                description of "details"
            }
        }

        activity!!
        assertEquals(
            listOf(Coding(code = Code("code"), system = Uri("system"))),
            activity.outcomeCodeableConcept.first().coding
        )
        assertEquals(2, activity.outcomeReference.size)
        assertEquals(
            listOf(
                Reference(reference = "Procedure/1234".asFHIR()),
                Reference(reference = "Appointment/5678".asFHIR())
            ),
            activity.outcomeReference
        )
        assertEquals(Markdown("text"), activity.progress.first().text)
        assertEquals("Appointment/1234".asFHIR(), activity.reference?.reference)
    }
}

class CarePlanDetailGeneratorTest {
    @Test
    fun `detail function works with defaults`() {
        val detail = carePlanDetail { }
        detail!!
        assertNull(detail.id)
        assertTrue(detail.extension.isEmpty())
        assertTrue(detail.modifierExtension.isEmpty())
        assertNull(detail.kind)
        assertTrue(detail.instantiatesCanonical.isEmpty())
        assertTrue(detail.instantiatesUri.isEmpty())
        assertNull(detail.code)
        assertTrue(detail.reasonCode.isEmpty())
        assertTrue(detail.reasonReference.isEmpty())
        assertTrue(detail.goal.isEmpty())
        assertNotNull(detail.status)
        assertTrue(detail.status?.value?.isNotEmpty() == true)
        assertNull(detail.statusReason)
        assertNull(detail.doNotPerform)
        assertNull(detail.scheduled)
        assertNull(detail.location)
        assertTrue(detail.performer.isEmpty())
        assertNull(detail.product)
        assertNull(detail.dailyAmount)
        assertNull(detail.quantity)
        assertNull(detail.description)
    }

    @Test
    fun `detail function works with parameters`() {
        val detail = carePlanDetail {
            kind of Code("kind")
            instantiatesCanonical of listOf(
                Canonical("url")
            )
            instantiatesUri of listOf(
                Uri("uri")
            )
            code of codeableConcept {
                coding of listOf(
                    coding {
                        system of "system"
                        code of "code"
                    }
                )
            }
            reasonCode of listOf(
                codeableConcept {
                    coding of listOf(
                        coding {
                            system of "reasonSystem"
                            code of "reasonCode"
                        }
                    )
                }
            )
            reasonReference of listOf(
                reference("Condition", "1234")
            )
            goal of listOf(
                reference("Goal", "1234")
            )
            status of "status"
            statusReason of codeableConcept {
                coding of listOf(
                    coding {
                        system of "statusSystem"
                        code of "statusCode"
                    }
                )
            }
            doNotPerform of false
            scheduled of DynamicValues.period(
                period {
                    start of dateTime {
                        year of 1990
                        day of 9
                        month of 4
                    }
                    end of dateTime {
                        year of 1990
                        day of 10
                        month of 4
                    }
                }
            )
            location of reference("Location", "1234")
            performer of listOf(
                reference("Practitioner", "1234")
            )
            product of DynamicValues.reference(reference("Medication", "1234"))
            dailyAmount of simpleQuantity {
                value of 1.23
                unit of "centimeters"
                system of Uri("dailySystem")
                code of Code("dailyCode")
            }
            quantity of simpleQuantity {
                value of 1.23
                unit of "cubits"
                system of Uri("quantitySystem")
                code of Code("quantityCode")
            }
            description of "text"
        }

        detail!!
        assertEquals(Code("kind"), detail.kind)
        assertEquals(Canonical("url"), detail.instantiatesCanonical.first())
        assertEquals(Uri("uri"), detail.instantiatesUri.first())
        assertEquals(
            Coding(code = Code("code"), system = Uri("system")),
            detail.code?.coding?.first()
        )
        assertEquals(
            Coding(code = Code("reasonCode"), system = Uri("reasonSystem")),
            detail.reasonCode.first().coding.first()
        )
        assertEquals(
            "Condition/1234".asFHIR(),
            detail.reasonReference.first().reference
        )
        assertEquals(
            "Goal/1234".asFHIR(),
            detail.goal.first().reference
        )
        assertEquals("status", detail.status?.value)
        assertEquals(
            Coding(code = Code("statusCode"), system = Uri("statusSystem")),
            detail.statusReason?.coding?.first()
        )
        assertEquals(false, detail.doNotPerform?.value)
        assertEquals(DynamicValueType.PERIOD, detail.scheduled?.type)
        assertEquals(
            Period(
                start = DateTime(value = "1990-04-09"),
                end = DateTime(value = "1990-04-10")
            ),
            detail.scheduled?.value
        )
        assertEquals(
            "Location/1234".asFHIR(),
            detail.location?.reference
        )
        assertEquals(
            "Practitioner/1234".asFHIR(),
            detail.performer.first().reference
        )
        assertEquals(DynamicValueType.REFERENCE, detail.product?.type)
        assertEquals(
            Reference(reference = "Medication/1234".asFHIR()),
            detail.product?.value
        )
        assertEquals(
            SimpleQuantity(
                value = Decimal(1.23),
                unit = "centimeters".asFHIR(),
                system = Uri("dailySystem"),
                code = Code("dailyCode")
            ),
            detail.dailyAmount
        )
        assertEquals(
            SimpleQuantity(
                value = Decimal(1.23),
                unit = "cubits".asFHIR(),
                system = Uri("quantitySystem"),
                code = Code("quantityCode")
            ),
            detail.quantity
        )
        assertEquals("text".asFHIR(), detail.description)
    }
}
