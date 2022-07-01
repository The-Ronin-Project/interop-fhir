package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A Reference to another resource.
 *
 * See [FHIR Documentation](http://hl7.org/fhir/R4/references.html#Reference)
 */
data class Reference(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val reference: String? = null,
    val type: Uri? = null,
    val identifier: Identifier? = null,
    val display: String? = null
) : Element {
    init {
        require(reference != null || identifier != null || display != null || extension.isNotEmpty()) {
            "At least one of reference, identifier and display SHALL be present (unless an extension is provided)"
        }
    }
}
