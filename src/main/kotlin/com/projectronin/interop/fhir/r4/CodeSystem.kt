package com.projectronin.interop.fhir.r4

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * Enumeration of common code systems used in FHIR.
 */
enum class CodeSystem(private val uriString: String) {
    UCUM("http://unitsofmeasure.org"),
    CURRENCY("urn:iso:std:iso:4217"),
    RONIN_TENANT("http://projectronin.com/id/tenantId"),
    NPI("http://hl7.org/fhir/sid/us-npi"),
    MRN("http://projectronin.com/id/mrn"),
    FHIR_STU3_ID("http://projectronin.com/id/fhir"),
    SER("http://projectronin.com/id/ser"),
    RONIN_ID("http://projectronin.com/fhir/us/ronin");

    val uri = Uri(uriString)
}
