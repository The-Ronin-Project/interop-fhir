package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/us/core/STU4/ValueSet-us-core-condition-category.html)
 */

enum class ConditionCategoryCodes(override val code: String) : CodedEnum<ConditionCategoryCodes> {
    PROBLEM_LIST_ITEM("problem-list-item"),
    ENCOUNTER_DIAGNOSIS("encounter-diagnosis"),
    HEALTH_CONCERN("health-concern"),
    DEATH_DIAGNOSIS("16100001")
}
