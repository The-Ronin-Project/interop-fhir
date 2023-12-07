package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.validate.annotation.RequiredField

/**
 * The Expression structure defines an expression that generates a value. The expression is provided in a specified
 * language (by mime type). The context of use of the expression must specify the context in which the expression is
 * evaluated, and how the result of the expression is used.
 */
@JsonSerialize(using = ExpressionSerializer::class)
@JsonDeserialize(using = ExpressionDeserializer::class)
data class Expression(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    val description: FHIRString? = null,
    val name: Id? = null,
    @RequiredField
    val language: Code?,
    val expression: FHIRString? = null,
    val reference: Uri? = null,
) : Element<Expression>

class ExpressionSerializer : BaseFHIRSerializer<Expression>(Expression::class.java)

class ExpressionDeserializer : BaseFHIRDeserializer<Expression>(Expression::class.java)
