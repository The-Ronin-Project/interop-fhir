package com.projectronin.interop.fhir.stu3.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.MedicationStatementStatus
import com.projectronin.interop.fhir.stu3.element.STU3Dosage
import com.projectronin.interop.fhir.util.asCode
import com.projectronin.interop.fhir.r4.resource.MedicationStatement as R4MedicationStatement

/**
 * A record of a medication that is being consumed by a patient. May indicate that the patient may be taking the
 * medication now, or has taken the medication in the past or will be taking the medication in the future.
 *
 * See FHIR Spec for [MedicationStatement](http://hl7.org/fhir/STU3/medicationstatement.html)
 */
@JsonSerialize(using = STU3MedicationStatementSerializer::class)
@JsonDeserialize(using = STU3MedicationStatementDeserializer::class)
@JsonTypeName("MedicationStatement")
data class STU3MedicationStatement(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<STU3Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val basedOn: List<Reference> = listOf(),
    val partOf: List<Reference> = listOf(),
    val status: Code?,
    val statusReason: List<CodeableConcept> = listOf(),
    val category: CodeableConcept? = null,
    val medication: DynamicValue<Any>?,
    val subject: Reference?,
    val context: Reference? = null,
    val effective: DynamicValue<Any>? = null,
    val dateAsserted: DateTime? = null,
    val informationSource: Reference? = null,
    val derivedFrom: List<Reference> = listOf(),
    val taken: String? = null,
    val reasonNotTaken: List<CodeableConcept> = listOf(),
    val reasonCode: List<CodeableConcept> = listOf(),
    val reasonReference: List<Reference> = listOf(),
    val note: List<Annotation> = listOf(),
    val dosage: List<STU3Dosage> = listOf()
) : STU3DomainResource<STU3MedicationStatement> {
    override val resourceType: String = "MedicationStatement"

    override fun transformToR4(): R4MedicationStatement {
        return R4MedicationStatement(
            identifier = identifier,
            id = id,
            meta = meta,
            implicitRules = implicitRules,
            language = language,
            text = text,
            contained = contained.map { it.transformToR4() },
            extension = extension,
            modifierExtension = modifierExtension,
            basedOn = basedOn,
            partOf = partOf,
            status = transformToR4Status(status, taken),
            statusReason = reasonNotTaken,
            category = category,
            medication = medication,
            subject = subject,
            context = context,
            effective = effective,
            dateAsserted = dateAsserted,
            informationSource = informationSource,
            derivedFrom = derivedFrom,
            reasonCode = reasonCode,
            reasonReference = reasonReference,
            note = note,
            dosage = dosage.map { it.transformToR4() }
        )
    }

    private fun transformToR4Status(status: Code?, taken: String?): Code? {
        return when (taken) {
            "n" -> MedicationStatementStatus.NOT_TAKEN.asCode()
            "unk" -> MedicationStatementStatus.UNKNOWN.asCode()
            else -> status
        }
    }
}

class STU3MedicationStatementSerializer :
    BaseFHIRSerializer<STU3MedicationStatement>(STU3MedicationStatement::class.java)

class STU3MedicationStatementDeserializer :
    BaseFHIRDeserializer<STU3MedicationStatement>(STU3MedicationStatement::class.java)
