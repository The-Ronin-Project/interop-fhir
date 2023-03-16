package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodingGenerator
import com.projectronin.interop.fhir.generators.datatypes.EncounterParticipantGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.PeriodGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.Encounter
import com.projectronin.interop.fhir.r4.resource.EncounterParticipant
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class EncounterGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator(),
    val participant: ListDataGenerator<EncounterParticipant> = ListDataGenerator(0, EncounterParticipantGenerator()),
    val `class`: CodingGenerator = CodingGenerator(),
    val period: DataGenerator<Period> = PeriodGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val type: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
) : FhirTestResource<Encounter> {

    override fun toFhir(): Encounter =
        Encounter(
            id = id.generate(),
            identifier = identifier.generate(),
            status = status.generate(),
            participant = participant.generate(),
            `class` = `class`.generate(),
            period = period.generate(),
            subject = subject.generate(),
            type = type.generate()
        )
}

fun encounter(block: EncounterGenerator.() -> Unit): Encounter {
    val encounter = EncounterGenerator()
    encounter.apply(block)
    return encounter.toFhir()
}
