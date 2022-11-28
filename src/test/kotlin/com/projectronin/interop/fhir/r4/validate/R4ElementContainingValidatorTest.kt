package com.projectronin.interop.fhir.r4.validate

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.resource.Patient
import com.projectronin.interop.fhir.r4.validate.datatype.primitive.validatePrimitive
import com.projectronin.interop.fhir.r4.validate.datatype.validateDatatype
import com.projectronin.interop.fhir.r4.validate.datatype.validateDynamicValue
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.validation
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * These tests are focused on the core elements of the [R4ElementContainingValidator] and not the extending uses.
 */
class R4ElementContainingValidatorTest {
    private val failedValidation = validation {
        checkNotNull(null, RequiredFieldError(Patient::name), null)
    }
    private val parentContext = LocationContext("Sample", "field")
    private val nameLocationContext = LocationContext("Sample", "field.name")

    @BeforeEach
    fun setup() {
        mockkStatic("com.projectronin.interop.fhir.r4.validate.datatype.primitive.R4PrimitivesKt")
        mockkStatic("com.projectronin.interop.fhir.r4.validate.datatype.R4DatatypesKt")
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `no properties of validatable types`() {
        val element = NoValidatableTypesValidatable(name = "Josh")

        val instance = object : NoElementValidationValidator<NoValidatableTypesValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(0, validation.issues().size)
    }

    @Test
    fun `ignores null primitives`() {
        val element = PrimitiveValidatable(name = null)

        val instance = object : NoElementValidationValidator<PrimitiveValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(0, validation.issues().size)
    }

    @Test
    fun `validates non-null primitives`() {
        val name = Code("name")
        every { validatePrimitive(name, nameLocationContext) } returns failedValidation

        val element = PrimitiveValidatable(name = name)

        val instance = object : NoElementValidationValidator<PrimitiveValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `ignores null elements`() {
        val element = ElementValidatable(name = null)

        val instance = object : NoElementValidationValidator<ElementValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(0, validation.issues().size)
    }

    @Test
    fun `validates non-null elements`() {
        val name = HumanName(id = FHIRString("1234"))
        every { validateDatatype(name, nameLocationContext) } returns failedValidation

        val element = ElementValidatable(name = name)

        val instance = object : NoElementValidationValidator<ElementValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `ignores null dynamic values`() {
        val element = DynamicValueValidatable(name = null)

        val instance = object : NoElementValidationValidator<DynamicValueValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(0, validation.issues().size)
    }

    @Test
    fun `validates non-null dynamic values`() {
        val name = DynamicValue(DynamicValueType.STRING, FHIRString("name"))
        every { validateDynamicValue(name, nameLocationContext) } returns failedValidation

        val element = DynamicValueValidatable(name = name)

        val instance = object : NoElementValidationValidator<DynamicValueValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(1, validation.issues().size)
    }

    @Test
    fun `ignores empty primitive collections`() {
        val element = PrimitiveCollectionValidatable(name = listOf())

        val instance = object : NoElementValidationValidator<PrimitiveCollectionValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(0, validation.issues().size)
    }

    @Test
    fun `validates each primitive in collection`() {
        val name1 = Code("name")
        val name2 = Code("name2")
        every { validatePrimitive(name1, LocationContext("Sample", "field.name[0]")) } returns failedValidation
        every { validatePrimitive(name2, LocationContext("Sample", "field.name[1]")) } returns failedValidation

        val element = PrimitiveCollectionValidatable(name = listOf(name1, name2))

        val instance = object : NoElementValidationValidator<PrimitiveCollectionValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(2, validation.issues().size)
    }

    @Test
    fun `ignores empty element collections`() {
        val element = ElementCollectionValidatable(name = listOf())

        val instance = object : NoElementValidationValidator<ElementCollectionValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(0, validation.issues().size)
    }

    @Test
    fun `validates each element in collection`() {
        val name1 = HumanName(id = FHIRString("1234"))
        val name2 = HumanName(id = FHIRString("5678"))
        every { validateDatatype(name1, LocationContext("Sample", "field.name[0]")) } returns failedValidation
        every { validateDatatype(name2, LocationContext("Sample", "field.name[1]")) } returns failedValidation

        val element = ElementCollectionValidatable(name = listOf(name1, name2))

        val instance = object : NoElementValidationValidator<ElementCollectionValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(2, validation.issues().size)
    }

    @Test
    fun `ignores empty dyanmic value collections`() {
        val element = DynamicValueCollectionValidatable(name = listOf())

        val instance = object : NoElementValidationValidator<DynamicValueCollectionValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(0, validation.issues().size)
    }

    @Test
    fun `validates each dynamic value in collection`() {
        val name1 = DynamicValue(DynamicValueType.STRING, FHIRString("name"))
        val name2 = DynamicValue(DynamicValueType.STRING, FHIRString("name2"))
        every { validateDynamicValue(name1, LocationContext("Sample", "field.name[0]")) } returns failedValidation
        every { validateDynamicValue(name2, LocationContext("Sample", "field.name[1]")) } returns failedValidation

        val element = DynamicValueCollectionValidatable(name = listOf(name1, name2))

        val instance = object : NoElementValidationValidator<DynamicValueCollectionValidatable>() {}
        val validation = instance.validate(element, parentContext)
        assertEquals(2, validation.issues().size)
    }

    data class NoValidatableTypesValidatable(val name: String) : Validatable<NoValidatableTypesValidatable>
    data class PrimitiveValidatable(val name: Code?) : Validatable<PrimitiveValidatable>
    data class ElementValidatable(val name: HumanName?) : Validatable<ElementValidatable>
    data class DynamicValueValidatable(val name: DynamicValue<*>?) : Validatable<DynamicValueValidatable>
    data class PrimitiveCollectionValidatable(val name: List<Code>) : Validatable<PrimitiveCollectionValidatable>
    data class ElementCollectionValidatable(val name: List<HumanName>) : Validatable<ElementCollectionValidatable>
    data class DynamicValueCollectionValidatable(val name: List<DynamicValue<*>>) :
        Validatable<DynamicValueCollectionValidatable>

    abstract class NoElementValidationValidator<T : Validatable<T>> : R4ElementContainingValidator<T>() {
        override fun validateElement(element: T, parentContext: LocationContext?, validation: Validation) {}
    }
}
