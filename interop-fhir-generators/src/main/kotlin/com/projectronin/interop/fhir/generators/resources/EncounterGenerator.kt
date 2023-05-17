package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodingGenerator
import com.projectronin.interop.fhir.generators.datatypes.EncounterParticipantGenerator
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.PeriodGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Duration
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.resource.Encounter
import com.projectronin.interop.fhir.r4.resource.EncounterClassHistory
import com.projectronin.interop.fhir.r4.resource.EncounterDiagnosis
import com.projectronin.interop.fhir.r4.resource.EncounterHospitalization
import com.projectronin.interop.fhir.r4.resource.EncounterLocation
import com.projectronin.interop.fhir.r4.resource.EncounterParticipant
import com.projectronin.interop.fhir.r4.resource.EncounterStatusHistory
import com.projectronin.interop.fhir.r4.valueset.EncounterStatus
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class EncounterGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<ContainedResource?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator(EncounterStatus::class),
    val statusHistory: ListDataGenerator<EncounterStatusHistory> = ListDataGenerator(
        0,
        EncounterStatusHistoryGenerator()
    ),
    val `class`: CodingGenerator = CodingGenerator(),
    val classHistory: ListDataGenerator<EncounterClassHistory> = ListDataGenerator(0, EncounterClassHistoryGenerator()),
    val type: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val serviceType: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val priority: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val subject: DataGenerator<Reference?> = NullDataGenerator(),
    val episodeOfCare: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val basedOn: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val participant: ListDataGenerator<EncounterParticipant> = ListDataGenerator(0, EncounterParticipantGenerator()),
    val appointment: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val period: DataGenerator<Period?> = NullDataGenerator(),
    val length: DataGenerator<Duration?> = NullDataGenerator(),
    val reasonCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val reasonReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val diagnosis: ListDataGenerator<EncounterDiagnosis> = ListDataGenerator(0, EncounterDiagnosisGenerator()),
    val account: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val hospitalization: DataGenerator<EncounterHospitalization?> = NullDataGenerator(),
    val location: ListDataGenerator<EncounterLocation> = ListDataGenerator(0, EncounterLocationGenerator()),
    val serviceProvider: DataGenerator<Reference?> = NullDataGenerator(),
    val partOf: DataGenerator<Reference?> = NullDataGenerator()
) : DomainResource<Encounter> {

    override fun toFhir(): Encounter =
        Encounter(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            identifier = identifier.generate(),
            status = status.generate(),
            statusHistory = statusHistory.generate(),
            `class` = `class`.generate(),
            classHistory = classHistory.generate(),
            type = type.generate(),
            serviceType = serviceType.generate(),
            priority = priority.generate(),
            subject = subject.generate(),
            episodeOfCare = episodeOfCare.generate(),
            basedOn = basedOn.generate(),
            participant = participant.generate(),
            appointment = appointment.generate(),
            period = period.generate(),
            length = length.generate(),
            reasonCode = reasonCode.generate(),
            reasonReference = reasonReference.generate(),
            diagnosis = diagnosis.generate(),
            account = account.generate(),
            hospitalization = hospitalization.generate(),
            location = location.generate(),
            serviceProvider = serviceProvider.generate(),
            partOf = partOf.generate()
        )
}

class EncounterClassHistoryGenerator : DataGenerator<EncounterClassHistory>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val `class`: DataGenerator<Code?> = CodeGenerator(listOf("inpatient", "outpatient", "ambulatory", "emergency"))
    val period: DataGenerator<Period> = PeriodGenerator()

    override fun generateInternal(): EncounterClassHistory =
        EncounterClassHistory(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            `class` = `class`.generate(),
            period = period.generate()
        )
}

class EncounterDiagnosisGenerator : DataGenerator<EncounterDiagnosis>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val condition: DataGenerator<Reference> = ReferenceGenerator()
    val use: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val rank: DataGenerator<PositiveInt?> = NullDataGenerator()

    override fun generateInternal(): EncounterDiagnosis =
        EncounterDiagnosis(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            condition = condition.generate(),
            use = use.generate(),
            rank = rank.generate()
        )
}

class EncounterHospitalizationGenerator : DataGenerator<EncounterHospitalization>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val preAdmissionIdentifier: DataGenerator<Identifier?> = NullDataGenerator()
    val origin: DataGenerator<Reference?> = NullDataGenerator()
    val admitSource: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val reAdmission: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val dietPreference: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val specialCourtesy: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val specialArrangement: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val destination: DataGenerator<Reference?> = NullDataGenerator()
    val dischargeDisposition: DataGenerator<CodeableConcept?> = NullDataGenerator()

    override fun generateInternal(): EncounterHospitalization =
        EncounterHospitalization(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            preAdmissionIdentifier = preAdmissionIdentifier.generate(),
            origin = origin.generate(),
            admitSource = admitSource.generate(),
            reAdmission = reAdmission.generate(),
            dietPreference = dietPreference.generate(),
            specialCourtesy = specialCourtesy.generate(),
            specialArrangement = specialArrangement.generate(),
            destination = destination.generate(),
            dischargeDisposition = dischargeDisposition.generate()
        )
}

class EncounterLocationGenerator : DataGenerator<EncounterLocation>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val location: DataGenerator<Reference> = ReferenceGenerator()
    val status: DataGenerator<Code?> = NullDataGenerator()
    val physicalType: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val period: DataGenerator<Period?> = NullDataGenerator()

    override fun generateInternal(): EncounterLocation =
        EncounterLocation(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            location = location.generate(),
            status = status.generate(),
            physicalType = physicalType.generate(),
            period = period.generate()
        )
}

class EncounterStatusHistoryGenerator : DataGenerator<EncounterStatusHistory>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val status: DataGenerator<Code?> = CodeGenerator(EncounterStatus::class)
    val period: DataGenerator<Period> = PeriodGenerator()

    override fun generateInternal(): EncounterStatusHistory =
        EncounterStatusHistory(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            status = status.generate(),
            period = period.generate()
        )
}

fun encounter(block: EncounterGenerator.() -> Unit): Encounter {
    val encounter = EncounterGenerator()
    encounter.apply(block)
    return encounter.toFhir()
}

fun encounterClassHistory(block: EncounterClassHistoryGenerator.() -> Unit): EncounterClassHistory {
    val classHistory = EncounterClassHistoryGenerator()
    classHistory.apply(block)
    return classHistory.generate()
}

fun encounterDiagnosis(block: EncounterDiagnosisGenerator.() -> Unit): EncounterDiagnosis {
    val diagnosis = EncounterDiagnosisGenerator()
    diagnosis.apply(block)
    return diagnosis.generate()
}

fun encounterHospitalization(block: EncounterHospitalizationGenerator.() -> Unit): EncounterHospitalization {
    val hospitalization = EncounterHospitalizationGenerator()
    hospitalization.apply(block)
    return hospitalization.generate()
}

fun encounterLocation(block: EncounterLocationGenerator.() -> Unit): EncounterLocation {
    val location = EncounterLocationGenerator()
    location.apply(block)
    return location.generate()
}

fun encounterStatusHistory(block: EncounterStatusHistoryGenerator.() -> Unit): EncounterStatusHistory {
    val statusHistory = EncounterStatusHistoryGenerator()
    statusHistory.apply(block)
    return statusHistory.generate()
}
