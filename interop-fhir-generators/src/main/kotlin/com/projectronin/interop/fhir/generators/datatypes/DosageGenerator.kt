package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DoseAndRate
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.Timing
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRInteger
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class DosageGenerator : DataGenerator<Dosage>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val sequence: DataGenerator<FHIRInteger?> = NullDataGenerator()
    val text: DataGenerator<FHIRString?> = NullDataGenerator()
    val additionalInstruction: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
    val patientInstruction: DataGenerator<FHIRString?> = NullDataGenerator()
    val timing: DataGenerator<Timing?> = NullDataGenerator()
    val asNeeded: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()
    val site: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val route: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val method: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val doseAndRate: ListDataGenerator<DoseAndRate> = ListDataGenerator(0, DoseAndRateGenerator())
    val maxDosePerPeriod: DataGenerator<Ratio?> = NullDataGenerator()
    val maxDosePerAdministration: DataGenerator<SimpleQuantity?> = NullDataGenerator()
    val maxDosePerLifetime: DataGenerator<SimpleQuantity?> = NullDataGenerator()

    override fun generateInternal(): Dosage =
        Dosage(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            sequence = sequence.generate(),
            text = text.generate(),
            additionalInstruction = additionalInstruction.generate(),
            patientInstruction = patientInstruction.generate(),
            timing = timing.generate(),
            asNeeded = asNeeded.generate(),
            site = site.generate(),
            route = route.generate(),
            method = method.generate(),
            doseAndRate = doseAndRate.generate(),
            maxDosePerPeriod = maxDosePerPeriod.generate(),
            maxDosePerAdministration = maxDosePerAdministration.generate(),
            maxDosePerLifetime = maxDosePerLifetime.generate()
        )
}

class DoseAndRateGenerator : DataGenerator<DoseAndRate>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val type: DataGenerator<CodeableConcept?> = NullDataGenerator()
    val dose: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()
    val rate: DataGenerator<DynamicValue<Any>?> = NullDataGenerator()

    override fun generateInternal(): DoseAndRate =
        DoseAndRate(
            id = id.generate(),
            extension = extension.generate(),
            type = type.generate(),
            dose = dose.generate(),
            rate = rate.generate()
        )
}

fun dosage(block: DosageGenerator.() -> Unit): Dosage {
    val dosage = DosageGenerator()
    dosage.apply(block)
    return dosage.generate()
}

fun doseAndRate(block: DoseAndRateGenerator.() -> Unit): DoseAndRate {
    val doseAndRate = DoseAndRateGenerator()
    doseAndRate.apply(block)
    return doseAndRate.generate()
}
