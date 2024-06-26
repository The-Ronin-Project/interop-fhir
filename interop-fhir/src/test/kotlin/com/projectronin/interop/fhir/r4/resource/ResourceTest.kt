package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.projectronin.interop.common.jackson.JacksonManager
import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.interop.fhir.r4.element.BaseElementTest
import io.github.classgraph.ClassGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.jvmName

class ResourceTest : BaseElementTest() {
    @Test
    fun `all subtypes are defined`() {
        // This test is not really running anything (Resource is an interface, after all), but it is verifying a critical
        // piece of information for our system. This test ensures that all final subtypes of this interface are included
        // in the JsonSubTypes. This ensures that Bundles can be built properly.
        val jsonSubTypes =
            Resource::class.findAnnotation<JsonSubTypes>()?.value?.map { it.value.jvmName }
                ?: fail { "No JsonSubTypes found" }

        val defaultType =
            Resource::class.findAnnotation<JsonTypeInfo>()?.defaultImpl?.jvmName
                ?: fail { "No JsonTypeInfo or default found" }

        val knownSubTypes =
            ClassGraph().acceptPackages("com.projectronin.interop.fhir").enableClassInfo().scan().use {
                it.getClassesImplementing(Resource::class.java).standardClasses.map { c -> c.name }
            }

        val missingSubTypes = knownSubTypes - jsonSubTypes - defaultType
        if (missingSubTypes.isNotEmpty()) {
            fail {
                "JsonSubType not specified for following defined types: ${
                    missingSubTypes.joinToString(
                        "\n\t",
                        prefix = "\n\t",
                    )
                }"
            }
        }
    }

    @Test
    fun `all resources have serializers, deserializers and only FHIR properties`() {
        val allElements =
            ClassGraph().acceptPackages("com.projectronin.interop.fhir").enableClassInfo().scan().use {
                it.getClassesImplementing(Resource::class.java).filter { c -> c.isFinal }
                    .map { c -> c.loadClass().kotlin }
                    .filterNot { c -> c == UnknownResource::class }
            }
        verifyElements(allElements, "resourceType")
    }

    @Test
    fun `consistent hash uses serialized string`() {
        val patient = Patient(id = Id("1234"))
        assertEquals(JacksonManager.objectMapper.writeValueAsString(patient).hashCode(), patient.consistentHashCode())
        assertNotEquals(patient.hashCode(), patient.consistentHashCode())
    }

    @Test
    fun `find fhir id works`() {
        val patient1 = Patient(id = Id("1234"))
        val patient2 =
            Patient(
                id = Id("localized"),
                identifier =
                    listOf(
                        Identifier(system = CodeSystem.RONIN_FHIR_ID.uri, value = "unlocalized".asFHIR()),
                    ),
            )
        val patient3 = Patient(id = null)
        val patient4 =
            Patient(
                id = Id("localized"),
                identifier =
                    listOf(
                        Identifier(system = null, value = "unlocalized".asFHIR()),
                        Identifier(system = CodeSystem.RONIN_FHIR_ID.uri, value = null),
                    ),
            )
        assertEquals("1234", patient1.findFhirId())
        assertEquals("unlocalized", patient2.findFhirId())
        assertNull(patient3.findFhirId())
        assertNull(patient4.findFhirId())
        val ignoredResources =
            listOf(
                UnknownResource::class,
                Binary::class,
                Bundle::class,
                ConceptMap::class,
            )
        val allElements =
            ClassGraph().acceptPackages("com.projectronin.interop.fhir").enableClassInfo().scan().use {
                it.getClassesImplementing(Resource::class.java).filter { c -> c.isFinal }
                    .map { c -> c.loadClass().kotlin }
                    .filterNot { c -> c in ignoredResources }
            }
        allElements.forEach {
            val field = it.java.getDeclaredField("identifier")
            assertNotNull(field)
            assertEquals(field.type, List::class.java)
        }
    }

    @Test
    fun `find fhir id fails for example non-domain resource`() {
        val bundle = Bundle(type = Code("example"))
        assertNull(bundle.findFhirId())
    }

    @Test
    fun `findTenantID works`() {
        val patient =
            Patient(
                id = Id("localized"),
                identifier =
                    listOf(
                        Identifier(system = CodeSystem.RONIN_TENANT.uri, value = "tenant".asFHIR()),
                    ),
            )
        val tenantID = patient.findTenantId()
        assertEquals("tenant", tenantID)
    }

    @Test
    fun `findTenantID fails null 1`() {
        val patient =
            Patient(
                id = Id("localized"),
                identifier =
                    listOf(
                        Identifier(system = CodeSystem.RONIN_TENANT.uri, value = null),
                    ),
            )
        val tenantID = patient.findTenantId()
        assertEquals(null, tenantID)
    }

    @Test
    fun `findTenantID fails null 2`() {
        val patient =
            Patient(
                id = Id("localized"),
                identifier = listOf(),
            )
        val tenantID = patient.findTenantId()
        assertEquals(null, tenantID)
    }
}
