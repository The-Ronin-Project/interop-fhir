package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Organization
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity
import com.projectronin.interop.fhir.validate.append

object R4OrganizationValidator : R4ElementContainingValidator<Organization>() {

    private val requiredNameOrIdentifierError = FHIRError(
        code = "R4_ORG_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "The organization SHALL at least have a name or an identifier, and possibly more than one",
        location = LocationContext(Organization::class)
    )

    private val orgAddressUseError = FHIRError(
        code = "R4_ORG_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "An address of an organization can never be of use 'home'",
        location = LocationContext(Organization::class)
    )

    private val orgTelecomUseError = FHIRError(
        code = "R4_ORG_003",
        severity = ValidationIssueSeverity.ERROR,
        description = "The telecom of an organization can never be of use 'home'",
        location = LocationContext(Organization::class)
    )

    override fun validateElement(element: Organization, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue(
                (element.identifier.isNotEmpty() || element.name != null),
                requiredNameOrIdentifierError,
                parentContext
            )

            element.address.forEachIndexed { index, address ->
                val currentContext = parentContext.append(LocationContext("Organization", "address[$index]"))
                checkTrue(address.use?.value != "home", orgAddressUseError, currentContext)
            }

            element.telecom.forEachIndexed { index, telecom ->
                val currentContext = parentContext.append(LocationContext("Organization", "telecom[$index]"))
                checkTrue(telecom.use?.value != "home", orgTelecomUseError, currentContext)
            }
        }
    }
}
