package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.MedicationAdministration
import com.projectronin.interop.fhir.r4.resource.MedicationAdministrationDosage
import com.projectronin.interop.fhir.r4.valueset.MedicationAdministrationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4MedicationAdministrationValidatorTest {
    @Test
    fun `fails if no status provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationAdministration = MedicationAdministration(
                status = null,
                effective = DynamicValue(
                    DynamicValueType.DATE_TIME,
                    DateTime("2022")
                ),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationAdministrationValidator.validate(medicationAdministration).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ MedicationAdministration.status",
            exception.message
        )
    }

    @Test
    fun `fails for status outside value set`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationAdministration = MedicationAdministration(
                status = Code("invalid-status"),
                effective = DynamicValue(
                    DynamicValueType.DATE_TIME,
                    DateTime("2022")
                ),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationAdministrationValidator.validate(medicationAdministration).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-status' is outside of required value set @ MedicationAdministration.status",
            exception.message
        )
    }

    @Test
    fun `fails if no effective provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationAdministration = MedicationAdministration(
                status = MedicationAdministrationStatus.COMPLETED.asCode(),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationAdministrationValidator.validate(medicationAdministration).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: effective is a required element @ MedicationAdministration.effective",
            exception.message
        )
    }

    @Test
    fun `fails for effective outside value type`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationAdministration = MedicationAdministration(
                status = MedicationAdministrationStatus.COMPLETED.asCode(),
                effective = DynamicValue(
                    DynamicValueType.BOOLEAN,
                    false
                ),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationAdministrationValidator.validate(medicationAdministration).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: effective can only be one of the following: DateTime, Period @ MedicationAdministration.effective",
            exception.message
        )
    }

    @Test
    fun `fails if no medication provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationAdministration = MedicationAdministration(
                status = MedicationAdministrationStatus.COMPLETED.asCode(),
                medication = null,
                effective = DynamicValue(
                    DynamicValueType.DATE_TIME,
                    DateTime("2022")
                ),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationAdministrationValidator.validate(medicationAdministration).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: medication is a required element @ MedicationAdministration.medication",
            exception.message
        )
    }

    @Test
    fun `fails for unsupported medication dynamic value type`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationAdministration = MedicationAdministration(
                status = MedicationAdministrationStatus.COMPLETED.asCode(),
                medication = DynamicValue(DynamicValueType.BOOLEAN, false),
                effective = DynamicValue(
                    DynamicValueType.DATE_TIME,
                    DateTime("2022")
                ),
                subject = Reference(reference = FHIRString("Patient/1234"))
            )
            R4MedicationAdministrationValidator.validate(medicationAdministration).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: medication can only be one of the following: CodeableConcept, Reference @ MedicationAdministration.medication",
            exception.message
        )
    }

    @Test
    fun `fails if no subject provided`() {
        val exception = assertThrows<IllegalArgumentException> {
            val medicationAdministration = MedicationAdministration(
                status = MedicationAdministrationStatus.COMPLETED.asCode(),
                effective = DynamicValue(
                    DynamicValueType.DATE_TIME,
                    DateTime("2022")
                ),
                medication = DynamicValue(
                    DynamicValueType.CODEABLE_CONCEPT,
                    CodeableConcept(text = FHIRString("medication"))
                ),
                subject = null
            )
            R4MedicationAdministrationValidator.validate(medicationAdministration).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: subject is a required element @ MedicationAdministration.subject",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val medicationAdministration = MedicationAdministration(
            status = MedicationAdministrationStatus.COMPLETED.asCode(),
            effective = DynamicValue(
                DynamicValueType.DATE_TIME,
                DateTime("2022")
            ),
            medication = DynamicValue(
                DynamicValueType.CODEABLE_CONCEPT,
                CodeableConcept(text = FHIRString("medication"))
            ),
            subject = Reference(reference = FHIRString("Patient/1234"))
        )
        R4MedicationAdministrationValidator.validate(medicationAdministration).alertIfErrors()
    }
}

class R4MedAdminDosageValidatorTest {

    @Test
    fun `fails if rate is a bad type`() {
        val exception = assertThrows<IllegalArgumentException> {
            val dosage = MedicationAdministrationDosage(rate = DynamicValue(type = DynamicValueType.BOOLEAN, false))
            R4MedAdminDosageValidator.validate(dosage).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: rate can only be one of the following: Ratio, Quantity @ MedicationAdministrationDosage.rate",
            exception.message
        )
    }

    @Test
    fun `fails if dosage is provided without rate or dose`() {
        val exception = assertThrows<IllegalArgumentException> {
            val dosage = MedicationAdministrationDosage(id = FHIRString("badID"))
            R4MedAdminDosageValidator.validate(dosage).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_MAD_001: If dosage is provided, it SHALL have at least one of dosage.dose or dosage.rate[x] @ MedicationAdministration.dosage",
            exception.message
        )
    }

    @Test
    fun `validates successfully`() {
        val dosage = MedicationAdministrationDosage(
            rate = DynamicValue(
                type = DynamicValueType.QUANTITY,
                SimpleQuantity(value = Decimal(1))
            )
        )
        R4MedAdminDosageValidator.validate(dosage).alertIfErrors()
    }
}
