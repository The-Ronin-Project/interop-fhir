package com.projectronin.interop.fhir.r4.validate.element

import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.Age
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeFilter
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Contributor
import com.projectronin.interop.fhir.r4.datatype.Count
import com.projectronin.interop.fhir.r4.datatype.DataRequirement
import com.projectronin.interop.fhir.r4.datatype.DateFilter
import com.projectronin.interop.fhir.r4.datatype.Distance
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DoseAndRate
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.Expression
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.MoneyQuantity
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.ParameterDefinition
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.RelatedArtifact
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.Sort
import com.projectronin.interop.fhir.r4.datatype.TimingRepeat
import com.projectronin.interop.fhir.r4.datatype.TriggerDefinition
import com.projectronin.interop.fhir.r4.datatype.UsageContext
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.r4.resource.AvailableTime
import com.projectronin.interop.fhir.r4.resource.BundleRequest
import com.projectronin.interop.fhir.r4.resource.BundleSearch
import com.projectronin.interop.fhir.r4.resource.CarePlanActivity
import com.projectronin.interop.fhir.r4.resource.CarePlanDetail
import com.projectronin.interop.fhir.r4.resource.CareTeamParticipant
import com.projectronin.interop.fhir.r4.resource.CommunicationPayload
import com.projectronin.interop.fhir.r4.resource.ConceptMapTarget
import com.projectronin.interop.fhir.r4.resource.ConceptMapUnmapped
import com.projectronin.interop.fhir.r4.resource.ConditionEvidence
import com.projectronin.interop.fhir.r4.resource.ConditionStage
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceRelatesTo
import com.projectronin.interop.fhir.r4.resource.EncounterLocation
import com.projectronin.interop.fhir.r4.resource.EncounterStatusHistory
import com.projectronin.interop.fhir.r4.resource.ImmunizationEducation
import com.projectronin.interop.fhir.r4.resource.ImmunizationProtocolApplied
import com.projectronin.interop.fhir.r4.resource.Ingredient
import com.projectronin.interop.fhir.r4.resource.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.resource.MedicationAdministrationDosage
import com.projectronin.interop.fhir.r4.resource.ObservationComponent
import com.projectronin.interop.fhir.r4.resource.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.resource.Participant
import com.projectronin.interop.fhir.r4.resource.PatientContact
import com.projectronin.interop.fhir.r4.resource.PatientLink
import com.projectronin.interop.fhir.r4.resource.RequestGroupAction
import com.projectronin.interop.fhir.r4.resource.RequestGroupCondition
import com.projectronin.interop.fhir.r4.resource.RequestGroupRelatedAction
import com.projectronin.interop.fhir.r4.resource.Substitution
import com.projectronin.interop.fhir.r4.resource.ValueSetContains
import com.projectronin.interop.fhir.r4.resource.ValueSetFilter
import com.projectronin.interop.fhir.r4.resource.ValueSetInclude
import com.projectronin.interop.fhir.r4.resource.ValueSetParameter
import com.projectronin.interop.fhir.r4.validate.datatype.R4AddressValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4AgeValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4AnnotationValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4AttachmentValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4CodeFilterValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4ContactPointValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4ContributorValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4CountValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DataRequirementValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DateFilterValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DistanceValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DosageValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DoseAndRateValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DurationValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4ExpressionValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4ExtensionValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4HumanNameValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4IdentifierValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4MoneyQuantityValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4NarrativeValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4ParameterDefinitionValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4PeriodValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4QuantityValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4RangeValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4RatioValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4ReferenceValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4RelatedArtifactValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4SimpleQuantityValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4SortValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4TimingRepeatValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4TriggerDefinitionValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4UsageContextValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4AvailableTimeValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4BundleRequestValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4BundleSearchValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CarePlanActivityValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CarePlanDetailValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CareTeamParticipantValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CommunicationPayloadValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConceptMapTargetValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConceptMapUnmappedValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConditionEvidenceValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConditionStageValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4DocumentReferenceRelatesToValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4EncounterLocationValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4EncounterStatusHistoryValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ImmunizationEducationValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ImmunizationProtocolAppliedValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4IngredientValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4LocationHoursOfOperationValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4MedAdminDosageValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ObservationComponentValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ObservationReferenceRangeValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ParticipantValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4PatientContactValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4PatientLinkValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4RequestGroupActionValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4RequestGroupConditionValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4RequestGroupRelatedActionValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4SubstitutionValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ValueSetContainsValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ValueSetFilterValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ValueSetIncludeValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ValueSetParameterValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validates the [element] against the R4 spec.
 */
