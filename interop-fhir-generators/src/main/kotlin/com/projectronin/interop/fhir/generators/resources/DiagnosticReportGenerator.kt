package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeGenerator
import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.datatypes.ReferenceGenerator
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.DiagnosticReport
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator

data class DiagnosticReportGenerator(
    override val id: NullDataGenerator<Id> = NullDataGenerator(),
    override val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: CodeGenerator = CodeGenerator(),
    val code: DataGenerator<CodeableConcept> = CodeableConceptGenerator(),
    val subject: DataGenerator<Reference> = ReferenceGenerator(),
    val category: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator())
) : FhirTestResource<DiagnosticReport> {

    override fun toFhir(): DiagnosticReport =
        DiagnosticReport(
            id = id.generate(),
            identifier = identifier.generate(),
            status = status.generate(),
            code = code.generate(),
            subject = subject.generate(),
            category = category.generate()
        )
}

fun diagnosticReport(block: DiagnosticReportGenerator.() -> Unit): DiagnosticReport {
    val diagnosticReport = DiagnosticReportGenerator()
    diagnosticReport.apply(block)
    return diagnosticReport.toFhir()
}
