package com.projectronin.interop.fhir.r4.datatype

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.QuantityComparator

/**
 * Defined variation of [Money] using a system and code to define the currency.
 *
 * See [FHIR Documentation](https://hl7.org/fhir/R4/datatypes.html#MoneyVariations)
 */
data class MoneyQuantity(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val value: Double? = null,
    override val comparator: QuantityComparator? = null,
    override val unit: String? = null,
    override val system: Uri? = null,
    override val code: Code? = null
) : BaseQuantity(id, extension, value, comparator, unit, system, code) {
    init {
        validate()
        require(code != null || value == null) {
            "There SHALL be a code if there is a value"
        }
        require(system == null || system == CodeSystem.CURRENCY.uri) {
            "If system is present, it SHALL be CURRENCY"
        }
    }
}
