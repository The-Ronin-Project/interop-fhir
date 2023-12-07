package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.codeableConcept
import com.projectronin.interop.fhir.generators.datatypes.expression
import com.projectronin.interop.fhir.generators.datatypes.reference
import com.projectronin.interop.fhir.generators.primitives.of
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.resource.RequestGroupAction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class RequestGroupGeneratorTest {
    @Test
    fun `function works with default`() {
        val requestGroup = requestGroup {}
        assertNull(requestGroup.id)
        assertNull(requestGroup.meta)
        assertNull(requestGroup.implicitRules)
        assertNull(requestGroup.language)
        assertNull(requestGroup.text)
        assertEquals(0, requestGroup.contained.size)
        assertEquals(0, requestGroup.extension.size)
        assertEquals(0, requestGroup.modifierExtension.size)
        assertEquals(0, requestGroup.identifier.size)
        assertEquals(0, requestGroup.instantiatesCanonical.size)
        assertEquals(0, requestGroup.instantiatesUri.size)
        assertEquals(0, requestGroup.basedOn.size)
        assertEquals(0, requestGroup.replaces.size)
        assertNull(requestGroup.groupIdentifier)
        assertNotNull(requestGroup.status)
        assertNotNull(requestGroup.intent)
        assertNotNull(requestGroup.priority)
        assertNull(requestGroup.code)
        assertNotNull(requestGroup.subject)
        assertNull(requestGroup.encounter)
        assertNull(requestGroup.authoredOn)
        assertNull(requestGroup.author)
        assertEquals(0, requestGroup.reasonCode.size)
        assertEquals(0, requestGroup.reasonReference.size)
        assertEquals(0, requestGroup.note.size)
        assertEquals(0, requestGroup.action.size)
    }

    @Test
    fun `function works with parameters`() {
        val requestGroup =
            requestGroup {
                code of codeableConcept { }
                author of reference("Practitioner", "1234")
            }
        assertNotNull(requestGroup.code)
        assertNotNull(requestGroup.author)
    }
}

class RequestGroupActionGeneratorTest {
    @Test
    fun `function works with default`() {
        val requestGroupAction = requestGroupAction {}
        assertNull(requestGroupAction.id)
        assertEquals(0, requestGroupAction.extension.size)
        assertEquals(0, requestGroupAction.modifierExtension.size)
        assertNull(requestGroupAction.prefix)
        assertNull(requestGroupAction.title)
        assertNull(requestGroupAction.description)
        assertNull(requestGroupAction.textEquivalent)
        assertNull(requestGroupAction.priority)
        assertEquals(0, requestGroupAction.documentation.size)
        assertEquals(0, requestGroupAction.condition.size)
        assertEquals(0, requestGroupAction.relatedAction.size)
        assertNull(requestGroupAction.timing)
        assertEquals(0, requestGroupAction.participant.size)
        assertNull(requestGroupAction.type)
        assertNull(requestGroupAction.groupingBehavior)
        assertNull(requestGroupAction.selectionBehavior)
        assertNull(requestGroupAction.requiredBehavior)
        assertNull(requestGroupAction.precheckBehavior)
        assertNull(requestGroupAction.cardinalityBehavior)
        assertNull(requestGroupAction.resource)
        assertEquals(0, requestGroupAction.action.size)
    }

    @Test
    fun `function works with parameters`() {
        val requestGroupAction =
            requestGroupAction {
                title of "action title"
                action plus
                    requestGroupAction {
                        title of "sub action title"
                    }
            }
        assertEquals(FHIRString("action title"), requestGroupAction.title)
        assertEquals(listOf(RequestGroupAction(title = FHIRString("sub action title"))), requestGroupAction.action)
    }
}

class RequestGroupConditionGeneratorTest {
    @Test
    fun `function works with default`() {
        val requestGroupCondition = requestGroupCondition {}
        assertNull(requestGroupCondition.id)
        assertEquals(0, requestGroupCondition.extension.size)
        assertEquals(0, requestGroupCondition.modifierExtension.size)
        assertNotNull(requestGroupCondition.kind)
        assertNull(requestGroupCondition.expression)
    }

    @Test
    fun `function works with parameters`() {
        val requestGroupCondition =
            requestGroupCondition {
                expression of expression { }
            }
        assertNotNull(requestGroupCondition.expression)
    }
}

class RequestGroupRelatedActionGeneratorTest {
    @Test
    fun `function works with default`() {
        val requestGroupRelatedAction = requestGroupRelatedAction {}
        assertNull(requestGroupRelatedAction.id)
        assertEquals(0, requestGroupRelatedAction.extension.size)
        assertNotNull(requestGroupRelatedAction.actionId)
        assertNotNull(requestGroupRelatedAction.relationship)
        assertNull(requestGroupRelatedAction.offset)
    }

    @Test
    fun `function works with parameters`() {
        val requestGroupRelatedAction =
            requestGroupRelatedAction {
                actionId of Id("action123")
            }
        assertEquals(Id("action123"), requestGroupRelatedAction.actionId)
    }
}
