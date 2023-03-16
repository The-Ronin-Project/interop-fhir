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
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.MoneyQuantity
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.ParameterDefinition
import com.projectronin.interop.fhir.r4.datatype.Period
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
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.r4.resource.AvailableTime
import com.projectronin.interop.fhir.r4.resource.BundleLink
import com.projectronin.interop.fhir.r4.resource.BundleRequest
import com.projectronin.interop.fhir.r4.resource.BundleResponse
import com.projectronin.interop.fhir.r4.resource.BundleSearch
import com.projectronin.interop.fhir.r4.resource.CarePlanActivity
import com.projectronin.interop.fhir.r4.resource.CarePlanDetail
import com.projectronin.interop.fhir.r4.resource.CareTeamParticipant
import com.projectronin.interop.fhir.r4.resource.CommunicationPayload
import com.projectronin.interop.fhir.r4.resource.ConceptMapDependsOn
import com.projectronin.interop.fhir.r4.resource.ConceptMapElement
import com.projectronin.interop.fhir.r4.resource.ConceptMapGroup
import com.projectronin.interop.fhir.r4.resource.ConceptMapTarget
import com.projectronin.interop.fhir.r4.resource.ConceptMapUnmapped
import com.projectronin.interop.fhir.r4.resource.ConditionEvidence
import com.projectronin.interop.fhir.r4.resource.ConditionStage
import com.projectronin.interop.fhir.r4.resource.DiagnosticReportMedia
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceContent
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceRelatesTo
import com.projectronin.interop.fhir.r4.resource.EncounterClassHistory
import com.projectronin.interop.fhir.r4.resource.EncounterDiagnosis
import com.projectronin.interop.fhir.r4.resource.EncounterLocation
import com.projectronin.interop.fhir.r4.resource.EncounterStatusHistory
import com.projectronin.interop.fhir.r4.resource.Ingredient
import com.projectronin.interop.fhir.r4.resource.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.resource.LocationPosition
import com.projectronin.interop.fhir.r4.resource.NotAvailable
import com.projectronin.interop.fhir.r4.resource.ObservationComponent
import com.projectronin.interop.fhir.r4.resource.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.resource.Participant
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.resource.PatientCommunication
import com.projectronin.interop.fhir.r4.resource.PatientContact
import com.projectronin.interop.fhir.r4.resource.PatientLink
import com.projectronin.interop.fhir.r4.resource.Qualification
import com.projectronin.interop.fhir.r4.resource.Substitution
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
import com.projectronin.interop.fhir.r4.validate.datatype.R4SampledDataValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4SignatureValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4SimpleQuantityValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4SortValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4TimingRepeatValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4TriggerDefinitionValidator
import com.projectronin.interop.fhir.r4.validate.datatype.R4UsageContextValidator
import com.projectronin.interop.fhir.r4.validate.datatype.primitive.validatePrimitive
import com.projectronin.interop.fhir.r4.validate.resource.R4AvailableTimeValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4BundleLinkValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4BundleRequestValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4BundleResponseValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4BundleSearchValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CarePlanActivityValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CarePlanDetailValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CareTeamParticipantValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4CommunicationPayloadValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConceptMapDependsOnValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConceptMapElementValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConceptMapGroupValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConceptMapTargetValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConceptMapUnmappedValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConditionEvidenceValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ConditionStageValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4DiagnosticReportMediaValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4DocumentReferenceContentValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4DocumentReferenceRelatesToValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4EncounterClassHistoryValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4EncounterDiagnosisValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4EncounterLocationValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4EncounterStatusHistoryValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4IngredientValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4LocationHoursOfOperationValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4LocationPositionValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4NotAvailableValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ObservationComponentValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ObservationReferenceRangeValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4ParticipantValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4PatientCommunicationValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4PatientContactValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4PatientLinkValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4QualificationValidator
import com.projectronin.interop.fhir.r4.validate.resource.R4SubstitutionValidator
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
    fun `can validate Address`() {
        val address = mockk<Address>()
        every { address.validate(R4AddressValidator, locationContext) } returns failedValidation

        val validation = validateElement(address, locationContext)
        assertEquals(1, validation.issues().size)
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
    fun `can validate AvailableTime`() {
        val availableTime = mockk<AvailableTime>()
        every { availableTime.validate(R4AvailableTimeValidator, locationContext) } returns failedValidation

        val validation = validateElement(availableTime, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate BundleLink`() {
        val bundleLink = mockk<BundleLink>()
        every { bundleLink.validate(R4BundleLinkValidator, locationContext) } returns failedValidation

        val validation = validateElement(bundleLink, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate BundleRequest`() {
        val bundleRequest = mockk<BundleRequest>()
        every { bundleRequest.validate(R4BundleRequestValidator, locationContext) } returns failedValidation

        val validation = validateElement(bundleRequest, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate BundleResponse`() {
        val bundleResponse = mockk<BundleResponse>()
        every { bundleResponse.validate(R4BundleResponseValidator, locationContext) } returns failedValidation

        val validation = validateElement(bundleResponse, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate BundleSearch`() {
        val bundleSearch = mockk<BundleSearch>()
        every { bundleSearch.validate(R4BundleSearchValidator, locationContext) } returns failedValidation

        val validation = validateElement(bundleSearch, locationContext)
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
    fun `can validate ConceptMapDependsOn`() {
        val conceptMapDependsOn = mockk<ConceptMapDependsOn>()
        every { conceptMapDependsOn.validate(R4ConceptMapDependsOnValidator, locationContext) } returns failedValidation

        val validation = validateElement(conceptMapDependsOn, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ConceptMapElement`() {
        val conceptMapElement = mockk<ConceptMapElement>()
        every { conceptMapElement.validate(R4ConceptMapElementValidator, locationContext) } returns failedValidation

        val validation = validateElement(conceptMapElement, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate ConceptMapGroup`() {
        val conceptMapGroup = mockk<ConceptMapGroup>()
        every { conceptMapGroup.validate(R4ConceptMapGroupValidator, locationContext) } returns failedValidation

        val validation = validateElement(conceptMapGroup, locationContext)
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
    fun `can validate Contributor`() {
        val contributor = mockk<Contributor>()
        every { contributor.validate(R4ContributorValidator, locationContext) } returns failedValidation

        val validation = validateElement(contributor, locationContext)
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
    fun `can validate DiagnosticReportMedia`() {
        val diagnosticReportMedia = mockk<DiagnosticReportMedia>()
        every {
            diagnosticReportMedia.validate(
                R4DiagnosticReportMediaValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(diagnosticReportMedia, locationContext)
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
    fun `can validate DocumentReferenceContent`() {
        val content = mockk<DocumentReferenceContent>()
        every { content.validate(R4DocumentReferenceContentValidator, locationContext) } returns failedValidation

        val validation = validateElement(content, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate DocumentReferenceRelatesTo`() {
        val relatesTo = mockk<DocumentReferenceRelatesTo>()
        every { relatesTo.validate(R4DocumentReferenceRelatesToValidator, locationContext) } returns failedValidation

        val validation = validateElement(relatesTo, locationContext)
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
    fun `can validate EncounterClassHistory`() {
        val encounterClassHistory = mockk<EncounterClassHistory>()
        every {
            encounterClassHistory.validate(
                R4EncounterClassHistoryValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(encounterClassHistory, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate EncounterDiagnosis`() {
        val encounterDiagnosis = mockk<EncounterDiagnosis>()
        every { encounterDiagnosis.validate(R4EncounterDiagnosisValidator, locationContext) } returns failedValidation

        val validation = validateElement(encounterDiagnosis, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate EncounterLocation`() {
        val encounterLocation = mockk<EncounterLocation>()
        every { encounterLocation.validate(R4EncounterLocationValidator, locationContext) } returns failedValidation

        val validation = validateElement(encounterLocation, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate EncounterStatusHistory`() {
        val encounterStatusHistory = mockk<EncounterStatusHistory>()
        every {
            encounterStatusHistory.validate(
                R4EncounterStatusHistoryValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(encounterStatusHistory, locationContext)
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
    fun `can validate HumanName`() {
        val humanName = mockk<HumanName>()
        every { humanName.validate(R4HumanNameValidator, locationContext) } returns failedValidation

        val validation = validateElement(humanName, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Identifier`() {
        val identifier = mockk<Identifier>()
        every { identifier.validate(R4IdentifierValidator, locationContext) } returns failedValidation

        val validation = validateElement(identifier, locationContext)
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
    fun `can validate LocationHoursOfOperation`() {
        val locationHoursOfOperation = mockk<LocationHoursOfOperation>()
        every {
            locationHoursOfOperation.validate(
                R4LocationHoursOfOperationValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(locationHoursOfOperation, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate LocationPosition`() {
        val locationPosition = mockk<LocationPosition>()
        every { locationPosition.validate(R4LocationPositionValidator, locationContext) } returns failedValidation

        val validation = validateElement(locationPosition, locationContext)
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
    fun `can validate Narrative`() {
        val narrative = mockk<Narrative>()
        every { narrative.validate(R4NarrativeValidator, locationContext) } returns failedValidation

        val validation = validateElement(narrative, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate NotAvailable`() {
        val notAvailable = mockk<NotAvailable>()
        every { notAvailable.validate(R4NotAvailableValidator, locationContext) } returns failedValidation

        val validation = validateElement(notAvailable, locationContext)
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
    fun `can validate ParameterDefinition`() {
        val parameterDefinition = mockk<ParameterDefinition>()
        every { parameterDefinition.validate(R4ParameterDefinitionValidator, locationContext) } returns failedValidation

        val validation = validateElement(parameterDefinition, locationContext)
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
    fun `can validate PatientCommunication`() {
        val patientCommunication = mockk<PatientCommunication>()
        every {
            patientCommunication.validate(
                R4PatientCommunicationValidator,
                locationContext
            )
        } returns failedValidation

        val validation = validateElement(patientCommunication, locationContext)
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
    fun `can validate PatientLink`() {
        val patientLink = mockk<PatientLink>()
        every { patientLink.validate(R4PatientLinkValidator, locationContext) } returns failedValidation

        val validation = validateElement(patientLink, locationContext)
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
    fun `can validate Qualification`() {
        val qualification = mockk<Qualification>()
        every { qualification.validate(R4QualificationValidator, locationContext) } returns failedValidation

        val validation = validateElement(qualification, locationContext)
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
    fun `can validate RelatedArtifact`() {
        val relatedArtifact = mockk<RelatedArtifact>()
        every { relatedArtifact.validate(R4RelatedArtifactValidator, locationContext) } returns failedValidation

        val validation = validateElement(relatedArtifact, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate SampledData`() {
        val sampledData = mockk<SampledData>()
        every { sampledData.validate(R4SampledDataValidator, locationContext) } returns failedValidation

        val validation = validateElement(sampledData, locationContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `can validate Signature`() {
        val signature = mockk<Signature>()
        every { signature.validate(R4SignatureValidator, locationContext) } returns failedValidation

        val validation = validateElement(signature, locationContext)
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
    fun `can validate Sort`() {
        val sort = mockk<Sort>()
        every { sort.validate(R4SortValidator, locationContext) } returns failedValidation

        val validation = validateElement(sort, locationContext)
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
