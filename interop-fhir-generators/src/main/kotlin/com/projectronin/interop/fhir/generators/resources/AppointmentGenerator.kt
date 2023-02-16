package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ParticipantGenerator
import com.projectronin.interop.fhir.generators.primitives.InstantGenerator
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.interop.fhir.r4.resource.Appointment
import com.projectronin.interop.fhir.r4.resource.Participant
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator
import com.projectronin.test.data.generator.faker.IntGenerator

data class AppointmentGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator(),
    val participant: ListDataGenerator<Participant> = ListDataGenerator(0, ParticipantGenerator()),
    val minutesDuration: DataGenerator<Int> = IntGenerator(10, 120),
    val start: DataGenerator<Instant> = InstantGenerator(),
    val end: DataGenerator<Instant> = InstantGenerator()
) : FhirTestResource<Appointment> {
    override fun toFhir(): Appointment =
        Appointment(
            id = id.generate(),
            identifier = identifier.generate(),
            status = status.generate(),
            participant = participant.generate(),
            minutesDuration = minutesDuration.generate().asFHIR(),
            start = start.generate(),
            end = end.generate()
        )
}

fun appointment(block: AppointmentGenerator.() -> Unit): Appointment {
    val appointment = AppointmentGenerator()
    appointment.apply(block)
    return appointment.toFhir()
}
