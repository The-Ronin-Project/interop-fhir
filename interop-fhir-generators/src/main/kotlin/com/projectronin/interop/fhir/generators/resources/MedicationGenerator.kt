package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.Medication
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class MedicationGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator(),
    val code: DataGenerator<CodeableConcept> = CodeableConceptGenerator()
) : FhirTestResource<Medication> {
    override fun toFhir(): Medication =
        Medication(
            id = id.generate(),
            identifier = identifier.generate(),
            status = status.generate(),
            code = code.generate()
        )
}

fun medication(block: MedicationGenerator.() -> Unit): Medication {
    val medication = MedicationGenerator()
    medication.apply(block)
    return medication.toFhir()
}
