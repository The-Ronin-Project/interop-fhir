package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.module.kotlin.readValue
import com.projectronin.interop.common.jackson.JacksonManager.Companion.objectMapper
import com.projectronin.interop.fhir.r4.datatype.Attachment
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.DiagnosticReportStatus
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class DiagnosticReportTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val diagnosticReport = DiagnosticReport(
            id = Id("12345"),
            meta = Meta(
                profile = listOf(Canonical("RoninAppointment")),
            ),
            implicitRules = Uri("implicit-rules"),
            language = Code("en-US"),
            text = Narrative(
                status = com.projectronin.interop.fhir.r4.valueset.NarrativeStatus.GENERATED.asCode(),
                div = FHIRString("div")
            ),
            contained = listOf(ContainedResource("""{"resourceType":"Banana","field":"24680"}""")),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            identifier = listOf(Identifier(value = FHIRString("id"))),
            basedOn = listOf(Reference(display = FHIRString("based-on"))),
            status = DiagnosticReportStatus.FINAL.asCode(),
            category = listOf(CodeableConcept(text = FHIRString("category"))),
            code = CodeableConcept(text = FHIRString("code")),
            subject = Reference(display = FHIRString("subject")),
            encounter = Reference(display = FHIRString("encounter")),
            effective = DynamicValue(DynamicValueType.DATE_TIME, DateTime("2023")),
            issued = Instant("2023-01-25T13:28:17.239+02:00"),
            performer = listOf(Reference(display = FHIRString("performer"))),
            resultsInterpreter = listOf(Reference(display = FHIRString("results-interpreter"))),
            specimen = listOf(Reference(display = FHIRString("specimen"))),
            result = listOf(Reference(display = FHIRString("result"))),
            imagingStudy = listOf(Reference(display = FHIRString("imaging-study"))),
            media = listOf(DiagnosticReportMedia(link = Reference(display = FHIRString("link")))),
            conclusion = FHIRString("conclusion"),
            conclusionCode = listOf(CodeableConcept(text = FHIRString("conclusion-code"))),
            presentedForm = listOf(Attachment(title = FHIRString("presented-form")))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(diagnosticReport)

        val expectedJson = """
            {
              "resourceType" : "DiagnosticReport",
              "id" : "12345",
              "meta" : {
                "profile" : [ "RoninAppointment" ]
              },
              "implicitRules" : "implicit-rules",
              "language" : "en-US",
              "text" : {
                "status" : "generated",
                "div" : "div"
              },
              "contained" : [ {"resourceType":"Banana","field":"24680"} ],
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "identifier" : [ {
                "value" : "id"
              } ],
              "basedOn" : [ {
                "display" : "based-on"
              } ],
              "status" : "final",
              "category" : [ {
                "text" : "category"
              } ],
              "code" : {
                "text" : "code"
              },
              "subject" : {
                "display" : "subject"
              },
              "encounter" : {
                "display" : "encounter"
              },
              "effectiveDateTime" : "2023",
              "issued" : "2023-01-25T13:28:17.239+02:00",
              "performer" : [ {
                "display" : "performer"
              } ],
              "resultsInterpreter" : [ {
                "display" : "results-interpreter"
              } ],
              "specimen" : [ {
                "display" : "specimen"
              } ],
              "result" : [ {
                "display" : "result"
              } ],
              "imagingStudy" : [ {
                "display" : "imaging-study"
              } ],
              "media" : [ {
                "link" : {
                  "display" : "link"
                }
              } ],
              "conclusion" : "conclusion",
              "conclusionCode" : [ {
                "text" : "conclusion-code"
              } ],
              "presentedForm" : [ {
                "title" : "presented-form"
              } ]
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedDiagnosticReport = objectMapper.readValue<DiagnosticReport>(json)
        assertEquals(diagnosticReport, deserializedDiagnosticReport)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val diagnosticReport = DiagnosticReport(
            status = DiagnosticReportStatus.FINAL.asCode(),
            code = CodeableConcept(text = FHIRString("code"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(diagnosticReport)

        val expectedJson = """
            {
              "resourceType" : "DiagnosticReport",
              "status" : "final",
              "code" : {
                "text" : "code"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedDiagnosticReport = objectMapper.readValue<DiagnosticReport>(json)
        assertEquals(diagnosticReport, deserializedDiagnosticReport)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "resourceType" : "DiagnosticReport",
              "status" : "final",
              "code" : {
                "text" : "code"
              }
            }
        """.trimIndent()
        val diagnosticReport = objectMapper.readValue<DiagnosticReport>(json)

        assertNull(diagnosticReport.id)
        assertNull(diagnosticReport.meta)
        assertNull(diagnosticReport.implicitRules)
        assertNull(diagnosticReport.language)
        assertNull(diagnosticReport.text)
        assertEquals(listOf<ContainedResource>(), diagnosticReport.contained)
        assertEquals(listOf<Extension>(), diagnosticReport.extension)
        assertEquals(listOf<Extension>(), diagnosticReport.modifierExtension)
        assertEquals(listOf<Identifier>(), diagnosticReport.identifier)
        assertEquals(listOf<Reference>(), diagnosticReport.basedOn)
        assertEquals(Code("final"), diagnosticReport.status)
        assertEquals(listOf<CodeableConcept>(), diagnosticReport.category)
        assertEquals(CodeableConcept(text = FHIRString("code")), diagnosticReport.code)
        assertNull(diagnosticReport.subject)
        assertNull(diagnosticReport.encounter)
        assertNull(diagnosticReport.effective)
        assertNull(diagnosticReport.issued)
        assertEquals(listOf<Reference>(), diagnosticReport.performer)
        assertEquals(listOf<Reference>(), diagnosticReport.resultsInterpreter)
        assertEquals(listOf<Reference>(), diagnosticReport.specimen)
        assertEquals(listOf<Reference>(), diagnosticReport.result)
        assertEquals(listOf<Reference>(), diagnosticReport.imagingStudy)
        assertEquals(listOf<DiagnosticReportMedia>(), diagnosticReport.media)
        assertNull(diagnosticReport.conclusion)
        assertEquals(listOf<CodeableConcept>(), diagnosticReport.conclusionCode)
        assertEquals(listOf<Attachment>(), diagnosticReport.presentedForm)
    }
}

class DiagnosticReportMediaTest {
    @Test
    fun `can serialize and deserialize JSON`() {
        val media = DiagnosticReportMedia(
            id = FHIRString("67890"),
            extension = listOf(
                Extension(
                    url = Uri("http://localhost/extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            modifierExtension = listOf(
                Extension(
                    url = Uri("http://localhost/modifier-extension"),
                    value = DynamicValue(DynamicValueType.STRING, FHIRString("Value"))
                )
            ),
            comment = FHIRString("comment"),
            link = Reference(display = FHIRString("link"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(media)

        val expectedJson = """
            {
              "id" : "67890",
              "extension" : [ {
                "url" : "http://localhost/extension",
                "valueString" : "Value"
              } ],
              "modifierExtension" : [ {
                "url" : "http://localhost/modifier-extension",
                "valueString" : "Value"
              } ],
              "comment" : "comment",
              "link" : {
                "display" : "link"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedMedia = objectMapper.readValue<DiagnosticReportMedia>(json)
        assertEquals(media, deserializedMedia)
    }

    @Test
    fun `serialized JSON ignores null and empty fields`() {
        val media = DiagnosticReportMedia(
            link = Reference(display = FHIRString("link"))
        )
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(media)

        val expectedJson = """
            {
              "link" : {
                "display" : "link"
              }
            }
        """.trimIndent()
        assertEquals(expectedJson, json)

        val deserializedMedia = objectMapper.readValue<DiagnosticReportMedia>(json)
        assertEquals(media, deserializedMedia)
    }

    @Test
    fun `can deserialize from JSON with nullable and empty fields`() {
        val json = """
            {
              "link" : {
                "display" : "link"
              }
            }
        """.trimIndent()
        val media = objectMapper.readValue<DiagnosticReportMedia>(json)

        assertNull(media.id)
        assertEquals(listOf<Extension>(), media.extension)
        assertEquals(listOf<Extension>(), media.modifierExtension)
        assertNull(media.comment)
        assertEquals(Reference(display = FHIRString("link")), media.link)
    }
}
