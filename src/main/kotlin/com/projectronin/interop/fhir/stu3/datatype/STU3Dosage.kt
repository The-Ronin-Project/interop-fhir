package com.projectronin.interop.fhir.stu3.datatype

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.stu3.DosageDeserializer
import com.projectronin.interop.fhir.jackson.outbound.stu3.DosageSerializer
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.r4.datatype.DoseAndRate
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.Timing

/**
 * The Dosage structure defines general dosage instruction information typically represented in medication requests,
 * medication dispenses and medication statements.
 *
 * See FHIR Spec for [Dosage](https://hl7.org/fhir/STU3/dosage.html)
 */
@JsonDeserialize(using = DosageDeserializer::class)
@JsonSerialize(using = DosageSerializer::class)
@JsonTypeName("Dosage")
data class STU3Dosage(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    val sequence: Int? = null,
    val text: String? = null,
    val additionalInstruction: List<CodeableConcept> = listOf(),
    val patientInstruction: String? = null,
    val timing: Timing? = null,
    val asNeeded: DynamicValue<Any>? = null,
    val site: CodeableConcept? = null,
    val route: CodeableConcept? = null,
    val method: CodeableConcept? = null,
    val dose: DynamicValue<Any>? = null,
    val rate: DynamicValue<Any>? = null,
    val maxDosePerPeriod: Ratio? = null,
    val maxDosePerAdministration: SimpleQuantity? = null,
    val maxDosePerLifetime: SimpleQuantity? = null
) : STU3Element<STU3Dosage> {

    override fun transformToR4(): Dosage {
        return Dosage(
            id = id,
            extension = extension,
            sequence = sequence,
            text = text,
            additionalInstruction = additionalInstruction,
            patientInstruction = patientInstruction,
            timing = timing,
            asNeeded = asNeeded,
            site = site,
            route = route,
            method = method,
            doseAndRate = transformToR4DoseAndRate(dose, rate),
            maxDosePerPeriod = maxDosePerPeriod,
            maxDosePerAdministration = maxDosePerAdministration,
            maxDosePerLifetime = maxDosePerLifetime,
        )
    }

    private fun transformToR4DoseAndRate(
        dose: DynamicValue<Any>?,
        rate: DynamicValue<Any>?
    ): List<DoseAndRate> {
        return if ((dose != null) || (rate != null)) {
            listOf(
                DoseAndRate(
                    // R4 type - not populated
                    dose = dose,
                    rate = rate
                )
            )
        } else {
            emptyList()
        }
    }
}
