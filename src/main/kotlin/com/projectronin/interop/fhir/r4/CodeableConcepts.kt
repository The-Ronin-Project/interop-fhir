package com.projectronin.interop.fhir.r4

import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR

/**
 * Access to common Ronin [CodeableConcept]s
 */
object CodeableConcepts {
    val RONIN_TENANT = CodeableConcept(
        text = "Ronin-specified Tenant Identifier".asFHIR(),
        coding = listOf(
            Coding(
                system = CodeSystem.RONIN_TENANT.uri,
                code = Code("TID"),
                display = "Ronin-specified Tenant Identifier".asFHIR()
            )
        )
    )

    val RONIN_MRN = CodeableConcept(
        text = "Medical Record Number".asFHIR(),
        coding = listOf(
            Coding(
                system = CodeSystem.HL7_IDENTIFIER_TYPE.uri,
                code = Code("MRN"),
                display = "Medical Record Number".asFHIR()
            )
        )
    )

    val RONIN_FHIR_ID = CodeableConcept(
        text = "FHIR Identifier".asFHIR(),
        coding = listOf(
            Coding(
                system = CodeSystem.RONIN_FHIR_ID.uri,
                code = Code("FHIR ID"),
                display = "FHIR Identifier".asFHIR()
            )
        )
    )
}
