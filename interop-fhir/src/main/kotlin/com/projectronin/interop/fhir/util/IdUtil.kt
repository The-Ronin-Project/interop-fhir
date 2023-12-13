package com.projectronin.interop.fhir.util

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.CodeableConcepts
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id

/**
 * Returns an [Identifier] representation of this Id.
 */
fun Id?.toFhirIdentifier(): Identifier? =
    this?.let {
        Identifier(
            value = value?.let { FHIRString(it) },
            system = CodeSystem.RONIN_FHIR_ID.uri,
            type = CodeableConcepts.RONIN_FHIR_ID,
        )
    }

/**
 * Localizes the Id relative to the [tenantMnemonic]
 */
fun Id.localize(tenantMnemonic: String): Id = Id(value?.localizeFhirId(tenantMnemonic), id, extension)

/**
 * Localizes the String relative to the [tenantMnemonic]
 */
fun String.localizeFhirId(tenantMnemonic: String): String {
    val prefix = "$tenantMnemonic-"
    return if (this.startsWith(prefix)) this else "$prefix$this"
}

/**
 * Unlocalizes the String relative to the [tenantMnemonic]
 */
fun String.unlocalizeFhirId(tenantMnemonic: String): String = this.removePrefix("$tenantMnemonic-")

/**
 * Localizes a Reference.
 */
fun Reference.localize(tenantMnemonic: String): Reference {
    reference?.value?.let {
        val matchResult = Reference.FHIR_RESOURCE_REGEX.matchEntire(it) ?: return this

        val (_, _, _, type, fhirId, history) = matchResult.destructured
        return copy(
            reference =
                FHIRString(
                    "$type/${fhirId.localizeFhirId(tenantMnemonic)}$history",
                    reference.id,
                    reference.extension,
                ),
        )
    } ?: return this
}
