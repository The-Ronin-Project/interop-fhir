package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SupportedReferenceTypesValidatorTest {
    private val validator = SupportedReferenceTypesValidator()

    @Test
    fun `no validation occurs when no resource types provided`() {
        val annotation = SupportedReferenceTypes(value = emptyArray())
        val element = SingleReferenceElement(null)
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                SingleReferenceElement::reference,
                Reference::class,
                element,
                "SingleReferenceElement",
                LocationContext(SingleReferenceElement::class),
                this
            )
        }
        assertFalse(validation.hasIssues())
    }

    @Test
    fun `passes when provided null Reference property`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element = SingleReferenceElement(null)
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                SingleReferenceElement::reference,
                Reference::class,
                element,
                "SingleReferenceElement",
                LocationContext(SingleReferenceElement::class),
                this
            )
        }
        assertFalse(validation.hasIssues())
    }

    @Test
    fun `passes when provided Reference property with supported resource type`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element = SingleReferenceElement(Reference(reference = FHIRString("Patient/1234")))
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                SingleReferenceElement::reference,
                Reference::class,
                element,
                "SingleReferenceElement",
                LocationContext(SingleReferenceElement::class),
                this
            )
        }
        assertFalse(validation.hasIssues())
    }

    @Test
    fun `warns when provided Reference property with unsupported resource type`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element = SingleReferenceElement(Reference(reference = FHIRString("Observation/1234")))
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                SingleReferenceElement::reference,
                Reference::class,
                element,
                "SingleReferenceElement",
                LocationContext(SingleReferenceElement::class),
                this
            )
        }

        assertTrue(validation.hasIssues())
        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(
            "WARNING INV_REF_TYPE: reference can only be one of the following: Group, Patient @ SingleReferenceElement.reference.reference",
            issues.first().toString()
        )
    }

    @Test
    fun `passes when provided Reference property with local reference`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element = SingleReferenceElement(Reference(reference = FHIRString("#1234")))
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                SingleReferenceElement::reference,
                Reference::class,
                element,
                "SingleReferenceElement",
                LocationContext(SingleReferenceElement::class),
                this
            )
        }
        assertFalse(validation.hasIssues())
    }

    @Test
    fun `passes when provided DynamicValue Reference property with supported reference type`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element =
            DynamicValueElement(
                DynamicValue(
                    DynamicValueType.REFERENCE,
                    Reference(reference = FHIRString("Patient/1234"))
                )
            )
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                DynamicValueElement::value,
                DynamicValue::class,
                element,
                "DynamicValueElement",
                LocationContext(DynamicValueElement::class),
                this
            )
        }
        assertFalse(validation.hasIssues())
    }

    @Test
    fun `passes when provided DynamicValue property not for a Reference`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element =
            DynamicValueElement(
                DynamicValue(
                    DynamicValueType.STRING,
                    FHIRString("Patient/1234")
                )
            )
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                DynamicValueElement::value,
                DynamicValue::class,
                element,
                "DynamicValueElement",
                LocationContext(DynamicValueElement::class),
                this
            )
        }
        assertFalse(validation.hasIssues())
    }

    @Test
    fun `passes when provided DynamicValue property for a Reference but containing something else`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element =
            DynamicValueElement(
                DynamicValue(
                    DynamicValueType.REFERENCE,
                    FHIRString("Patient/1234")
                )
            )
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                DynamicValueElement::value,
                DynamicValue::class,
                element,
                "DynamicValueElement",
                LocationContext(DynamicValueElement::class),
                this
            )
        }
        assertFalse(validation.hasIssues())
    }

    @Test
    fun `warns when provided DynamicValue Reference property with unsupported reference type`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element =
            DynamicValueElement(
                DynamicValue(
                    DynamicValueType.REFERENCE,
                    Reference(reference = FHIRString("Practitioner/1234"))
                )
            )
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                DynamicValueElement::value,
                DynamicValue::class,
                element,
                "DynamicValueElement",
                LocationContext(DynamicValueElement::class),
                this
            )
        }

        assertTrue(validation.hasIssues())
        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(
            "WARNING INV_REF_TYPE: reference can only be one of the following: Group, Patient @ DynamicValueElement.value.reference",
            issues.first().toString()
        )
    }

    @Test
    fun `passes when provided List of Reference property with only supported resource types`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element = ListReferenceElement(
            listOf(
                Reference(reference = FHIRString("Patient/1234")),
                Reference(reference = FHIRString("Group/1234"))
            )
        )
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                ListReferenceElement::references,
                List::class,
                element,
                "ListReferenceElement",
                LocationContext(ListReferenceElement::class),
                this
            )
        }
        assertFalse(validation.hasIssues())
    }

    @Test
    fun `warns when provided List of Reference property with single unsupported resource type`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element = ListReferenceElement(
            listOf(
                Reference(reference = FHIRString("Observation/1234")),
                Reference(reference = FHIRString("Group/1234"))
            )
        )
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                ListReferenceElement::references,
                List::class,
                element,
                "ListReferenceElement",
                LocationContext(ListReferenceElement::class),
                this
            )
        }

        assertTrue(validation.hasIssues())
        val issues = validation.issues()
        assertEquals(1, issues.size)
        assertEquals(
            "WARNING INV_REF_TYPE: reference can only be one of the following: Group, Patient @ ListReferenceElement.references[0].reference",
            issues.first().toString()
        )
    }

    @Test
    fun `warns when provided List of Reference property with multiple unsupported resource types`() {
        val annotation = SupportedReferenceTypes(ResourceType.Group, ResourceType.Patient)
        val element = ListReferenceElement(
            listOf(
                Reference(reference = FHIRString("Observation/1234")),
                Reference(reference = FHIRString("Condition/1234"))
            )
        )
        val validation = Validation().apply {
            validator.validateAnnotations(
                listOf(annotation),
                ListReferenceElement::references,
                List::class,
                element,
                "ListReferenceElement",
                LocationContext(ListReferenceElement::class),
                this
            )
        }

        assertTrue(validation.hasIssues())
        val issues = validation.issues()
        assertEquals(2, issues.size)
        assertEquals(
            "WARNING INV_REF_TYPE: reference can only be one of the following: Group, Patient @ ListReferenceElement.references[0].reference",
            issues[0].toString()
        )
        assertEquals(
            "WARNING INV_REF_TYPE: reference can only be one of the following: Group, Patient @ ListReferenceElement.references[1].reference",
            issues[1].toString()
        )
    }

    data class SingleReferenceElement(
        val reference: Reference?
    ) : Validatable<SingleReferenceElement>

    data class DynamicValueElement(
        val value: DynamicValue<Any>?
    ) : Validatable<DynamicValueElement>

    data class ListReferenceElement(
        val references: List<Reference>
    ) : Validatable<ListReferenceElement>
}
