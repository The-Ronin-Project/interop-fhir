package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.valueset.ContactPointSystem
import com.projectronin.interop.fhir.r4.valueset.ContactPointUse
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator
import net.datafaker.Faker

class ContactPointGenerator : DataGenerator<ContactPoint>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val system: CodeGenerator = CodeGenerator(ContactPointSystem::class)
    val value: DataGenerator<FHIRString?> = NullDataGenerator()
    val use: CodeGenerator = CodeGenerator(ContactPointUse::class)
    val rank: DataGenerator<PositiveInt?> = NullDataGenerator()
    val period: DataGenerator<Period?> = NullDataGenerator()

    override fun generateInternal(): ContactPoint {
        val system = system.generate()
        val value = value.generate() ?: when (system!!.value) {
            ContactPointSystem.PHONE.code,
            ContactPointSystem.FAX.code,
            ContactPointSystem.SMS.code,
            ContactPointSystem.PAGER.code -> Faker().phoneNumber().phoneNumber()

            ContactPointSystem.EMAIL.code -> Faker().internet().emailAddress()
            ContactPointSystem.URL.code -> Faker().internet().url()
            else -> Faker().lorem().word()
        }?.let { FHIRString(it) }

        return ContactPoint(
            id = id.generate(),
            extension = extension.generate(),
            system = system,
            value = value,
            use = use.generate(),
            rank = rank.generate(),
            period = period.generate()
        )
    }
}

fun contactPoint(block: ContactPointGenerator.() -> Unit): ContactPoint {
    val contactPoint = ContactPointGenerator()
    contactPoint.apply(block)
    return contactPoint.generate()
}
