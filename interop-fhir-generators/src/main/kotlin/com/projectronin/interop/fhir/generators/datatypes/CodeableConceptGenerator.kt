package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.generators.primitives.UriGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator
import kotlin.reflect.KClass

class CodeableConceptGenerator(
    private val possibleValues: List<String> = listOf(),
    private val defaultSystem: String? = null
) : DataGenerator<CodeableConcept>() {
    // Constructor to accept a enum class
    constructor(enumClass: KClass<out CodedEnum<*>>) : this(enumClass.java.enumConstants.map { it.code })

    val coding: ListDataGenerator<Coding> = ListDataGenerator(0, CodingGenerator())
    val text: FHIRStringDataGenerator = FHIRStringDataGenerator()

    override fun generateInternal(): CodeableConcept =
        if (possibleValues.isEmpty()) {
            // By default, generate a codeable concept
            CodeableConcept(
                coding = coding.generate(),
                text = text.generate()
            )
        } else {
            // When required values are provided build a codeable concept based on that.
            val selectedCode = CodeGenerator(possibleValues).generate()
            CodeableConcept(
                coding = listOf(
                    coding {
                        system of if (defaultSystem == null) {
                            UriGenerator().generate()
                        } else {
                            Uri(defaultSystem)
                        }
                        code of selectedCode
                        text of FHIRString(selectedCode!!.value)
                    }
                ),
                text = text.generate()
            )
        }
}

fun codeableConcept(block: CodeableConceptGenerator.() -> Unit): CodeableConcept {
    val codeableConcept = CodeableConceptGenerator()
    codeableConcept.apply(block)
    return codeableConcept.generate()
}
