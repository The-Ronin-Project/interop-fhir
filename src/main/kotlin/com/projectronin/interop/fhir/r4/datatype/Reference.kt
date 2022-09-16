package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A Reference to another resource.
 */
data class Reference(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val reference: String? = null,
    val type: Uri? = null,
    val identifier: Identifier? = null,
    val display: String? = null
) : Element<Reference> {

    fun isForType(type: String): Boolean {
        return this.type?.value == type || (reference?.contains("$type/") ?: false)
    }

    fun decomposedType(): String? {
        val matchResult = getMatchResult()
        return this.type?.value ?: matchResult?.destructured?.component4()
    }

    fun decomposedId(): String? {
        val matchResult = getMatchResult()
        return this.id ?: matchResult?.destructured?.component5()
    }

    private fun getMatchResult(): MatchResult? {
        return this.reference?.let { FHIR_RESOURCE_REGEX.matchEntire(it) }
    }

    companion object {
        val FHIR_RESOURCE_REGEX = Regex(
            """((http|https):\/\/([A-Za-z0-9\-\\\.\:\%${"\$"}]*\/)+)?(Account|ActivityDefinition|AdverseEvent|AllergyIntolerance|Appointment|AppointmentResponse|AuditEvent|Basic|Binary|BiologicallyDerivedProduct|BodyStructure|Bundle|CapabilityStatement|CarePlan|CareTeam|CatalogEntry|ChargeItem|ChargeItemDefinition|Claim|ClaimResponse|ClinicalImpression|CodeSystem|Communication|CommunicationRequest|CompartmentDefinition|Composition|ConceptMap|Condition|Consent|Contract|Coverage|CoverageEligibilityRequest|CoverageEligibilityResponse|DetectedIssue|Device|DeviceDefinition|DeviceMetric|DeviceRequest|DeviceUseStatement|DiagnosticReport|DocumentManifest|DocumentReference|EffectEvidenceSynthesis|Encounter|Endpoint|EnrollmentRequest|EnrollmentResponse|EpisodeOfCare|EventDefinition|Evidence|EvidenceVariable|ExampleScenario|ExplanationOfBenefit|FamilyMemberHistory|Flag|Goal|GraphDefinition|Group|GuidanceResponse|HealthcareService|ImagingStudy|Immunization|ImmunizationEvaluation|ImmunizationRecommendation|ImplementationGuide|InsurancePlan|Invoice|Library|Linkage|List|Location|Measure|MeasureReport|Media|Medication|MedicationAdministration|MedicationDispense|MedicationKnowledge|MedicationRequest|MedicationStatement|MedicinalProduct|MedicinalProductAuthorization|MedicinalProductContraindication|MedicinalProductIndication|MedicinalProductIngredient|MedicinalProductInteraction|MedicinalProductManufactured|MedicinalProductPackaged|MedicinalProductPharmaceutical|MedicinalProductUndesirableEffect|MessageDefinition|MessageHeader|MolecularSequence|NamingSystem|NutritionOrder|Observation|ObservationDefinition|OperationDefinition|OperationOutcome|Organization|OrganizationAffiliation|Patient|PaymentNotice|PaymentReconciliation|Person|PlanDefinition|Practitioner|PractitionerRole|Procedure|Provenance|Questionnaire|QuestionnaireResponse|RelatedPerson|RequestGroup|ResearchDefinition|ResearchElementDefinition|ResearchStudy|ResearchSubject|RiskAssessment|RiskEvidenceSynthesis|Schedule|SearchParameter|ServiceRequest|Slot|Specimen|SpecimenDefinition|StructureDefinition|StructureMap|Subscription|Substance|SubstanceNucleicAcid|SubstancePolymer|SubstanceProtein|SubstanceReferenceInformation|SubstanceSourceMaterial|SubstanceSpecification|SupplyDelivery|SupplyRequest|Task|TerminologyCapabilities|TestReport|TestScript|ValueSet|VerificationResult|VisionPrescription)\/([A-Za-z0-9\-\.]+)(\/_history\/[A-Za-z0-9\-\.]+)?"""
        )
    }
}
