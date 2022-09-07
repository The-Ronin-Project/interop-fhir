package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ConceptMapDependsOn
import com.projectronin.interop.fhir.r4.datatype.ConceptMapElement
import com.projectronin.interop.fhir.r4.datatype.ConceptMapGroup
import com.projectronin.interop.fhir.r4.datatype.ConceptMapTarget
import com.projectronin.interop.fhir.r4.datatype.ConceptMapUnmapped
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.valueset.ConceptMapEquivalence
import com.projectronin.interop.fhir.r4.valueset.ConceptMapMode
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class R4ConceptMapGroupValidatorsTest {
    @Test
    fun `unmapped - mode is required`() {
        val ex = assertThrows<IllegalArgumentException> {
            val unmapped = ConceptMapUnmapped(
                mode = null
            )
            R4ConceptMapUnmappedValidator.validate(unmapped).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: mode is a required element @ ConceptMapUnmapped.mode",
            ex.message
        )
    }

    @Test
    fun `unmapped -  mode is outside of required value set`() {
        val ex = assertThrows<IllegalArgumentException> {
            val unmapped = ConceptMapUnmapped(
                mode = Code("unsupported-status"),
            )
            R4ConceptMapUnmappedValidator.validate(unmapped).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR INV_VALUE_SET: mode is outside of required value set @ ConceptMapUnmapped.mode",
            ex.message
        )
    }

    @Test
    fun `unmapped -  fixed must also have a code`() {
        val ex = assertThrows<IllegalArgumentException> {
            val unmapped = ConceptMapUnmapped(
                mode = ConceptMapMode.FIXED.asCode(),
                code = null
            )
            R4ConceptMapUnmappedValidator.validate(unmapped).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNCPMPUM_001: If the mode is 'fixed', a code must be provided @ ConceptMapUnmapped",
            ex.message
        )
    }

    @Test
    fun `unmapped -  other must also have a uri`() {
        val ex = assertThrows<IllegalArgumentException> {
            val unmapped = ConceptMapUnmapped(
                mode = ConceptMapMode.OTHER_MAP.asCode(),
                uri = null
            )
            R4ConceptMapUnmappedValidator.validate(unmapped).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNCPMPUM_002: If the mode is 'other-map', a url must be provided @ ConceptMapUnmapped",
            ex.message
        )
    }

    @Test
    fun `unmapped -  works with fixed`() {
        val unmapped = ConceptMapUnmapped(
            mode = ConceptMapMode.FIXED.asCode(),
            uri = Canonical("cannon"),
            code = Code("code")
        )
        R4ConceptMapUnmappedValidator.validate(unmapped).alertIfErrors()
    }

    @Test
    fun `unmapped -  works with OTHER`() {
        val unmapped = ConceptMapUnmapped(
            mode = ConceptMapMode.OTHER_MAP.asCode(),
            uri = Canonical("cannon"),
            code = Code("code")
        )
        R4ConceptMapUnmappedValidator.validate(unmapped).alertIfErrors()
    }

    @Test
    fun `dependsOn - property Required`() {
        val ex = assertThrows<IllegalArgumentException> {
            val dependsOn = ConceptMapDependsOn(
                property = null,
                value = "value"
            )
            R4ConceptMapDependsOnValidator.validate(dependsOn).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: property is a required element @ ConceptMapDependsOn.property",
            ex.message
        )
    }
    @Test
    fun `dependsOn - value Required`() {
        val ex = assertThrows<IllegalArgumentException> {
            val dependsOn = ConceptMapDependsOn(
                property = Uri("google.com"),
                value = null
            )
            R4ConceptMapDependsOnValidator.validate(dependsOn).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: value is a required element @ ConceptMapDependsOn.value",
            ex.message
        )
    }

    @Test
    fun `target - equivalence required`() {
        val ex = assertThrows<IllegalArgumentException> {
            val target = ConceptMapTarget(
                equivalence = null
            )
            R4ConceptMapTargetValidator.validate(target).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: equivalence is a required element @ ConceptMapTarget.equivalence",
            ex.message
        )
    }
    @Test
    fun `target - comments required for inexact`() {
        val ex = assertThrows<IllegalArgumentException> {
            val target = ConceptMapTarget(
                equivalence = ConceptMapEquivalence.INEXACT.asCode(),
                comment = null
            )
            R4ConceptMapTargetValidator.validate(target).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNCPMPTG_001: If the map is narrower or inexact, there SHALL be some comments @ ConceptMapTarget",
            ex.message
        )
    }

    @Test
    fun `target - comments required for narrow`() {
        val ex = assertThrows<IllegalArgumentException> {
            val target = ConceptMapTarget(
                equivalence = ConceptMapEquivalence.NARROWER.asCode(),
                comment = null
            )
            R4ConceptMapTargetValidator.validate(target).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR R4_CNCPMPTG_001: If the map is narrower or inexact, there SHALL be some comments @ ConceptMapTarget",
            ex.message
        )
    }

    @Test
    fun `target - works`() {
        val target = ConceptMapTarget(
            equivalence = ConceptMapEquivalence.EQUAL.asCode()
        )
        R4ConceptMapTargetValidator.validate(target).alertIfErrors()
    }

    @Test
    fun `target - works with narrower`() {
        val target = ConceptMapTarget(
            equivalence = ConceptMapEquivalence.NARROWER.asCode(),
            comment = "comments"
        )
        R4ConceptMapTargetValidator.validate(target).alertIfErrors()
    }

    @Test
    fun `target - works with inexact`() {
        val target = ConceptMapTarget(
            equivalence = ConceptMapEquivalence.INEXACT.asCode(),
            comment = "comments"
        )
        R4ConceptMapTargetValidator.validate(target).alertIfErrors()
    }

    @Test
    fun `element - works`() {
        val element = ConceptMapElement()
        R4ConceptMapElementValidator.validate(element).alertIfErrors()
    }

    @Test
    fun `group - element required`() {
        val ex = assertThrows<IllegalArgumentException> {
            val group = ConceptMapGroup(
                element = null
            )
            R4ConceptMapGroupValidator.validate(group).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: element is a required element @ ConceptMapGroup.element",
            ex.message
        )
    }

    @Test
    fun `group - element at least one`() {
        val ex = assertThrows<IllegalArgumentException> {
            val group = ConceptMapGroup(
                element = emptyList()
            )
            R4ConceptMapGroupValidator.validate(group).alertIfErrors()
        }
        assertEquals(
            "Encountered validation error(s):\n" +
                "ERROR REQ_FIELD: element is a required element @ ConceptMapGroup.element",
            ex.message
        )
    }

    @Test
    fun `group - works`() {
        val group = ConceptMapGroup(
            element = listOf(ConceptMapElement())
        )
        R4ConceptMapGroupValidator.validate(group).alertIfErrors()
    }
}
