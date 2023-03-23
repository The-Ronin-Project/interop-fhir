package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ValueSet
import com.projectronin.interop.fhir.r4.resource.ValueSetCompose
import com.projectronin.interop.fhir.r4.resource.ValueSetConcept
import com.projectronin.interop.fhir.r4.resource.ValueSetContains
import com.projectronin.interop.fhir.r4.resource.ValueSetDesignation
import com.projectronin.interop.fhir.r4.resource.ValueSetExpansion
import com.projectronin.interop.fhir.r4.resource.ValueSetFilter
import com.projectronin.interop.fhir.r4.resource.ValueSetInclude
import com.projectronin.interop.fhir.r4.resource.ValueSetParameter
import com.projectronin.interop.fhir.r4.valueset.FilterOperator
import com.projectronin.interop.fhir.r4.valueset.PublicationStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ValueSetValidatorTest {
    @Test
    fun `fails if no status`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetValidator.validate(
                ValueSet(
                    status = null
                )
            ).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: status is a required element @ ValueSet.status",
            exception.message
        )
    }

    @Test
    fun `fails for invalid status`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetValidator.validate(
                ValueSet(
                    status = Code("invalid-status")
                )
            ).alertIfErrors()
        }
        Assertions.assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: 'invalid-status' is outside of required value set @ ValueSet.status",
            exception.message
        )
    }

    @Test
    fun `validates successfully with valid status`() {
        R4ValueSetValidator.validate(
            ValueSet(
                status = PublicationStatus.ACTIVE.asCode()
            )
        )
    }
}

class R4ValueSetComposeValidatorTest {
    @Test
    fun `fails if no include`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetComposeValidator.validate(
                ValueSetCompose(
                    include = listOf()
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: include is a required element @ ValueSetCompose.include",
            exception.message
        )
    }

    @Test
    fun `validates succesfully with an include`() {
        R4ValueSetComposeValidator.validate(
            ValueSetCompose(
                include = listOf(
                    ValueSetInclude(
                        system = Uri("http://localhost/valueset-include"),
                        filter = listOf(
                            ValueSetFilter(
                                property = Code("property"),
                                op = FilterOperator.REGULAR_EXPRESSION.asCode(),
                                value = FHIRString("value")
                            )
                        ),
                        valueSet = listOf(
                            Canonical(
                                value = "canonical"
                            )
                        )
                    )
                )
            )
        ).alertIfErrors()
    }
}

class R4ValueSetIncludeValidatorTest {
    @Test
    fun `fails if both valueSet and system are missing`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetIncludeValidator.validate(
                ValueSetInclude(
                    system = null,
                    valueSet = listOf()
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_VALSTINC_001: A value set include/exclude SHALL have a value set or a system @ ValueSetInclude",
            exception.message
        )
    }

    @Test
    fun `validates successfully if valueSet exists`() {
        R4ValueSetIncludeValidator.validate(
            ValueSetInclude(
                system = null,
                valueSet = listOf(
                    Canonical(
                        value = "canonical"
                    )
                )
            )
        ).alertIfErrors()
    }

    @Test
    fun `validates successfully if system exists`() {
        R4ValueSetIncludeValidator.validate(
            ValueSetInclude(
                system = Uri("http://localhost/valueset-include"),
                valueSet = listOf()
            )
        ).alertIfErrors()
    }

    @Test
    fun `validates successfully if both valueSet and system exist`() {
        R4ValueSetIncludeValidator.validate(
            ValueSetInclude(
                system = Uri("http://localhost/valueset-include"),
                valueSet = listOf(
                    Canonical(
                        value = "canonical"
                    )
                )
            )
        ).alertIfErrors()
    }

