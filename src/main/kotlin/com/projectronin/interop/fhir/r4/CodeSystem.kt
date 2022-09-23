package com.projectronin.interop.fhir.r4

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * Enumeration of common code systems used in FHIR.
 */
enum class CodeSystem(uriString: String) {
    UCUM("http://unitsofmeasure.org"),
    CURRENCY("urn:iso:std:iso:4217"),
    NPI("http://hl7.org/fhir/sid/us-npi"),
    OBSERVATION_CATEGORY("http://terminology.hl7.org/CodeSystem/observation-category"),
    HL7_IDENTIFIER_TYPE("http://terminology.hl7.org/CodeSystem/v2-0203"),
    CONDITION_CATEGORY("http://terminology.hl7.org/CodeSystem/condition-category"),
    LOINC("http://loinc.org"),
    CONDITION_CLINICAL("http://terminology.hl7.org/CodeSystem/condition-clinical"),
    SNOMED_CT("http://snomed.info/sct");

    val uri = Uri(uriString)
}
