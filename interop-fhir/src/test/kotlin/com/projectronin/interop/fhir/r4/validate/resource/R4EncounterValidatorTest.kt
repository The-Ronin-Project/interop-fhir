package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Encounter
import com.projectronin.interop.fhir.r4.resource.EncounterClassHistory
import com.projectronin.interop.fhir.r4.resource.EncounterDiagnosis
import com.projectronin.interop.fhir.r4.resource.EncounterLocation
import com.projectronin.interop.fhir.r4.resource.EncounterStatusHistory
import com.projectronin.interop.fhir.r4.valueset.EncounterLocationStatus
import com.projectronin.interop.fhir.r4.valueset.EncounterStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4EncounterValidatorTest {
    @Test
    fun `validate - succeeds with only required attributes`() {
        val encounter = Encounter(
            status = EncounterStatus.TRIAGED.asCode(),
            `class` = Coding(code = EncounterStatus.TRIAGED.asCode()),
        )
        R4EncounterValidator.validate(encounter).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with required and other attributes`() {
        val encounter = Encounter(
            status = EncounterStatus.ONLEAVE.asCode(),
            `class` = Coding(code = EncounterStatus.UNKNOWN.asCode()),
            priority = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = CodeSystem.SNOMED_CT.uri,
                        code = Code("103391001"),
                        display = FHIRString("Non-urgent ear, nose and throat admission")
                    )
                )
            ),
            subject = Reference(
                reference = FHIRString("Patient/f001"),
                display = FHIRString("P. van de Heuvel")
            ),
            length = Duration(
                value = Decimal(90.0),
                unit = FHIRString("min"),
                system = CodeSystem.UCUM.uri,
                code = Code("min")
            ),
            reasonCode = listOf(
                CodeableConcept(
                    coding = listOf(
                        Coding(
                            system = CodeSystem.SNOMED_CT.uri,
                            code = Code("18099001"),
                            display = FHIRString("Retropharyngeal abscess")
                        )
                    )
                )
            )
        )
        R4EncounterValidator.validate(encounter).alertIfErrors()
    }

    @Test
    fun `validate - fails for null class`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounter = Encounter(
                    status = EncounterStatus.UNKNOWN.asCode(),
                    `class` = null
                )
                R4EncounterValidator.validate(encounter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: class is a required element @ Encounter.class",
            exception.message
        )
    }

    @Test
    fun `validate - fails for null status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounter = Encounter(
                    status = null,
                    `class` = Coding(code = EncounterStatus.UNKNOWN.asCode()),
                )
                R4EncounterValidator.validate(encounter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ Encounter.status",
            exception.message
        )
    }

    @Test
    fun `validate - fails for bad status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounter = Encounter(
                    status = Code("unavailable"),
                    `class` = Coding(code = EncounterStatus.UNKNOWN.asCode()),
                )
                R4EncounterValidator.validate(encounter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unavailable' is outside of required value set @ Encounter.status",
            exception.message
        )
    }

    @Test
    fun `validate - when there are multiple failure cases, reports all`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounter = Encounter(
                    status = Code("unavailable"),
                    `class` = null
                )
                R4EncounterValidator.validate(encounter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unavailable' is outside of required value set @ Encounter.status\n" +
                "ERROR REQ_FIELD: class is a required element @ Encounter.class",
            exception.message
        )
    }
}

class R4EncounterClassHistoryValidatorTest {
    @Test
    fun `validate - succeeds with only required attributes`() {
        val encounterClassHistory = EncounterClassHistory(
            `class` = Code("PRENC"),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        R4EncounterClassHistoryValidator.validate(encounterClassHistory).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with required and other attributes`() {
        val encounterClassHistory = EncounterClassHistory(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            `class` = Code("PRENC"),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        R4EncounterClassHistoryValidator.validate(encounterClassHistory).alertIfErrors()
    }

    @Test
    fun `validate - fails for missing class`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterClassHistory = EncounterClassHistory(
                    `class` = null,
                    period = Period(
                        start = DateTime(value = "2021-11-17T08:00:00Z"),
                        end = DateTime(value = "2021-11-17T09:00:00Z")
                    )
                )
                R4EncounterClassHistoryValidator.validate(encounterClassHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: class is a required element @ EncounterClassHistory.class",
            exception.message
        )
    }

    @Test
    fun `validate - fails for missing period`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterClassHistory = EncounterClassHistory(
                    `class` = Code("PRENC"),
                    period = null,
                )
                R4EncounterClassHistoryValidator.validate(encounterClassHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: period is a required element @ EncounterClassHistory.period",
            exception.message
        )
    }

    @Test
    fun `validate - when there are multiple failure cases, reports all`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterClassHistory = EncounterClassHistory(
                    `class` = null,
                    period = null,
                )
                R4EncounterClassHistoryValidator.validate(encounterClassHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: class is a required element @ EncounterClassHistory.class\n" +
                "ERROR REQ_FIELD: period is a required element @ EncounterClassHistory.period",
            exception.message
        )
    }
}