    @Test
    fun `fails when concept exists but system does not`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetIncludeValidator.validate(
                ValueSetInclude(
                    system = null,
                    concept = listOf(
                        ValueSetConcept(
                            code = Code("concept_code")
                        )
                    ),
                    valueSet = listOf(
                        Canonical(
                            value = "canonical"
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_VALSTINC_002: A value set with concepts or filters SHALL include a system @ ValueSetInclude",
            exception.message
        )
    }

    @Test
    fun `fails when filter exists but system does not`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetIncludeValidator.validate(
                ValueSetInclude(
                    system = null,
                    filter = listOf(
                        ValueSetFilter(
                            property = Code("property"),
                            op = FilterOperator.REGULAR_EXPRESSION.asCode(),
                            value = FHIRString("value")
                        )
                    ),
                    valueSet = listOf(
                        Canonical(
                            value = "canonical"
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_VALSTINC_002: A value set with concepts or filters SHALL include a system @ ValueSetInclude",
            exception.message
        )
    }

    @Test
    fun `validates successfully when concept and system exist`() {
        R4ValueSetIncludeValidator.validate(
            ValueSetInclude(
                system = Uri("http://localhost/valueset-include"),
                filter = listOf(
                    ValueSetFilter(
                        property = Code("property"),
                        op = FilterOperator.REGULAR_EXPRESSION.asCode(),
                        value = FHIRString("value")
                    )
                )
            )
        ).alertIfErrors()
    }

    @Test
    fun `validates successfully when filter and system exist`() {
        R4ValueSetIncludeValidator.validate(
            ValueSetInclude(
                system = Uri("http://localhost/valueset-include"),
                concept = listOf(
                    ValueSetConcept(
                        code = Code("concept_code")
                    )
                )
            )
        ).alertIfErrors()
    }

    @Test
    fun `fails when both concept and filter exist`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetIncludeValidator.validate(
                ValueSetInclude(
                    system = Uri("http://localhost/valueset-include"),
                    filter = listOf(
                        ValueSetFilter(
                            property = Code("property"),
                            op = FilterOperator.REGULAR_EXPRESSION.asCode(),
                            value = FHIRString("value")
                        )
                    ),
                    concept = listOf(
                        ValueSetConcept(
                            code = Code("code")
                        )
                    ),
                    valueSet = listOf(
                        Canonical(
                            value = "canonical"
                        )
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_VALSTINC_003: Cannot have both concept and filter @ ValueSetInclude",
            exception.message
        )
    }

    @Test
    fun `validates successfully with filter`() {
        R4ValueSetIncludeValidator.validate(
            ValueSetInclude(
                system = Uri("http://localhost/valueset-include"),
                version = FHIRString("include_version"),
                filter = listOf(
                    ValueSetFilter(
                        property = Code("property"),
                        op = FilterOperator.REGULAR_EXPRESSION.asCode(),
                        value = FHIRString("value")
                    )
                ),
                valueSet = listOf(
                    Canonical(
                        value = "canonical"
                    )
                )
            )
        ).alertIfErrors()
    }

    @Test
    fun `validates successfully with concept`() {
        R4ValueSetIncludeValidator.validate(
            ValueSetInclude(
                system = Uri("http://localhost/valueset-include"),
                version = FHIRString("include_version"),
                concept = listOf(
                    ValueSetConcept(
                        code = Code("code")
                    )
                ),
                valueSet = listOf(
                    Canonical(
                        value = "canonical"
                    )
                )
            )
        ).alertIfErrors()
    }
}

class ValueSetConceptValidatorTest {
    @Test
    fun `fails if no code`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetConceptValidator.validate(
                ValueSetConcept(
                    code = null
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: code is a required element @ ValueSetConcept.code",
            exception.message
        )
    }

    @Test
    fun `validates successfully with code`() {
        R4ValueSetConceptValidator.validate(
            ValueSetConcept(
                code = Code("code")
            )
        ).alertIfErrors()
    }
}

class R4ValueSetDesignationValidatorTest {
    @Test
    fun `fails if no value`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetDesignationValidator.validate(
                ValueSetDesignation(
                    value = null
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: value is a required element @ ValueSetDesignation.value",
            exception.message
        )
    }

    @Test
    fun `validates successfully with value`() {
        R4ValueSetDesignationValidator.validate(
            ValueSetDesignation(
                value = FHIRString("value")
            )
        ).alertIfErrors()
    }
}

class R4ValueSetFilterValidatorTest {
    @Test
    fun `fails if no property`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetFilterValidator.validate(
                ValueSetFilter(
                    property = null,
                    op = FilterOperator.IN_SET.asCode(),
                    value = FHIRString("value")
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: property is a required element @ ValueSetFilter.property",
            exception.message
        )
    }

    @Test
    fun `fails if no op`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetFilterValidator.validate(
                ValueSetFilter(
                    property = Code("code"),
                    op = null,
                    value = FHIRString("value")
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: op is a required element @ ValueSetFilter.op",
            exception.message
        )
    }

    @Test
    fun `fails if no value`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetFilterValidator.validate(
                ValueSetFilter(
                    property = Code("code"),
                    op = FilterOperator.IN_SET.asCode(),
                    value = null
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: value is a required element @ ValueSetFilter.value",
            exception.message
        )
    }

    @Test
    fun `validates successfully with property, op, and value`() {
        R4ValueSetFilterValidator.validate(
            ValueSetFilter(
                property = Code("code"),
                op = FilterOperator.IN_SET.asCode(),
                value = FHIRString("value")
            )
        ).alertIfErrors()
    }
}

class R4ValueSetExpansionValidatorTest {
    @Test
    fun `fails if no timestamp`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetExpansionValidator.validate(
                ValueSetExpansion(
                    timestamp = null
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: timestamp is a required element @ ValueSetExpansion.timestamp",
            exception.message
        )
    }

    @Test
    fun `validates successfully with timestamp`() {
        R4ValueSetExpansionValidator.validate(
            ValueSetExpansion(
                timestamp = DateTime("1993-06-12")
            )
        ).alertIfErrors()
    }
}

class R4ValueSetParameterValidatorTest {
    @Test
    fun `fails if no name`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetParameterValidator.validate(
                ValueSetParameter(
                    name = null
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: name is a required element @ ValueSetParameter.name",
            exception.message
        )
    }

    @Test
    fun `fails if value outside of available types`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetParameterValidator.validate(
                ValueSetParameter(
                    name = FHIRString("name"),
                    value = DynamicValue(
                        DynamicValueType.CANONICAL,
                        Canonical("canonical")
                    )
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_DYN_VAL: value can only be one of the following: String, Boolean, Integer, Decimal, Uri, Code, DateTime @ ValueSetParameter.value",
            exception.message
        )
    }

    @Test
    fun `validates successfully with name`() {
        R4ValueSetParameterValidator.validate(
            ValueSetParameter(
                name = FHIRString("name")
            )
        ).alertIfErrors()
    }

    @Test
    fun `validates successfully with name and value of correct type`() {
        R4ValueSetParameterValidator.validate(
            ValueSetParameter(
                name = FHIRString("name"),
                value = DynamicValue(
                    DynamicValueType.DATE_TIME,
                    DateTime("2014-02-04")
                )
            )
        ).alertIfErrors()
    }
}

class R4ValueSetContainsValidatorTest {
    @Test
    fun `fails if both code and display are missing`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetContainsValidator.validate(
                ValueSetContains(
                    abstract = FHIRBoolean(true)
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_VALSTCON_001: SHALL have a code or a display @ ValueSetContains",
            exception.message
        )
    }

    @Test
    fun `validates successfully if there is a code`() {
        R4ValueSetContainsValidator.validate(
            ValueSetContains(
                code = Code("code"),
                system = Uri("http://localhost/valueset-contains")
            )
        ).alertIfErrors()
    }

    @Test
    fun `validates successfully if there is a display`() {
        R4ValueSetContainsValidator.validate(
            ValueSetContains(
                display = FHIRString("display"),
                abstract = FHIRBoolean(true)
            )
        ).alertIfErrors()
    }

    @Test
    fun `fails when not abstract and no code`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetContainsValidator.validate(
                ValueSetContains(
                    abstract = FHIRBoolean(false),
                    code = null,
                    display = FHIRString("display")
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_VALSTCON_002: Must have a code if not abstract @ ValueSetContains",
            exception.message
        )
    }

    @Test
    fun `fails when abstract is null and no code `() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetContainsValidator.validate(
                ValueSetContains(
                    abstract = null,
                    code = null,
                    display = FHIRString("display")
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_VALSTCON_002: Must have a code if not abstract @ ValueSetContains",
            exception.message
        )
    }

    @Test
    fun `validates successfully if abstract and no code`() {
        R4ValueSetContainsValidator.validate(
            ValueSetContains(
                display = FHIRString("display"),
                abstract = FHIRBoolean(true)
            )
        ).alertIfErrors()
    }

    @Test
    fun `validates successfully if not abstract and has code`() {
        R4ValueSetContainsValidator.validate(
            ValueSetContains(
                abstract = FHIRBoolean(false),
                code = Code("code"),
                system = Uri("http://localhost/valueset-contains")
            )
        ).alertIfErrors()
    }

    @Test
    fun `validates successfully if abstract is null and has code`() {
        R4ValueSetContainsValidator.validate(
            ValueSetContains(
                code = Code("code"),
                system = Uri("http://localhost/valueset-contains")
            )
        ).alertIfErrors()
    }

    @Test
    fun `fails when code exists but system does not`() {
        val exception = assertThrows<IllegalArgumentException> {
            R4ValueSetContainsValidator.validate(
                ValueSetContains(
                    code = Code("code"),
                    display = FHIRString("display")
                )
            ).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_VALSTCON_003: Must have a system if a code is present @ ValueSetContains",
            exception.message
        )
    }

    @Test
    fun `validates successfully when code and system exist`() {
        R4ValueSetContainsValidator.validate(
            ValueSetContains(
                code = Code("code"),
                display = FHIRString("display"),
                system = Uri("http://localhost/valueset-contains")
            )
        ).alertIfErrors()
    }

    @Test
    fun `validates successfully`() {
        R4ValueSetContainsValidator.validate(
            ValueSetContains(
                system = Uri("http://localhost/contains-system"),
                abstract = FHIRBoolean(false),
                inactive = FHIRBoolean(false),
                version = FHIRString("contains_version"),
                code = Code("contains_code"),
                display = FHIRString("contains display"),
                designation = listOf(
                    ValueSetDesignation(
                        value = FHIRString("valueset_designationvalue")
                    )
                )
            )
        ).alertIfErrors()
    }
}
