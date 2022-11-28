package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CareTeamParticipant
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

@JsonTypeName("CareTeam")
data class CareTeam(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val status: Code? = null,
    val category: List<CodeableConcept> = listOf(),
    val name: String? = null,
    val subject: Reference? = null,
    val encounter: Reference? = null,
    val period: Period? = null,
    val participant: List<CareTeamParticipant> = emptyList(),
    val reasonCode: List<CodeableConcept> = emptyList(),
    val reasonReference: List<Reference> = emptyList(),
    val managingOrganization: List<Reference> = emptyList(),
    val telecom: List<ContactPoint> = emptyList(),
    val note: List<Annotation> = emptyList()
) : DomainResource<CareTeam> {
    override val resourceType: String = "CareTeam"
}