class R4EncounterDiagnosisValidatorTest {
    @Test
    fun `validate - succeeds with only required attributes`() {
        val encounterDiagnosis = EncounterDiagnosis(
            condition = Reference(reference = FHIRString("Condition/example"))
        )
        R4EncounterDiagnosisValidator.validate(encounterDiagnosis).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with required and other attributes`() {
        val encounterDiagnosis = EncounterDiagnosis(
            condition = Reference(reference = FHIRString("Condition/example")),
            use = CodeableConcept(
                coding = listOf(
                    Coding(
                        system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                        code = Code("DD"),
                        display = FHIRString("Discharge diagnosis")
                    )
                )
            )
        )
        R4EncounterDiagnosisValidator.validate(encounterDiagnosis).alertIfErrors()
    }

    @Test
    fun `validate - fails for missing condition`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterDiagnosis = EncounterDiagnosis(
                    condition = null,
                    use = CodeableConcept(
                        coding = listOf(
                            Coding(
                                system = Uri("http://terminology.hl7.org/CodeSystem/diagnosis-role"),
                                code = Code("DD"),
                                display = FHIRString("Discharge diagnosis")
                            )
                        )
                    )
                )
                R4EncounterDiagnosisValidator.validate(encounterDiagnosis).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: condition is a required element @ EncounterDiagnosis.condition",
            exception.message
        )
    }
}

class R4EncounterLocationValidatorTest {
    @Test
    fun `validate - succeeds with only required attributes`() {
        val encounterLocation = EncounterLocation(
            location = Reference(reference = FHIRString("Location/example"))
        )
        R4EncounterLocationValidator.validate(encounterLocation).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with required and other attributes`() {
        val encounterLocation = EncounterLocation(
            location = Reference(reference = FHIRString("Location/example")),
            status = EncounterLocationStatus.ACTIVE.asCode(),
            physicalType = CodeableConcept(coding = listOf(Coding(code = Code("ca"))))
        )
        R4EncounterLocationValidator.validate(encounterLocation).alertIfErrors()
    }

    @Test
    fun `validate - fails for missing location`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterLocation = EncounterLocation(
                    location = null,
                    status = EncounterLocationStatus.ACTIVE.asCode()
                )
                R4EncounterLocationValidator.validate(encounterLocation).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: location is a required element @ EncounterLocation.location",
            exception.message
        )
    }

    @Test
    fun `validate - fails for bad status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterLocation = EncounterLocation(
                    location = Reference(reference = FHIRString("Location/example")),
                    status = Code("unavailable")
                )
                R4EncounterLocationValidator.validate(encounterLocation).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unavailable' is outside of required value set @ EncounterLocation.status",
            exception.message
        )
    }

    @Test
    fun `validate - when there are multiple failure cases, reports all`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterLocation = EncounterLocation(
                    location = null,
                    status = Code("unavailable")
                )
                R4EncounterLocationValidator.validate(encounterLocation).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: location is a required element @ EncounterLocation.location\n" +
                "ERROR INV_VALUE_SET: 'unavailable' is outside of required value set @ EncounterLocation.status",
            exception.message
        )
    }
}

class R4EncounterStatusHistoryValidatorTest {
    @Test
    fun `validate - succeeds with only required attributes`() {
        val encounterStatusHistory = EncounterStatusHistory(
            status = EncounterStatus.CANCELLED.asCode(),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        R4EncounterStatusHistoryValidator.validate(encounterStatusHistory).alertIfErrors()
    }

    @Test
    fun `validate - succeeds with required and other attributes`() {
        val encounterStatusHistory = EncounterStatusHistory(
            id = FHIRString("12345"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            status = EncounterStatus.UNKNOWN.asCode(),
            period = Period(
                start = DateTime(value = "2021-11-17T08:00:00Z"),
                end = DateTime(value = "2021-11-17T09:00:00Z")
            )
        )
        R4EncounterStatusHistoryValidator.validate(encounterStatusHistory).alertIfErrors()
    }

    @Test
    fun `validate - fails for missing status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterStatusHistory = EncounterStatusHistory(
                    status = null,
                    period = Period(
                        start = DateTime(value = "2021-11-17T08:00:00Z"),
                        end = DateTime(value = "2021-11-17T09:00:00Z")
                    )
                )
                R4EncounterStatusHistoryValidator.validate(encounterStatusHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ EncounterStatusHistory.status",
            exception.message
        )
    }

    @Test
    fun `validate - fails for bad status`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounter = EncounterStatusHistory(
                    status = Code("unavailable"),
                    period = Period(
                        start = DateTime(value = "2021-11-17T08:00:00Z"),
                        end = DateTime(value = "2021-11-17T09:00:00Z")
                    )
                )
                R4EncounterStatusHistoryValidator.validate(encounter).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'unavailable' is outside of required value set @ EncounterStatusHistory.status",
            exception.message
        )
    }

    @Test
    fun `validate - fails for missing period`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterStatusHistory = EncounterStatusHistory(
                    status = EncounterStatus.TRIAGED.asCode(),
                    period = null,
                )
                R4EncounterStatusHistoryValidator.validate(encounterStatusHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: period is a required element @ EncounterStatusHistory.period",
            exception.message
        )
    }

    @Test
    fun `validate - when there are multiple failure cases, reports all`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                val encounterStatusHistory = EncounterStatusHistory(
                    status = null,
                    period = null,
                )
                R4EncounterStatusHistoryValidator.validate(encounterStatusHistory).alertIfErrors()
            }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ EncounterStatusHistory.status\n" +
                "ERROR REQ_FIELD: period is a required element @ EncounterStatusHistory.period",
            exception.message
        )
    }
}
