package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.FHIRStringDataGenerator
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class MetaGenerator : DataGenerator<Meta>() {
    val id: FHIRStringDataGenerator = FHIRStringDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val versionId: DataGenerator<Id?> = NullDataGenerator()
    val lastUpdated: DataGenerator<Instant?> = NullDataGenerator()
    val source: DataGenerator<Uri?> = NullDataGenerator()
    val profile: ListDataGenerator<Canonical?> = ListDataGenerator(0, NullDataGenerator())
    val security: ListDataGenerator<Coding> = ListDataGenerator(0, CodingGenerator())
    val tag: ListDataGenerator<Coding> = ListDataGenerator(0, CodingGenerator())

    override fun generateInternal() = Meta(
        id = id.generate(),
        extension = extension.generate(),
        versionId = versionId.generate(),
        lastUpdated = lastUpdated.generate(),
        source = source.generate(),
        profile = profile.generate().filterNotNull(),
        security = security.generate(),
        tag = tag.generate()
    )
}

fun meta(block: MetaGenerator.() -> Unit): Meta {
    val meta = MetaGenerator()
    meta.apply(block)
    return meta.generate()
}
