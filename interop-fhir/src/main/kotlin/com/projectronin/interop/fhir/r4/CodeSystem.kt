package com.projectronin.interop.fhir.r4

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * Enumeration of common code systems used in FHIR.
 */
enum class CodeSystem(uriString: String) {
    CAREPLAN_CATEGORY("http://hl7.org/fhir/us/core/CodeSystem/careplan-category"),
    CONDITION_CATEGORY("http://terminology.hl7.org/CodeSystem/condition-category"),
    CONDITION_CATEGORY_HEALTH_CONCERN("http://hl7.org/fhir/us/core/CodeSystem/condition-category"),
    CONDITION_CLINICAL("http://terminology.hl7.org/CodeSystem/condition-clinical"),
    CURRENCY("urn:iso:std:iso:4217"),
    DOCUMENT_REFERENCE_CATEGORY("http://hl7.org/fhir/us/core/CodeSystem/us-core-documentreference-category"),
    DIAGNOSTIC_REPORT_LABORATORY("http://terminology.hl7.org/CodeSystem/v2-0074"),
    HL7_IDENTIFIER_TYPE("http://terminology.hl7.org/CodeSystem/v2-0203"),
    LOINC("http://loinc.org"),
    NPI("http://hl7.org/fhir/sid/us-npi"),
    OBSERVATION_CATEGORY("http://terminology.hl7.org/CodeSystem/observation-category"),
    RONIN_TENANT("http://projectronin.com/id/tenantId"),
    RONIN_MRN("http://projectronin.com/id/mrn"),
    RONIN_FHIR_ID("http://projectronin.com/id/fhir"),
    RONIN_DATA_AUTHORITY("http://projectronin.com/id/dataAuthorityId"),
    RXNORM("http://www.nlm.nih.gov/research/umls/rxnorm"),
    SNOMED_CT("http://snomed.info/sct"),
    UCUM("http://unitsofmeasure.org");

    val uri = Uri(uriString)
}
