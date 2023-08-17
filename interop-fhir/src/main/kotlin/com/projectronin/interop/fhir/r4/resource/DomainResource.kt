package com.projectronin.interop.fhir.r4.resource

import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Narrative

/**
 * A domain resource is a resource that:
 * - has a human-readable XHTML representation of the content of the resource (see Human Narrative in resources)
 * - can contain additional related resources inside the resource (see Contained Resources)
 * - can have additional extensions and modifierExtensions as well as the defined data (See Extensibility)
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/domainresource.html)
 */
interface DomainResource<T : DomainResource<T>> : Resource<T> {
    val text: Narrative?
    val contained: List<Resource<*>>
    val extension: List<Extension>
    val modifierExtension: List<Extension>
}
