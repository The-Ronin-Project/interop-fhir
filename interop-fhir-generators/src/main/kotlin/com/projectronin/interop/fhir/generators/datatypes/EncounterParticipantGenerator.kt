package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.resource.EncounterParticipant
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class EncounterParticipantGenerator : DataGenerator<EncounterParticipant>() {
    val type: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val period: NullDataGenerator<Period> = NullDataGenerator()
    val individual: DataGenerator<Reference> = ReferenceGenerator()

    override fun generateInternal(): EncounterParticipant =
        EncounterParticipant(
            type = type.generate(),
            period = period.generate(),
            individual = individual.generate()
        )
}

fun encounterParticipant(block: EncounterParticipantGenerator.() -> Unit): EncounterParticipant {
    val participant = EncounterParticipantGenerator()
    participant.apply(block)
    return participant.generate()
}
