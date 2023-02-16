package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.resource.Participant
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class ParticipantGenerator : DataGenerator<Participant>() {
    val type: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val actor: DataGenerator<Reference> = ReferenceGenerator()
    val required: CodeGenerator = CodeGenerator()
    val status: CodeGenerator = CodeGenerator()
    val period: NullDataGenerator<Period> = NullDataGenerator()

    override fun generateInternal(): Participant =
        Participant(
            type = type.generate(),
            actor = actor.generate(),
            required = required.generate(),
            status = status.generate(),
            period = period.generate()
        )
}

fun participant(block: ParticipantGenerator.() -> Unit): Participant {
    val participant = ParticipantGenerator()
    participant.apply(block)
    return participant.generate()
}
