package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.RelatedArtifact
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Markdown
import com.projectronin.interop.fhir.r4.datatype.primitive.Url
import com.projectronin.interop.fhir.r4.valueset.RelatedArtifactType
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class RelatedArtifactGenerator(
    val id: DataGenerator<FHIRString?> = NullDataGenerator(),
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val type: DataGenerator<Code?> = CodeGenerator(RelatedArtifactType::class),
    val label: DataGenerator<FHIRString?> = NullDataGenerator(),
    val display: DataGenerator<FHIRString?> = NullDataGenerator(),
    val citation: DataGenerator<Markdown?> = NullDataGenerator(),
    val url: DataGenerator<Url?> = NullDataGenerator(),
    val document: DataGenerator<Attachment?> = NullDataGenerator(),
    val resource: DataGenerator<Canonical?> = NullDataGenerator()
) : DataGenerator<RelatedArtifact>() {
    override fun generateInternal() =
        RelatedArtifact(
            id = id.generate(),
            extension = extension.generate(),
            type = type.generate(),
            label = label.generate(),
            display = display.generate(),
            citation = citation.generate(),
            url = url.generate(),
            document = document.generate(),
            resource = resource.generate()
        )
}

fun relatedArtifact(block: RelatedArtifactGenerator.() -> Unit): RelatedArtifact {
    val relatedArtifact = RelatedArtifactGenerator()
    relatedArtifact.apply(block)
    return relatedArtifact.generate()
}
