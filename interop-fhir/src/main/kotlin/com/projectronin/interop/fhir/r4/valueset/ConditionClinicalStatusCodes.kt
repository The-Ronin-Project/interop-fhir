package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/R4/valueset-condition-clinical.html)
 */
enum class ConditionClinicalStatusCodes(override val code: String) : CodedEnum<ConditionClinicalStatusCodes> {
    ACTIVE("active"),
    RECURRENCE("recurrence"),
    RELAPSE("relapse"),
    INACTIVE("inactive"),
    REMISSION("remission"),
    RESOLVED("resolved")
}
