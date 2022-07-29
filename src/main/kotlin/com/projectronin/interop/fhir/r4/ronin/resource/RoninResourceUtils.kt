package com.projectronin.interop.fhir.r4.ronin.resource

import com.projectronin.interop.fhir.r4.CodeSystem
import com.projectronin.interop.fhir.r4.CodeableConcepts
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validates that the supplied [identifier] list contains at least one valid tenant identifier.
 */
fun requireTenantIdentifier(validation: Validation, identifier: List<Identifier>) {
    val tenantIdentifier = identifier.find { it.system == CodeSystem.RONIN_TENANT.uri }
    validation.apply {
        notNull(tenantIdentifier) { "Tenant identifier is required" }

        ifNotNull(tenantIdentifier) {
            // tenantIdentifier.use is constrained by the IdentifierUse enum type, so it needs no validation.
            check(tenantIdentifier.type == CodeableConcepts.RONIN_TENANT) { "Tenant identifier provided without proper CodeableConcept defined" }
            notNull(tenantIdentifier.value) { "tenant value is required" }
        }
    }
}
