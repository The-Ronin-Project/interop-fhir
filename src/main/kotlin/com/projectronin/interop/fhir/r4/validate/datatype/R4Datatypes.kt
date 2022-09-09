package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.Age
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.AvailableTime
import com.projectronin.interop.fhir.r4.datatype.BundleLink
import com.projectronin.interop.fhir.r4.datatype.BundleRequest
import com.projectronin.interop.fhir.r4.datatype.BundleResponse
import com.projectronin.interop.fhir.r4.datatype.BundleSearch
import com.projectronin.interop.fhir.r4.datatype.CodeFilter
import com.projectronin.interop.fhir.r4.datatype.Communication
import com.projectronin.interop.fhir.r4.datatype.ConditionEvidence
import com.projectronin.interop.fhir.r4.datatype.ConditionStage
import com.projectronin.interop.fhir.r4.datatype.Contact
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Contributor
import com.projectronin.interop.fhir.r4.datatype.Count
import com.projectronin.interop.fhir.r4.datatype.DataRequirement
import com.projectronin.interop.fhir.r4.datatype.DateFilter
import com.projectronin.interop.fhir.r4.datatype.Distance
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DoseAndRate
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Element
import com.projectronin.interop.fhir.r4.datatype.Expression
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Ingredient
import com.projectronin.interop.fhir.r4.datatype.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.datatype.LocationPosition
import com.projectronin.interop.fhir.r4.datatype.MoneyQuantity
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.NotAvailable
import com.projectronin.interop.fhir.r4.datatype.ObservationComponent
import com.projectronin.interop.fhir.r4.datatype.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.datatype.ParameterDefinition
import com.projectronin.interop.fhir.r4.datatype.Participant
import com.projectronin.interop.fhir.r4.datatype.PatientLink
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Qualification
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.RelatedArtifact
import com.projectronin.interop.fhir.r4.datatype.SampledData
import com.projectronin.interop.fhir.r4.datatype.Signature
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.Sort
import com.projectronin.interop.fhir.r4.datatype.TimingRepeat
import com.projectronin.interop.fhir.r4.datatype.TriggerDefinition
import com.projectronin.interop.fhir.r4.datatype.UsageContext
import com.projectronin.interop.fhir.r4.datatype.primitive.Primitive
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.validate.datatype.primitive.validatePrimitive
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validates the [dynamicValue] against the R4 spec.
 */
fun <T> validateDynamicValue(dynamicValue: DynamicValue<T>, parentContext: LocationContext?): Validation {
    return when (val value = dynamicValue.value) {
        is Primitive<*, *> -> validatePrimitive(value, parentContext)
        is Element<*> -> validateDatatype(value, parentContext)
        else -> Validation()
    }
}

/**
 * Validates the [element] against the R4 spec.
 */
fun <T : Element<T>> validateDatatype(element: Element<T>, parentContext: LocationContext?): Validation {
    return when (element) {
        is Address -> element.validate(R4AddressValidator, parentContext)
        is Age -> element.validate(R4AgeValidator, parentContext)
        is Annotation -> element.validate(R4AnnotationValidator, parentContext)
        is Attachment -> element.validate(R4AttachmentValidator, parentContext)
        is AvailableTime -> element.validate(R4AvailableTimeValidator, parentContext)
        is BundleLink -> element.validate(R4BundleLinkValidator, parentContext)
        is BundleRequest -> element.validate(R4BundleRequestValidator, parentContext)
        is BundleResponse -> element.validate(R4BundleResponseValidator, parentContext)
        is BundleSearch -> element.validate(R4BundleSearchValidator, parentContext)
        is CodeFilter -> element.validate(R4CodeFilterValidator, parentContext)
        is Communication -> element.validate(R4CommunicationValidator, parentContext)
        is ConditionEvidence -> element.validate(R4ConditionEvidenceValidator, parentContext)
        is ConditionStage -> element.validate(R4ConditionStageValidator, parentContext)
        is Contact -> element.validate(R4ContactValidator, parentContext)
        is ContactPoint -> element.validate(R4ContactPointValidator, parentContext)
        is Contributor -> element.validate(R4ContributorValidator, parentContext)
        is Count -> element.validate(R4CountValidator, parentContext)
        is DataRequirement -> element.validate(R4DataRequirementValidator, parentContext)
        is DateFilter -> element.validate(R4DateFilterValidator, parentContext)
        is Distance -> element.validate(R4DistanceValidator, parentContext)
        is Dosage -> element.validate(R4DosageValidator, parentContext)
        is DoseAndRate -> element.validate(R4DoseAndRateValidator, parentContext)
        is Duration -> element.validate(R4DurationValidator, parentContext)
        is Expression -> element.validate(R4ExpressionValidator, parentContext)
        is Extension -> element.validate(R4ExtensionValidator, parentContext)
        is HumanName -> element.validate(R4HumanNameValidator, parentContext)
        is Identifier -> element.validate(R4IdentifierValidator, parentContext)
        is Ingredient -> element.validate(R4IngredientValidator, parentContext)
        is LocationHoursOfOperation -> element.validate(R4LocationHoursOfOperationValidator, parentContext)
        is LocationPosition -> element.validate(R4LocationPositionValidator, parentContext)
        is MoneyQuantity -> element.validate(R4MoneyQuantityValidator, parentContext)
        is Narrative -> element.validate(R4NarrativeValidator, parentContext)
        is NotAvailable -> element.validate(R4NotAvailableValidator, parentContext)
        is ObservationComponent -> element.validate(R4ObservationComponentValidator, parentContext)
        is ObservationReferenceRange -> element.validate(R4ObservationReferenceRangeValidator, parentContext)
        is ParameterDefinition -> element.validate(R4ParameterDefinitionValidator, parentContext)
        is Participant -> element.validate(R4ParticipantValidator, parentContext)
        is PatientLink -> element.validate(R4PatientLinkValidator, parentContext)
        is Period -> element.validate(R4PeriodValidator, parentContext)
        is Qualification -> element.validate(R4QualificationValidator, parentContext)
        is Quantity -> element.validate(R4QuantityValidator, parentContext)
        is Range -> element.validate(R4RangeValidator, parentContext)
        is Ratio -> element.validate(R4RatioValidator, parentContext)
        is Reference -> element.validate(R4ReferenceValidator, parentContext)
        is RelatedArtifact -> element.validate(R4RelatedArtifactValidator, parentContext)
        is SampledData -> element.validate(R4SampledDataValidator, parentContext)
        is Signature -> element.validate(R4SignatureValidator, parentContext)
        is SimpleQuantity -> element.validate(R4SimpleQuantityValidator, parentContext)
        is Sort -> element.validate(R4SortValidator, parentContext)
        is TimingRepeat -> element.validate(R4TimingRepeatValidator, parentContext)
        is TriggerDefinition -> element.validate(R4TriggerDefinitionValidator, parentContext)
        is UsageContext -> element.validate(R4UsageContextValidator, parentContext)
        else -> element.validate(GenericElementValidator(), parentContext)
    }
}

/**
 * A generic validator for elements that ensures their properties are validated.
 */
internal class GenericElementValidator<T : Element<T>> : R4ElementContainingValidator<T>() {
    override fun validateElement(element: T, parentContext: LocationContext?, validation: Validation) {
        // There is no actual validation done for generic elements.
    }
}
