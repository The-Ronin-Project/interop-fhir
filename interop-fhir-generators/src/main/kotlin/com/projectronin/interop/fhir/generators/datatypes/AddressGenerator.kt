package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.FHIRStringFakerDataGenerator
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.valueset.AddressType
import com.projectronin.interop.fhir.r4.valueset.AddressUse
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator
import com.projectronin.test.data.generator.faker.FakerDataGenerator
import kotlin.reflect.KFunction1
import net.datafaker.providers.base.Address as FakerAddress

class AddressGenerator : DataGenerator<Address>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val use: CodeGenerator = CodeGenerator(AddressUse::class)
    val type: CodeGenerator = CodeGenerator(AddressType::class)
    val text: DataGenerator<FHIRString?> = NullDataGenerator()
    val line: DataGenerator<List<FHIRString>> = AddressLineGenerator()
    val city: FHIRStringFakerDataGenerator = AddressPartGenerator(FakerAddress::city)
    val district: DataGenerator<FHIRString?> = NullDataGenerator()
    val state: FHIRStringFakerDataGenerator = AddressPartGenerator(FakerAddress::state)
    val postalCode: FHIRStringFakerDataGenerator = AddressPartGenerator(FakerAddress::postcode)
    val country: FHIRStringFakerDataGenerator = AddressPartGenerator(FakerAddress::country)
    val period: DataGenerator<Period?> = NullDataGenerator()

    override fun generateInternal() =
        Address(
            id = id.generate(),
            extension = extension.generate(),
            use = use.generate(),
            type = type.generate(),
            text = text.generate(),
            line = line.generate(),
            city = city.generate(),
            district = district.generate(),
            state = state.generate(),
            postalCode = postalCode.generate(),
            country = country.generate(),
            period = period.generate(),
        )
}

class AddressLineGenerator : FakerDataGenerator<List<FHIRString>>() {
    private val address = faker.address()

    override fun generateInternal(): List<FHIRString> {
        return when (randomInt(0, 1)) {
            0 -> listOf(address.streetAddress())
            else -> listOf(address.streetAddress(), address.secondaryAddress())
        }.map { FHIRString(it) }
    }
}

class AddressPartGenerator(private val function: KFunction1<FakerAddress, String>) : FHIRStringFakerDataGenerator() {
    private val address = faker.address()

    override fun generateString(): String = function.call(address)
}

fun address(block: AddressGenerator.() -> Unit): Address {
    val address = AddressGenerator()
    address.apply(block)
    return address.generate()
}
