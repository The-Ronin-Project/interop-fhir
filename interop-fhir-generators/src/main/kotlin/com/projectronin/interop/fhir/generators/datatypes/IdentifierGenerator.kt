package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.StringGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator

class IdentifierGenerator : DataGenerator<Identifier>() {
    val use: NullDataGenerator<Code> = CodeGenerator()
    val type: DataGenerator<CodeableConcept> = CodeableConceptGenerator()
    val system: UriGenerator = UriGenerator()
    val value: DataGenerator<String> = StringGenerator(5)
    val period: NullDataGenerator<Period> = NullDataGenerator()
    val assigner: NullDataGenerator<Reference> = NullDataGenerator()

    override fun generateInternal(): Identifier =
        Identifier(
            use = use.generate(),
            type = type.generate(),
            system = system.generate(),
            value = value.generate().asFHIR(),
            period = period.generate(),
            assigner = assigner.generate()
        )
}

fun identifier(block: IdentifierGenerator.() -> Unit): Identifier {
    val identifier = IdentifierGenerator()
    identifier.apply(block)
    return identifier.generate()
}

fun externalIdentifier(block: IdentifierGenerator.() -> Unit): Identifier {
    val identifier = IdentifierGenerator()
    identifier.apply(block)
    identifier.type of codeableConcept {
        text of "External"
    }
    return identifier.generate()
}

fun internalIdentifier(block: IdentifierGenerator.() -> Unit): Identifier {
    val identifier = IdentifierGenerator()
    identifier.apply(block)
    identifier.type of codeableConcept {
        text of "Internal"
    }
    return identifier.generate()
}