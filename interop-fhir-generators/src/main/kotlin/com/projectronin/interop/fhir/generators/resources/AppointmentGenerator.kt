package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ParticipantGenerator
import com.projectronin.interop.fhir.generators.datatypes.PeriodGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.UnsignedIntGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Appointment
import com.projectronin.interop.fhir.r4.resource.Participant
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.valueset.AppointmentStatus
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class AppointmentGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator(AppointmentStatus::class),
    val cancelationReason: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val serviceCategory: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val serviceType: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val specialty: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val appointmentType: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val reasonCode: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val reasonReference: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val priority: UnsignedIntGenerator = UnsignedIntGenerator(),
    val description: DataGenerator<FHIRString?> = NullDataGenerator(),
    val supportingInformation: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val start: DataGenerator<Instant?> = NullDataGenerator(),
    val end: DataGenerator<Instant?> = NullDataGenerator(),
    val minutesDuration: DataGenerator<PositiveInt?> = NullDataGenerator(),
    val slot: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val created: DataGenerator<DateTime?> = NullDataGenerator(),
    val comment: DataGenerator<FHIRString?> = NullDataGenerator(),
    val patientInstruction: DataGenerator<FHIRString?> = NullDataGenerator(),
    val basedOn: ListDataGenerator<Reference> = ListDataGenerator(0, ReferenceGenerator()),
    val participant: ListDataGenerator<Participant> = ListDataGenerator(1, ParticipantGenerator()),
    val requestedPeriod: ListDataGenerator<Period> = ListDataGenerator(0, PeriodGenerator()),
) : DomainResource<Appointment> {
    override fun toFhir(): Appointment =
        Appointment(
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
            cancelationReason = cancelationReason.generate(),
            serviceCategory = serviceCategory.generate(),
            serviceType = serviceType.generate(),
            specialty = specialty.generate(),
            appointmentType = appointmentType.generate(),
            reasonCode = reasonCode.generate(),
            reasonReference = reasonReference.generate(),
            priority = priority.generate(),
            description = description.generate(),
            supportingInformation = supportingInformation.generate(),
            start = start.generate(),
            end = end.generate(),
            minutesDuration = minutesDuration.generate(),
            slot = slot.generate(),
            created = created.generate(),
            comment = comment.generate(),
            patientInstruction = patientInstruction.generate(),
            basedOn = basedOn.generate(),
            participant = participant.generate(),
            requestedPeriod = requestedPeriod.generate(),
        )
}

fun appointment(block: AppointmentGenerator.() -> Unit): Appointment {
    val appointment = AppointmentGenerator()
    appointment.apply(block)
    return appointment.toFhir()
}
