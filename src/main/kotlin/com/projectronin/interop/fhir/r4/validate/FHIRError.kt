package com.projectronin.interop.fhir.r4.validate

import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity as Severity

/**
 * Should be implemented for all types of validation, i.e. FHIR, USCore, Ronin, etc.
 */
interface FHIRError {
    val severity: Severity
    val description: String
    val location: String?
}

/**
 * List of error/warning codes defined by HL7 FHIR for version R4.
 */
enum class R4Error(
    override val severity: Severity,
    override val description: String,
    override val location: String?
) : FHIRError {
    R4_PAT_001(
        severity = Severity.ERROR,
        description = "Patient multipleBirth can only be one of the following data types: ${Patient.acceptedMultipleBirthTypes.joinToString { it.code }}",
        location = "Patient.multipleBirth"
    ),
    R4_PAT_002(
        severity = Severity.ERROR,
        description = "Patient deceased can only be one of the following data types: ${Patient.acceptedDeceasedTypes.joinToString { it.code }}",
        location = "Patient.deceased"
    ),
}
