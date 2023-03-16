package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.interop.fhir.r4.resource.Location
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator
import net.datafaker.Faker

class LocationGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator())
) : FhirTestResource<Location> {
    val name: DataGenerator<String> = LocationNameGenerator()
    override fun toFhir(): Location =
        Location(
            id = id.generate(),
            identifier = identifier.generate(),
            name = name.generate().asFHIR()
        )

    private class LocationNameGenerator : DataGenerator<String>() {
        override fun generateInternal(): String = Faker().medical().hospitalName()
    }
}

fun location(block: LocationGenerator.() -> Unit): Location {
    val location = LocationGenerator()
    location.apply(block)
    return location.toFhir()
}
