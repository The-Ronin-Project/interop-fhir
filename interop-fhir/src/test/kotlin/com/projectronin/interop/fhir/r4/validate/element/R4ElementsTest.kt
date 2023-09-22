package com.projectronin.interop.fhir.r4.validate.element

import com.projectronin.interop.fhir.r4.datatype.Age
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeFilter
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Count
import com.projectronin.interop.fhir.r4.datatype.DataRequirement
import com.projectronin.interop.fhir.r4.datatype.DateFilter
import com.projectronin.interop.fhir.r4.datatype.Distance
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DoseAndRate
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.Expression
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.MoneyQuantity
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.TimingRepeat
import com.projectronin.interop.fhir.r4.datatype.TriggerDefinition
import com.projectronin.interop.fhir.r4.datatype.UsageContext
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.r4.resource.CarePlanActivity
import com.projectronin.interop.fhir.r4.resource.CarePlanDetail
import com.projectronin.interop.fhir.r4.resource.CareTeamParticipant
import com.projectronin.interop.fhir.r4.resource.CommunicationPayload
import com.projectronin.interop.fhir.r4.resource.ConceptMapTarget
import com.projectronin.interop.fhir.r4.resource.ConceptMapUnmapped
import com.projectronin.interop.fhir.r4.resource.ConditionEvidence
import com.projectronin.interop.fhir.r4.resource.ConditionStage
import com.projectronin.interop.fhir.r4.resource.ImmunizationEducation
import com.projectronin.interop.fhir.r4.resource.ImmunizationProtocolApplied
import com.projectronin.interop.fhir.r4.resource.Ingredient
import com.projectronin.interop.fhir.r4.resource.MedicationAdministrationDosage
import com.projectronin.interop.fhir.r4.resource.ObservationComponent
import com.projectronin.interop.fhir.r4.resource.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.resource.Participant
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.resource.PatientContact
import com.projectronin.interop.fhir.r4.resource.RequestGroupAction
import com.projectronin.interop.fhir.r4.resource.RequestGroupRelatedAction
import com.projectronin.interop.fhir.r4.resource.Substitution
import com.projectronin.interop.fhir.r4.resource.ValueSetContains
import com.projectronin.interop.fhir.r4.resource.ValueSetInclude
import com.projectronin.interop.fhir.r4.resource.ValueSetParameter
import com.projectronin.interop.fhir.r4.validate.datatype.R4AgeValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4AnnotationValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4AttachmentValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4CodeFilterValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4ContactPointValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4CountValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DataRequirementValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DateFilterValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DistanceValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DosageValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DoseAndRateValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4DurationValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4ExpressionValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4ExtensionValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4MoneyQuantityValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4PeriodValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4QuantityValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4RangeValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4RatioValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4ReferenceValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4SimpleQuantityValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4TimingRepeatValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4TriggerDefinitionValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4UsageContextValidator
import com.projectronin.interop.fhir.r4.validate.datatype.primitive.validatePrimitive
import com.projectronin.interop.fhir.r4.validate.resource.R4CarePlanActivityValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CarePlanDetailValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CareTeamParticipantValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CommunicationPayloadValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConceptMapTargetValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConceptMapUnmappedValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConditionEvidenceValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConditionStageValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ImmunizationEducationValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ImmunizationProtocolAppliedValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4IngredientValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4MedAdminDosageValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ObservationComponentValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ObservationReferenceRangeValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ParticipantValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4PatientContactValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4RequestGroupActionValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4RequestGroupRelatedActionValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4SubstitutionValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ValueSetContainsValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ValueSetIncludeValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ValueSetParameterValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.ProfileValidator
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.validation
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class R4ElementsTest {
    private val failedValidation = validation {
        checkNotNull(null, RequiredFieldError(Patient::name), null)
    }
    private val locationContext = LocationContext("Sample", "field")

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `can validate Age`() {
        val age = mockk<Age>()
        every { age.validate(R4AgeValidator, locationContext) } returns failedValidation

        val validation = validateElement(age, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Annotation`() {
        val annotation = mockk<Annotation>()
        every { annotation.validate(R4AnnotationValidator, locationContext) } returns failedValidation

        val validation = validateElement(annotation, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Attachment`() {
        val attachment = mockk<Attachment>()
        every { attachment.validate(R4AttachmentValidator, locationContext) } returns failedValidation

        val validation = validateElement(attachment, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate CarePlanActivity`() {
        val carePlanActivity = mockk<CarePlanActivity>()
        every { carePlanActivity.validate(R4CarePlanActivityValidator, locationContext) } returns failedValidation

        val validation = validateElement(carePlanActivity, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate CarePlanDetail`() {
        val carePlanDetail = mockk<CarePlanDetail>()
        every { carePlanDetail.validate(R4CarePlanDetailValidator, locationContext) } returns failedValidation

        val validation = validateElement(carePlanDetail, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate CareTeamParticipant`() {
        val careTeamParticipant = mockk<CareTeamParticipant>()
        every { careTeamParticipant.validate(R4CareTeamParticipantValidator, locationContext) } returns failedValidation

        val validation = validateElement(careTeamParticipant, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate CodeFilter`() {
        val codeFilter = mockk<CodeFilter>()
        every { codeFilter.validate(R4CodeFilterValidator, locationContext) } returns failedValidation

        val validation = validateElement(codeFilter, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate CommunicationPayload`() {
        val communicationPayload = mockk<CommunicationPayload>()
        every {
            communicationPayload.validate(
                R4CommunicationPayloadValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(communicationPayload, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ConceptMapTarget`() {
        val conceptMapTarget = mockk<ConceptMapTarget>()
        every { conceptMapTarget.validate(R4ConceptMapTargetValidator, locationContext) } returns failedValidation

        val validation = validateElement(conceptMapTarget, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ConceptMapUnmapped`() {
        val conceptMapUnmapped = mockk<ConceptMapUnmapped>()
        every { conceptMapUnmapped.validate(R4ConceptMapUnmappedValidator, locationContext) } returns failedValidation

        val validation = validateElement(conceptMapUnmapped, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ConditionEvidence`() {
        val conditionEvidence = mockk<ConditionEvidence>()
        every { conditionEvidence.validate(R4ConditionEvidenceValidator, locationContext) } returns failedValidation

        val validation = validateElement(conditionEvidence, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ConditionStage`() {
        val conditionStage = mockk<ConditionStage>()
        every { conditionStage.validate(R4ConditionStageValidator, locationContext) } returns failedValidation

        val validation = validateElement(conditionStage, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ContactPoint`() {
        val contactPoint = mockk<ContactPoint>()
        every { contactPoint.validate(R4ContactPointValidator, locationContext) } returns failedValidation

        val validation = validateElement(contactPoint, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Count`() {
        val count = mockk<Count>()
        every { count.validate(R4CountValidator, locationContext) } returns failedValidation

        val validation = validateElement(count, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate DataRequirement`() {
        val dataRequirement = mockk<DataRequirement>()
        every { dataRequirement.validate(R4DataRequirementValidator, locationContext) } returns failedValidation

        val validation = validateElement(dataRequirement, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate DateFilter`() {
        val dateFilter = mockk<DateFilter>()
        every { dateFilter.validate(R4DateFilterValidator, locationContext) } returns failedValidation

        val validation = validateElement(dateFilter, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Distance`() {
        val distance = mockk<Distance>()
        every { distance.validate(R4DistanceValidator, locationContext) } returns failedValidation

        val validation = validateElement(distance, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Dosage`() {
        val dosage = mockk<Dosage>()
        every { dosage.validate(R4DosageValidator, locationContext) } returns failedValidation

        val validation = validateElement(dosage, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate DoseAndRate`() {
        val doseAndRate = mockk<DoseAndRate>()
        every { doseAndRate.validate(R4DoseAndRateValidator, locationContext) } returns failedValidation

        val validation = validateElement(doseAndRate, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Duration`() {
        val duration = mockk<Duration>()
        every { duration.validate(R4DurationValidator, locationContext) } returns failedValidation

        val validation = validateElement(duration, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Expression`() {
        val expression = mockk<Expression>()
        every { expression.validate(R4ExpressionValidator, locationContext) } returns failedValidation

        val validation = validateElement(expression, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Extension`() {
        val extension = mockk<Extension>()
        every { extension.validate(R4ExtensionValidator, locationContext) } returns failedValidation

        val validation = validateElement(extension, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ImmunizationEducation`() {
        val immunizationEducation = mockk<ImmunizationEducation>()
        every {
            immunizationEducation.validate(
                R4ImmunizationEducationValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(immunizationEducation, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ImmunizationProtocolApplied`() {
        val immunizationProtocolApplied = mockk<ImmunizationProtocolApplied>()
        every {
            immunizationProtocolApplied.validate(
                R4ImmunizationProtocolAppliedValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(immunizationProtocolApplied, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Ingredient`() {
        val ingredient = mockk<Ingredient>()
        every { ingredient.validate(R4IngredientValidator, locationContext) } returns failedValidation

        val validation = validateElement(ingredient, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate MedicationAdministrationDosage`() {
        val medicationAdministrationDosage = mockk<MedicationAdministrationDosage>()
        every {
            medicationAdministrationDosage.validate(
                R4MedAdminDosageValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(medicationAdministrationDosage, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate MoneyQuantity`() {
        val moneyQuantity = mockk<MoneyQuantity>()
        every { moneyQuantity.validate(R4MoneyQuantityValidator, locationContext) } returns failedValidation

        val validation = validateElement(moneyQuantity, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ObservationComponent`() {
        val observationComponent = mockk<ObservationComponent>()
        every {
            observationComponent.validate(
                R4ObservationComponentValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(observationComponent, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ObservationReferenceRange`() {
        val observationReferenceRange = mockk<ObservationReferenceRange>()
        every {
            observationReferenceRange.validate(
                R4ObservationReferenceRangeValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(observationReferenceRange, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Participant`() {
        val participant = mockk<Participant>()
        every { participant.validate(R4ParticipantValidator, locationContext) } returns failedValidation

        val validation = validateElement(participant, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate PatientContact`() {
        val contact = mockk<PatientContact>()
        every { contact.validate(R4PatientContactValidator, locationContext) } returns failedValidation

        val validation = validateElement(contact, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Period`() {
        val period = mockk<Period>()
        every { period.validate(R4PeriodValidator, locationContext) } returns failedValidation

        val validation = validateElement(period, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Quantity`() {
        val quantity = mockk<Quantity>()
        every { quantity.validate(R4QuantityValidator, locationContext) } returns failedValidation

        val validation = validateElement(quantity, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Range`() {
        val range = mockk<Range>()
        every { range.validate(R4RangeValidator, locationContext) } returns failedValidation

        val validation = validateElement(range, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Ratio`() {
        val ratio = mockk<Ratio>()
        every { ratio.validate(R4RatioValidator, locationContext) } returns failedValidation

        val validation = validateElement(ratio, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Reference`() {
        val reference = mockk<Reference>()
        every { reference.validate(R4ReferenceValidator, locationContext) } returns failedValidation

        val validation = validateElement(reference, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate RequestGroupAction`() {
        val requestGroupAction = mockk<RequestGroupAction>()
        every { requestGroupAction.validate(R4RequestGroupActionValidator, locationContext) } returns failedValidation

        val validation = validateElement(requestGroupAction, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate RequestGroupRelatedAction`() {
        val requestGroupRelatedAction = mockk<RequestGroupRelatedAction>()
        every {
            requestGroupRelatedAction.validate(
                R4RequestGroupRelatedActionValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(requestGroupRelatedAction, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate SimpleQuantity`() {
        val simpleQuantity = mockk<SimpleQuantity>()
        every { simpleQuantity.validate(R4SimpleQuantityValidator, locationContext) } returns failedValidation

        val validation = validateElement(simpleQuantity, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Substitution`() {
        val substitution = mockk<Substitution>()
        every { substitution.validate(R4SubstitutionValidator, locationContext) } returns failedValidation

        val validation = validateElement(substitution, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate TimingRepeat`() {
        val timingRepeat = mockk<TimingRepeat>()
        every { timingRepeat.validate(R4TimingRepeatValidator, locationContext) } returns failedValidation

        val validation = validateElement(timingRepeat, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate TriggerDefinition`() {
        val triggerDefinition = mockk<TriggerDefinition>()
        every { triggerDefinition.validate(R4TriggerDefinitionValidator, locationContext) } returns failedValidation

        val validation = validateElement(triggerDefinition, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate UsageContext`() {
        val usageContext = mockk<UsageContext>()
        every { usageContext.validate(R4UsageContextValidator, locationContext) } returns failedValidation

        val validation = validateElement(usageContext, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ValueSetContains`() {
        val valueSetContains = mockk<ValueSetContains>()
        every {
            valueSetContains.validate(
                R4ValueSetContainsValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(valueSetContains, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ValueSetInclude`() {
        val valueSetInclude = mockk<ValueSetInclude>()
        every {
            valueSetInclude.validate(
                R4ValueSetIncludeValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(valueSetInclude, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ValueSetParameter`() {
        val valueSetParameter = mockk<ValueSetParameter>()
        every {
            valueSetParameter.validate(
                R4ValueSetParameterValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(valueSetParameter, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate other datatypes with no constraints`() {
        val validatorSlot = slot<ProfileValidator<Meta>>()
        val meta = mockk<Meta>()
        every { meta.validate(capture(validatorSlot), locationContext) } returns failedValidation

        val validation = validateElement(meta, locationContext)
        assertEquals(1, validation.issues().size)
        assertTrue(validatorSlot.captured is GenericElementValidator)
    }

    @Test
    fun `GenericElementValidator processes child elements`() {
        data class MadeUpElement(
            override val id: FHIRString? = null,
            override val extension: List<Extension> = listOf(),
            val versionId: Id? = null
        ) : Element<MadeUpElement>

        mockkStatic("com.projectronin.interop.fhir.r4.validate.datatype.primitive.R4PrimitivesKt")
        val id = mockk<Id>()
        every { validatePrimitive(id, LocationContext("Sample", "field.versionId")) } returns failedValidation

        val madeUpElement = MadeUpElement(versionId = id)

        val validator = GenericElementValidator<MadeUpElement>()
        val validation = validator.validate(madeUpElement, locationContext)
        assertEquals(1, validation.issues().size)
    }
}
