package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.HumanNameGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.primitives.DateGenerator
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class PatientGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val name: ListDataGenerator<HumanName> = ListDataGenerator(1, HumanNameGenerator()),
    val gender: CodeGenerator = CodeGenerator(),
    val birthDate: DataGenerator<Date> = DateGenerator()
) : FhirTestResource<Patient> {
    override fun toFhir(): Patient =
        Patient(
            id = id.generate(),
            identifier = identifier.generate(),
            name = name.generate(),
            gender = gender.generate(),
            birthDate = birthDate.generate()
        )
}

fun patient(block: PatientGenerator.() -> Unit): Patient {
    val patient = PatientGenerator()
    patient.apply(block)
    return patient.toFhir()
}
