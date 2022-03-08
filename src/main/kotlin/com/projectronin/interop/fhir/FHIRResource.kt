package com.projectronin.interop.fhir

import com.projectronin.interop.fhir.r4.datatype.primitive.Id

/**
 * This base interface for a FHIR resource supports use of the Ronin FHIR profile
 * for some resources and more canonical profiles, such as R4 FHIR, for others.
 * Two examples of interfaces that inherit from [FHIRResource]
 * [com.projectronin.interop.fhir.r4.resource.Resource],
 * [com.projectronin.interop.fhir.r4.ronin.resource.RoninResource]
 */
interface FHIRResource {
    val resourceType: String
    val id: Id?
}
