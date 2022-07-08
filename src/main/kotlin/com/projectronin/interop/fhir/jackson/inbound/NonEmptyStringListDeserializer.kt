package com.projectronin.interop.fhir.jackson.inbound

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

/**
 * Custom deserializer that will ensure non-empty Strings are not included in the List.
 */
class NonEmptyStringListDeserializer : JsonDeserializer<List<String>>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): List<String> {
        val list = mutableListOf<String>()
        while (p.nextToken() != JsonToken.END_ARRAY) {
            val result = p.text
            if (!result.isNullOrBlank()) {
                list.add(result)
            }
        }
        return list
    }
}
