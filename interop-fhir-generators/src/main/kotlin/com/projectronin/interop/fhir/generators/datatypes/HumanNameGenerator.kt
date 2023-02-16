package com.projectronin.interop.fhir.generators.datatypes

import com.github.javafaker.Name
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator
import com.projectronin.test.data.generator.faker.NameGenerator

class HumanNameGenerator : DataGenerator<HumanName>() {

    val use: CodeGenerator = CodeGenerator()
    val text: NullDataGenerator<String> = NullDataGenerator()
    val family: DataGenerator<String> = NameGenerator(Name::lastName)
    val given: ListDataGenerator<String> = ListDataGenerator(1, NameGenerator(Name::firstName))
    val prefix: ListDataGenerator<String> = ListDataGenerator(0, NameGenerator(Name::prefix))
    val suffix: ListDataGenerator<String> = ListDataGenerator(0, NameGenerator(Name::suffix))
    val period: NullDataGenerator<Period> = NullDataGenerator()

    override fun generateInternal(): HumanName {
        return HumanName(
            use = use.generate(),
            text = text.generate()?.asFHIR(),
            family = family.generate().asFHIR(),
            given = given.generate().asFHIR(),
            prefix = prefix.generate().asFHIR(),
            suffix = suffix.generate().asFHIR(),
            period = period.generate()
        )
    }
}
fun name(block: HumanNameGenerator.() -> Unit): HumanName {
    val name = HumanNameGenerator()
    name.apply(block)
    return name.generate()
}