fun <T : Element<T>> validateElement(element: Element<T>, parentContext: LocationContext?): Validation {
    return when (element) {
        // r4.datatype
        is Address -> element.validate(R4AddressValidator, parentContext)
        is Annotation -> element.validate(R4AnnotationValidator, parentContext)
        is Attachment -> element.validate(R4AttachmentValidator, parentContext)
        is CodeFilter -> element.validate(R4CodeFilterValidator, parentContext)
        is ContactPoint -> element.validate(R4ContactPointValidator, parentContext)
        is Contributor -> element.validate(R4ContributorValidator, parentContext)
        is DataRequirement -> element.validate(R4DataRequirementValidator, parentContext)
        is DateFilter -> element.validate(R4DateFilterValidator, parentContext)
        is Dosage -> element.validate(R4DosageValidator, parentContext)
        is DoseAndRate -> element.validate(R4DoseAndRateValidator, parentContext)
        is Expression -> element.validate(R4ExpressionValidator, parentContext)
        is Extension -> element.validate(R4ExtensionValidator, parentContext)
        is HumanName -> element.validate(R4HumanNameValidator, parentContext)
        is Identifier -> element.validate(R4IdentifierValidator, parentContext)
        is Narrative -> element.validate(R4NarrativeValidator, parentContext)
        is ParameterDefinition -> element.validate(R4ParameterDefinitionValidator, parentContext)
        is Period -> element.validate(R4PeriodValidator, parentContext)
        is Range -> element.validate(R4RangeValidator, parentContext)
        is Ratio -> element.validate(R4RatioValidator, parentContext)
        is Reference -> element.validate(R4ReferenceValidator, parentContext)
        is RelatedArtifact -> element.validate(R4RelatedArtifactValidator, parentContext)
        is Sort -> element.validate(R4SortValidator, parentContext)
        is TimingRepeat -> element.validate(R4TimingRepeatValidator, parentContext)
        is TriggerDefinition -> element.validate(R4TriggerDefinitionValidator, parentContext)
        is UsageContext -> element.validate(R4UsageContextValidator, parentContext)

        // r4.datatype.quantity
        is Age -> element.validate(R4AgeValidator, parentContext)
        is Count -> element.validate(R4CountValidator, parentContext)
        is Distance -> element.validate(R4DistanceValidator, parentContext)
        is Duration -> element.validate(R4DurationValidator, parentContext)
        is MoneyQuantity -> element.validate(R4MoneyQuantityValidator, parentContext)
        is Quantity -> element.validate(R4QuantityValidator, parentContext)
        is SimpleQuantity -> element.validate(R4SimpleQuantityValidator, parentContext)

        // r4.resource
        is AvailableTime -> element.validate(R4AvailableTimeValidator, parentContext)
        is BundleRequest -> element.validate(R4BundleRequestValidator, parentContext)
        is BundleSearch -> element.validate(R4BundleSearchValidator, parentContext)
        is CarePlanActivity -> element.validate(R4CarePlanActivityValidator, parentContext)
        is CarePlanDetail -> element.validate(R4CarePlanDetailValidator, parentContext)
        is CareTeamParticipant -> element.validate(R4CareTeamParticipantValidator, parentContext)
        is CommunicationPayload -> element.validate(R4CommunicationPayloadValidator, parentContext)
        is ConceptMapTarget -> element.validate(R4ConceptMapTargetValidator, parentContext)
        is ConceptMapUnmapped -> element.validate(R4ConceptMapUnmappedValidator, parentContext)
        is ConditionEvidence -> element.validate(R4ConditionEvidenceValidator, parentContext)
        is ConditionStage -> element.validate(R4ConditionStageValidator, parentContext)
        is DocumentReferenceRelatesTo -> element.validate(R4DocumentReferenceRelatesToValidator, parentContext)
        is EncounterLocation -> element.validate(R4EncounterLocationValidator, parentContext)
        is EncounterStatusHistory -> element.validate(R4EncounterStatusHistoryValidator, parentContext)
        is ImmunizationEducation -> element.validate(R4ImmunizationEducationValidator, parentContext)
        is ImmunizationProtocolApplied -> element.validate(R4ImmunizationProtocolAppliedValidator, parentContext)
        is Ingredient -> element.validate(R4IngredientValidator, parentContext)
        is LocationHoursOfOperation -> element.validate(R4LocationHoursOfOperationValidator, parentContext)
        is MedicationAdministrationDosage -> element.validate(R4MedAdminDosageValidator, parentContext)
        is ObservationComponent -> element.validate(R4ObservationComponentValidator, parentContext)
        is ObservationReferenceRange -> element.validate(R4ObservationReferenceRangeValidator, parentContext)
        is Participant -> element.validate(R4ParticipantValidator, parentContext)
        is PatientContact -> element.validate(R4PatientContactValidator, parentContext)
        is PatientLink -> element.validate(R4PatientLinkValidator, parentContext)
        is RequestGroupAction -> element.validate(R4RequestGroupActionValidator, parentContext)
        is RequestGroupCondition -> element.validate(R4RequestGroupConditionValidator, parentContext)
        is RequestGroupRelatedAction -> element.validate(R4RequestGroupRelatedActionValidator, parentContext)
        is Substitution -> element.validate(R4SubstitutionValidator, parentContext)
        is ValueSetContains -> element.validate(R4ValueSetContainsValidator, parentContext)
        is ValueSetFilter -> element.validate(R4ValueSetFilterValidator, parentContext)
        is ValueSetInclude -> element.validate(R4ValueSetIncludeValidator, parentContext)
        is ValueSetParameter -> element.validate(R4ValueSetParameterValidator, parentContext)

        // no unique validator class
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
