package com.projectronin.interop.fhir.generators.datatypes

import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Url
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

class AttachmentGenerator : DataGenerator<Attachment>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val contentType: CodeGenerator = CodeGenerator()
    val language: CodeGenerator = CodeGenerator()
    val data: DataGenerator<Base64Binary?> = NullDataGenerator()
    val url: DataGenerator<Url?> = NullDataGenerator()
    val size: DataGenerator<UnsignedInt?> = NullDataGenerator()
    val hash: DataGenerator<Base64Binary?> = NullDataGenerator()
    val title: DataGenerator<FHIRString?> = NullDataGenerator()
    val creation: DataGenerator<DateTime?> = NullDataGenerator()

    override fun generateInternal() = Attachment(
        id = id.generate(),
        extension = extension.generate(),
        contentType = contentType.generate(),
        language = language.generate(),
        data = data.generate(),
        url = url.generate(),
        size = size.generate(),
        hash = hash.generate(),
        title = title.generate(),
        creation = creation.generate()
    )
}

fun attachment(block: AttachmentGenerator.() -> Unit): Attachment {
    val attachment = AttachmentGenerator()
    attachment.apply(block)
    return attachment.generate()
}
