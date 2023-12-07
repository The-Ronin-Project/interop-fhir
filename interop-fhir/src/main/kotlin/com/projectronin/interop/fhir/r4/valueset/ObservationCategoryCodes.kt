package com.projectronin.interop.fhir.r4.valueset

import com.fasterxml.jackson.annotation.JsonValue
import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [US Core](https://www.hl7.org/fhir/us/core/ValueSet-us-core-observation-category.html),
 * comprising [FHIR Spec](https://hl7.org/fhir/R4/valueset-observation-category.html),
 * [US Core (clinical-test)](https://hl7.org/fhir/R4/valueset-observation-category.html), and
 * [US Core (sdoh - social determinants of health)](https://www.hl7.org/fhir/us/core/CodeSystem-us-core-tags.html).
 */
enum class ObservationCategoryCodes constructor(
    @JsonValue override val code: String,
) : CodedEnum<ObservationCategoryCodes> {
    SOCIAL_HISTORY("social-history"),
    VITAL_SIGNS("vital-signs"),
    IMAGING("imaging"),
    LABORATORY("laboratory"),
    PROCECURE("procedure"),
    SURVEY("survey"),
    EXAM("exam"),
    THERAPY("therapy"),
    ACTIVITY("activity"),
    CLINICAL_TEST("clinical-test"),
    SDOH("sdoh"),
}
