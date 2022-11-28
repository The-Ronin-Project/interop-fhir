package com.projectronin.interop.fhir.stu3.datatype

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
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

/**
 * The Dosage structure defines general dosage instruction information typically represented in medication requests,
 * medication dispenses and medication statements.
 *
 * See FHIR Spec for [Dosage](https://hl7.org/fhir/STU3/dosage.html)
 */
@JsonSerialize(using = STU3DosageSerializer::class)
@JsonDeserialize(using = STU3DosageDeserializer::class)
@JsonTypeName("Dosage")
data class STU3Dosage(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val sequence: FHIRInteger? = null,
    val text: FHIRString? = null,
    val additionalInstruction: List<CodeableConcept> = listOf(),
    val patientInstruction: FHIRString? = null,
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

class STU3DosageSerializer : BaseFHIRSerializer<STU3Dosage>(STU3Dosage::class.java)
class STU3DosageDeserializer : BaseFHIRDeserializer<STU3Dosage>(STU3Dosage::class.java)
