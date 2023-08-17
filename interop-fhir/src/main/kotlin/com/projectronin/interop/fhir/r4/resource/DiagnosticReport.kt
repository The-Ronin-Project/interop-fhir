package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

/**
 * The findings and interpretation of diagnostic tests performed on patients, groups of patients, devices, and locations,
 * and/or specimens derived from these. The report includes clinical context such as requesting and provider information,
 * and some mix of atomic results, images, textual and coded interpretations, and formatted representation of diagnostic
 * reports.
 *
 * See [FHIR Spec](https://hl7.org/fhir/r4/diagnosticreport.html)
 */
@JsonSerialize(using = DiagnosticReportSerializer::class)
@JsonDeserialize(using = DiagnosticReportDeserializer::class)
@JsonTypeName("DiagnosticReport")
data class DiagnosticReport(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val basedOn: List<Reference> = listOf(),
    val status: Code?,
    val category: List<CodeableConcept> = listOf(),
    val code: CodeableConcept?,
    val subject: Reference? = null,
    val encounter: Reference? = null,
    val effective: DynamicValue<Any>? = null,
    val issued: Instant? = null,
    val performer: List<Reference> = listOf(),
    val resultsInterpreter: List<Reference> = listOf(),
    val specimen: List<Reference> = listOf(),
    val result: List<Reference> = listOf(),
    val imagingStudy: List<Reference> = listOf(),
    val media: List<DiagnosticReportMedia> = listOf(),
    val conclusion: FHIRString? = null,
    val conclusionCode: List<CodeableConcept> = listOf(),
    val presentedForm: List<Attachment> = listOf()
) : DomainResource<DiagnosticReport> {
    override val resourceType: String = "DiagnosticReport"
}

class DiagnosticReportSerializer : BaseFHIRSerializer<DiagnosticReport>(DiagnosticReport::class.java)
class DiagnosticReportDeserializer : BaseFHIRDeserializer<DiagnosticReport>(DiagnosticReport::class.java)

/**
 * Key images associated with this report
 */
@JsonSerialize(using = DiagnosticReportMediaSerializer::class)
@JsonDeserialize(using = DiagnosticReportMediaDeserializer::class)
data class DiagnosticReportMedia(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val comment: FHIRString? = null,
    val link: Reference?
) : BackboneElement<DiagnosticReportMedia>

class DiagnosticReportMediaSerializer : BaseFHIRSerializer<DiagnosticReportMedia>(DiagnosticReportMedia::class.java)
class DiagnosticReportMediaDeserializer : BaseFHIRDeserializer<DiagnosticReportMedia>(DiagnosticReportMedia::class.java)
