package com.projectronin.interop.fhir.r4.element

import com.projectronin.interop.fhir.r4.datatype.primitive.Primitive
import io.github.classgraph.ClassGraph
import org.junit.jupiter.api.Test
import kotlin.reflect.full.isSubclassOf

class ElementTest : BaseElementTest() {
    @Test
    fun `all elements have serializers, deserializers and only FHIR properties`() {
        val allElements =
            ClassGraph().acceptPackages("com.projectronin.interop.fhir.r4.datatype").enableClassInfo().scan().use {
                it.getClassesImplementing(Element::class.java).filter { c -> c.isFinal }
                    .map { c -> c.loadClass().kotlin }
                    .filterNot { c -> c.isSubclassOf(Primitive::class) }
            }
        verifyElements(allElements)
    }
}
