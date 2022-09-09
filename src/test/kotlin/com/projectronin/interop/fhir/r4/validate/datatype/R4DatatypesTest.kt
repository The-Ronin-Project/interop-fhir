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
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Element
import com.projectronin.interop.fhir.r4.datatype.Expression
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Ingredient
import com.projectronin.interop.fhir.r4.datatype.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.datatype.LocationPosition
import com.projectronin.interop.fhir.r4.datatype.Meta
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
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.validate.datatype.primitive.validatePrimitive
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.ProfileValidator
import com.projectronin.interop.fhir.validate.validation
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class R4DatatypesTest {
    private val failedValidation = validation {
        notNull(null) { "test exception" }
    }
    private val locationContext = LocationContext("Sample", "field")

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `can validate DynamicValue of Primitive`() {
        val unsignedInt = UnsignedInt(1)

        mockkStatic("com.projectronin.interop.fhir.r4.validate.datatype.primitive.R4PrimitivesKt")
        every { validatePrimitive(unsignedInt, locationContext) } returns failedValidation

        val dynamicValue = DynamicValue(DynamicValueType.UNSIGNED_INT, unsignedInt)

        val validation = validateDynamicValue(dynamicValue, locationContext)
        assertEquals(1, validation.errors().size)

        unmockkStatic("com.projectronin.interop.fhir.r4.validate.datatype.primitive.R4PrimitivesKt")
    }

    @Test
    fun `can validate DynamicValue of Element`() {
        val address = mockk<Address>()

        mockkStatic("com.projectronin.interop.fhir.r4.validate.datatype.R4DatatypesKt")
        every { validateDatatype(address, locationContext) } returns failedValidation

        val dynamicValue = DynamicValue(DynamicValueType.ADDRESS, address)

        val validation = validateDynamicValue(dynamicValue, locationContext)
        assertEquals(1, validation.errors().size)

        unmockkStatic("com.projectronin.interop.fhir.r4.validate.datatype.R4DatatypesKt")
    }

    @Test
    fun `can validate DynamicValue of other type`() {
        val dynamicValue = DynamicValue(DynamicValueType.BOOLEAN, true)

        val validation = validateDynamicValue(dynamicValue, locationContext)
        assertEquals(0, validation.errors().size)
    }

    @Test
    fun `can validate Address`() {
        val address = mockk<Address>()
        every { address.validate(R4AddressValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(address, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Age`() {
        val age = mockk<Age>()
        every { age.validate(R4AgeValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(age, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Annotation`() {
        val annotation = mockk<Annotation>()
        every { annotation.validate(R4AnnotationValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(annotation, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Attachment`() {
        val attachment = mockk<Attachment>()
        every { attachment.validate(R4AttachmentValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(attachment, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate AvailableTime`() {
        val availableTime = mockk<AvailableTime>()
        every { availableTime.validate(R4AvailableTimeValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(availableTime, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate BundleLink`() {
        val bundleLink = mockk<BundleLink>()
        every { bundleLink.validate(R4BundleLinkValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(bundleLink, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate BundleRequest`() {
        val bundleRequest = mockk<BundleRequest>()
        every { bundleRequest.validate(R4BundleRequestValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(bundleRequest, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate BundleResponse`() {
        val bundleResponse = mockk<BundleResponse>()
        every { bundleResponse.validate(R4BundleResponseValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(bundleResponse, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate BundleSearch`() {
        val bundleSearch = mockk<BundleSearch>()
        every { bundleSearch.validate(R4BundleSearchValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(bundleSearch, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate CodeFilter`() {
        val codeFilter = mockk<CodeFilter>()
        every { codeFilter.validate(R4CodeFilterValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(codeFilter, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Communication`() {
        val communication = mockk<Communication>()
        every { communication.validate(R4CommunicationValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(communication, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate ConditionEvidence`() {
        val conditionEvidence = mockk<ConditionEvidence>()
        every { conditionEvidence.validate(R4ConditionEvidenceValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(conditionEvidence, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate ConditionStage`() {
        val conditionStage = mockk<ConditionStage>()
        every { conditionStage.validate(R4ConditionStageValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(conditionStage, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Contact`() {
        val contact = mockk<Contact>()
        every { contact.validate(R4ContactValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(contact, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate ContactPoint`() {
        val contactPoint = mockk<ContactPoint>()
        every { contactPoint.validate(R4ContactPointValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(contactPoint, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Contributor`() {
        val contributor = mockk<Contributor>()
        every { contributor.validate(R4ContributorValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(contributor, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Count`() {
        val count = mockk<Count>()
        every { count.validate(R4CountValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(count, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate DataRequirement`() {
        val dataRequirement = mockk<DataRequirement>()
        every { dataRequirement.validate(R4DataRequirementValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(dataRequirement, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate DateFilter`() {
        val dateFilter = mockk<DateFilter>()
        every { dateFilter.validate(R4DateFilterValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(dateFilter, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Distance`() {
        val distance = mockk<Distance>()
        every { distance.validate(R4DistanceValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(distance, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Dosage`() {
        val dosage = mockk<Dosage>()
        every { dosage.validate(R4DosageValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(dosage, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate DoseAndRate`() {
        val doseAndRate = mockk<DoseAndRate>()
        every { doseAndRate.validate(R4DoseAndRateValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(doseAndRate, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Duration`() {
        val duration = mockk<Duration>()
        every { duration.validate(R4DurationValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(duration, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Expression`() {
        val expression = mockk<Expression>()
        every { expression.validate(R4ExpressionValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(expression, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Extension`() {
        val extension = mockk<Extension>()
        every { extension.validate(R4ExtensionValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(extension, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate HumanName`() {
        val humanName = mockk<HumanName>()
        every { humanName.validate(R4HumanNameValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(humanName, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Identifier`() {
        val identifier = mockk<Identifier>()
        every { identifier.validate(R4IdentifierValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(identifier, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Ingredient`() {
        val ingredient = mockk<Ingredient>()
        every { ingredient.validate(R4IngredientValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(ingredient, locationContext)
        assertEquals(1, validation.errors().size)
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

        val validation = validateDatatype(locationHoursOfOperation, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate LocationPosition`() {
        val locationPosition = mockk<LocationPosition>()
        every { locationPosition.validate(R4LocationPositionValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(locationPosition, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate MoneyQuantity`() {
        val moneyQuantity = mockk<MoneyQuantity>()
        every { moneyQuantity.validate(R4MoneyQuantityValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(moneyQuantity, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Narrative`() {
        val narrative = mockk<Narrative>()
        every { narrative.validate(R4NarrativeValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(narrative, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate NotAvailable`() {
        val notAvailable = mockk<NotAvailable>()
        every { notAvailable.validate(R4NotAvailableValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(notAvailable, locationContext)
        assertEquals(1, validation.errors().size)
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

        val validation = validateDatatype(observationComponent, locationContext)
        assertEquals(1, validation.errors().size)
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

        val validation = validateDatatype(observationReferenceRange, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate ParameterDefinition`() {
        val parameterDefinition = mockk<ParameterDefinition>()
        every { parameterDefinition.validate(R4ParameterDefinitionValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(parameterDefinition, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Participant`() {
        val participant = mockk<Participant>()
        every { participant.validate(R4ParticipantValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(participant, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate PatientLink`() {
        val patientLink = mockk<PatientLink>()
        every { patientLink.validate(R4PatientLinkValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(patientLink, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Period`() {
        val period = mockk<Period>()
        every { period.validate(R4PeriodValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(period, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Qualification`() {
        val qualification = mockk<Qualification>()
        every { qualification.validate(R4QualificationValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(qualification, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Quantity`() {
        val quantity = mockk<Quantity>()
        every { quantity.validate(R4QuantityValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(quantity, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Range`() {
        val range = mockk<Range>()
        every { range.validate(R4RangeValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(range, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Ratio`() {
        val ratio = mockk<Ratio>()
        every { ratio.validate(R4RatioValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(ratio, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Reference`() {
        val reference = mockk<Reference>()
        every { reference.validate(R4ReferenceValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(reference, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate RelatedArtifact`() {
        val relatedArtifact = mockk<RelatedArtifact>()
        every { relatedArtifact.validate(R4RelatedArtifactValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(relatedArtifact, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate SampledData`() {
        val sampledData = mockk<SampledData>()
        every { sampledData.validate(R4SampledDataValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(sampledData, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Signature`() {
        val signature = mockk<Signature>()
        every { signature.validate(R4SignatureValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(signature, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate SimpleQuantity`() {
        val simpleQuantity = mockk<SimpleQuantity>()
        every { simpleQuantity.validate(R4SimpleQuantityValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(simpleQuantity, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate Sort`() {
        val sort = mockk<Sort>()
        every { sort.validate(R4SortValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(sort, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate TimingRepeat`() {
        val timingRepeat = mockk<TimingRepeat>()
        every { timingRepeat.validate(R4TimingRepeatValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(timingRepeat, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate TriggerDefinition`() {
        val triggerDefinition = mockk<TriggerDefinition>()
        every { triggerDefinition.validate(R4TriggerDefinitionValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(triggerDefinition, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate UsageContext`() {
        val usageContext = mockk<UsageContext>()
        every { usageContext.validate(R4UsageContextValidator, locationContext) } returns failedValidation

        val validation = validateDatatype(usageContext, locationContext)
        assertEquals(1, validation.errors().size)
    }

    @Test
    fun `can validate other datatypes with no constraints`() {
        val validatorSlot = slot<ProfileValidator<Meta>>()
        val meta = mockk<Meta>()
        every { meta.validate(capture(validatorSlot), locationContext) } returns failedValidation

        val validation = validateDatatype(meta, locationContext)
        assertEquals(1, validation.errors().size)
        assertTrue(validatorSlot.captured is GenericElementValidator)
    }

    @Test
    fun `GenericElementValidator processes child elements`() {
        data class MadeUpElement(
            override val id: String? = null,
            override val extension: List<Extension> = listOf(),
            val versionId: Id? = null
        ) : Element<MadeUpElement>

        mockkStatic("com.projectronin.interop.fhir.r4.validate.datatype.primitive.R4PrimitivesKt")
        val id = mockk<Id>()
        every { validatePrimitive(id, LocationContext("Sample", "field.versionId")) } returns failedValidation

        val madeUpElement = MadeUpElement(versionId = id)

        val validator = GenericElementValidator<MadeUpElement>()
        val validation = validator.validate(madeUpElement, locationContext)
        assertEquals(1, validation.errors().size)
    }
}
