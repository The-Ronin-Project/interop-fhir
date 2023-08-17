package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.DynamicValues
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.resource.Batch
import com.projectronin.interop.fhir.r4.resource.Ingredient
import com.projectronin.interop.fhir.r4.resource.Medication
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class MedicationGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val code: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val status: DataGenerator<Code?> = NullDataGenerator(),
    val manufacturer: DataGenerator<Reference?> = NullDataGenerator(),
    val form: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val amount: DataGenerator<Ratio?> = NullDataGenerator(),
    val ingredient: ListDataGenerator<Ingredient> = ListDataGenerator(0, IngredientGenerator()),
    val batch: DataGenerator<Batch?> = NullDataGenerator()
) : DomainResource<Medication> {
    override fun toFhir(): Medication =
        Medication(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            identifier = identifier.generate(),
            status = status.generate(),
            code = code.generate(),
            manufacturer = manufacturer.generate(),
            form = form.generate(),
            amount = amount.generate(),
            ingredient = ingredient.generate(),
            batch = batch.generate()
        )
}

class IngredientGenerator : DataGenerator<Ingredient>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val item: DataGenerator<DynamicValue<Any>> = IngredientItemGenerator()
    val isActive: DataGenerator<FHIRBoolean?> = NullDataGenerator()
    val strength: DataGenerator<Ratio?> = NullDataGenerator()

    override fun generateInternal() = Ingredient(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        item = item.generate(),
        isActive = isActive.generate(),
        strength = strength.generate()
    )
}

class IngredientItemGenerator : DataGenerator<DynamicValue<Any>>() {
    override fun generateInternal(): DynamicValue<Any> {
        return DynamicValues.reference(ReferenceGenerator().generate())
    }
}

class BatchGenerator : DataGenerator<Batch>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val lotNumber: DataGenerator<FHIRString?> = NullDataGenerator()
    val expirationDate: DataGenerator<DateTime?> = NullDataGenerator()

    override fun generateInternal() = Batch(
        id = id.generate(),
        extension = extension.generate(),
        modifierExtension = modifierExtension.generate(),
        lotNumber = lotNumber.generate(),
        expirationDate = expirationDate.generate()
    )
}

fun medication(block: MedicationGenerator.() -> Unit): Medication {
    val medication = MedicationGenerator()
    medication.apply(block)
    return medication.toFhir()
}

fun ingredient(block: IngredientGenerator.() -> Unit): Ingredient {
    val ingredient = IngredientGenerator()
    ingredient.apply(block)
    return ingredient.generate()
}

fun batch(block: BatchGenerator.() -> Unit): Batch {
    val batch = BatchGenerator()
    batch.apply(block)
    return batch.generate()
}
