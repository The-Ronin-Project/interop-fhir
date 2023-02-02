package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.Element

/**
 * A Reference to another resource.
 */
@JsonSerialize(using = ReferenceSerializer::class)
@JsonDeserialize(using = ReferenceDeserializer::class)
data class Reference(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val reference: FHIRString? = null,
    val type: Uri? = null,
    val identifier: Identifier? = null,
    val display: FHIRString? = null
) : Element<Reference> {

    fun isForType(type: String): Boolean {
        return this.type?.value == type || (reference?.value?.contains("$type/") ?: false)
    }

    fun decomposedType(): String? {
        return this.type?.value ?: this.reference?.value?.let { getType(it) }
    }

    fun decomposedId(): String? {
        return this.id?.value ?: this.reference?.value?.let { getId(it) }
    }

    companion object {
        // Default scope to allow for usage by other dependencies that may need more data than provided by the helper methods.
        val FHIR_RESOURCE_REGEX = Regex(
            """((http|https):\/\/([A-Za-z0-9\-\\\.\:\%${"\$"}]*\/)+)?(Account|ActivityDefinition|AdverseEvent|AllergyIntolerance|Appointment|AppointmentResponse|AuditEvent|Basic|Binary|BiologicallyDerivedProduct|BodyStructure|Bundle|CapabilityStatement|CarePlan|CareTeam|CatalogEntry|ChargeItem|ChargeItemDefinition|Claim|ClaimResponse|ClinicalImpression|CodeSystem|Communication|CommunicationRequest|CompartmentDefinition|Composition|ConceptMap|Condition|Consent|Contract|Coverage|CoverageEligibilityRequest|CoverageEligibilityResponse|DetectedIssue|Device|DeviceDefinition|DeviceMetric|DeviceRequest|DeviceUseStatement|DiagnosticReport|DocumentManifest|DocumentReference|EffectEvidenceSynthesis|Encounter|Endpoint|EnrollmentRequest|EnrollmentResponse|EpisodeOfCare|EventDefinition|Evidence|EvidenceVariable|ExampleScenario|ExplanationOfBenefit|FamilyMemberHistory|Flag|Goal|GraphDefinition|Group|GuidanceResponse|HealthcareService|ImagingStudy|Immunization|ImmunizationEvaluation|ImmunizationRecommendation|ImplementationGuide|InsurancePlan|Invoice|Library|Linkage|List|Location|Measure|MeasureReport|Media|Medication|MedicationAdministration|MedicationDispense|MedicationKnowledge|MedicationRequest|MedicationStatement|MedicinalProduct|MedicinalProductAuthorization|MedicinalProductContraindication|MedicinalProductIndication|MedicinalProductIngredient|MedicinalProductInteraction|MedicinalProductManufactured|MedicinalProductPackaged|MedicinalProductPharmaceutical|MedicinalProductUndesirableEffect|MessageDefinition|MessageHeader|MolecularSequence|NamingSystem|NutritionOrder|Observation|ObservationDefinition|OperationDefinition|OperationOutcome|Organization|OrganizationAffiliation|Patient|PaymentNotice|PaymentReconciliation|Person|PlanDefinition|Practitioner|PractitionerRole|Procedure|Provenance|Questionnaire|QuestionnaireResponse|RelatedPerson|RequestGroup|ResearchDefinition|ResearchElementDefinition|ResearchStudy|ResearchSubject|RiskAssessment|RiskEvidenceSynthesis|Schedule|SearchParameter|ServiceRequest|Slot|Specimen|SpecimenDefinition|StructureDefinition|StructureMap|Subscription|Substance|SubstanceNucleicAcid|SubstancePolymer|SubstanceProtein|SubstanceReferenceInformation|SubstanceSourceMaterial|SubstanceSpecification|SupplyDelivery|SupplyRequest|Task|TerminologyCapabilities|TestReport|TestScript|ValueSet|VerificationResult|VisionPrescription)\/([A-Za-z0-9\-\.]+)(\/_history\/[A-Za-z0-9\-\.]+)?"""
        )

        /**
         * Gets the type from the [reference]. This supports both simple references (e.g. Patient/123) and full references (e.g. http://fhirserver.com/Patient/123).
         */
        fun getType(reference: String): String? = FHIR_RESOURCE_REGEX.matchEntire(reference)?.destructured?.component4()

        /**
         * Gets the ID from the [reference]. This supports both simple references (e.g. Patient/123) and full references (e.g. http://fhirserver.com/Patient/123).
         */
        fun getId(reference: String): String? = FHIR_RESOURCE_REGEX.matchEntire(reference)?.destructured?.component5()
    }
}

class ReferenceSerializer : BaseFHIRSerializer<Reference>(Reference::class.java)
class ReferenceDeserializer : BaseFHIRDeserializer<Reference>(Reference::class.java)
