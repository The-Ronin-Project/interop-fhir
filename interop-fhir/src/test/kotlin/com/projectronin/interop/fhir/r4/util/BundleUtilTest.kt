package com.projectronin.interop.fhir.r4.util

import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.Signature
import com.projectronin.interop.fhir.r4.datatype.primitive.Canonical
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.mergeBundles
import com.projectronin.interop.fhir.r4.resource.Bundle
import com.projectronin.interop.fhir.r4.resource.BundleEntry
import com.projectronin.interop.fhir.r4.resource.BundleLink
import com.projectronin.interop.fhir.r4.resource.PractitionerRole
import com.projectronin.interop.fhir.r4.valueset.BundleType
import com.projectronin.interop.fhir.util.asCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BundleUtilTest {
    @Test
    fun `merge retains original metadata`() {
        val profileCanonical1 = Canonical("RoninPractitioner")
        val link1 = BundleLink(relation = FHIRString("next"), url = Uri("http://example.com"))
        val signature1 =
            Signature(
                type = listOf(Coding(display = FHIRString("type"))),
                `when` = Instant("2017-01-01T00:00:00Z"),
                who = Reference(reference = FHIRString("who")),
            )
        val bundle1 =
            Bundle(
                id = Id("123"),
                meta = Meta(profile = listOf(profileCanonical1)),
                implicitRules = Uri("implicit-rules"),
                language = Code("en-US"),
                identifier = Identifier(value = FHIRString("identifier")),
                type = BundleType.SEARCHSET.asCode(),
                timestamp = Instant("2017-01-01T00:00:00Z"),
                total = UnsignedInt(1),
                link = listOf(link1),
                entry = listOf(BundleEntry()),
                signature = signature1,
            )

        val profileCanonical2 = Canonical("RoninPractitionerRole")
        val link2 = BundleLink(relation = FHIRString("next"), url = Uri("http://example.com/2"))
        val signature2 =
            Signature(
                type = listOf(Coding(display = FHIRString("type2"))),
                `when` = Instant("2017-01-01T00:00:00Z"),
                who = Reference(reference = FHIRString("who2")),
            )
        val bundle2 =
            Bundle(
                id = Id("1232"),
                meta = Meta(profile = listOf(profileCanonical2)),
                implicitRules = Uri("implicit-rules2"),
                language = Code("en-US2"),
                identifier = Identifier(value = FHIRString("identifier2")),
                type = BundleType.BATCH.asCode(),
                timestamp = Instant("2017-01-02T00:00:00Z"),
                total = UnsignedInt(1),
                link = listOf(link2),
                entry = listOf(BundleEntry()),
                signature = signature2,
            )

        val merged = mergeBundles(bundle1, bundle2)
        assertEquals(Id("123"), merged.id)
        assertEquals(Meta(profile = listOf(profileCanonical1)), merged.meta)
        assertEquals(Uri("implicit-rules"), merged.implicitRules)
        assertEquals(Code("en-US"), merged.language)
        assertEquals(Identifier(value = FHIRString("identifier")), merged.identifier)
        assertEquals(BundleType.SEARCHSET.asCode(), merged.type)
        assertEquals(Instant("2017-01-01T00:00:00Z"), merged.timestamp)
        assertEquals(listOf(link1), merged.link)
        assertEquals(signature1, merged.signature)
    }

    @Test
    fun `merge can combine empty bundles`() {
        val bundle1 =
            Bundle(
                id = Id("123"),
                type = BundleType.SEARCHSET.asCode(),
            )
        val bundle2 =
            Bundle(
                id = Id("1232"),
                type = BundleType.SEARCHSET.asCode(),
            )

        val merged = mergeBundles(bundle1, bundle2)
        assertEquals(0, merged.total?.value)
        assertEquals(listOf<BundleEntry>(), merged.entry)
    }

    @Test
    fun `merge can combine empty and populated bundle`() {
        val practitionerRole =
            PractitionerRole(
                identifier = listOf(Identifier(value = FHIRString("id"))),
            )

        val bundle1 =
            Bundle(
                id = Id("123"),
                type = BundleType.SEARCHSET.asCode(),
            )
        val bundle2 =
            Bundle(
                id = Id("1232"),
                type = BundleType.SEARCHSET.asCode(),
                total = UnsignedInt(1),
                entry = listOf(BundleEntry(resource = practitionerRole)),
            )

        val merged = mergeBundles(bundle1, bundle2)
        assertEquals(1, merged.total?.value)
        assertEquals(listOf(BundleEntry(resource = practitionerRole)), merged.entry)
    }

    @Test
    fun `merge can combine populated bundles`() {
        val practitionerRoleOne =
            PractitionerRole(
                id = Id("practitionerOne"),
                identifier = listOf(Identifier(value = FHIRString("id"))),
            )

        val practitionerRoleTwo =
            PractitionerRole(
                id = Id("practitionerTwo"),
                identifier = listOf(Identifier(value = FHIRString("id"))),
            )

        val bundle1 =
            Bundle(
                id = Id("123"),
                type = BundleType.SEARCHSET.asCode(),
                total = UnsignedInt(1),
                entry =
                    listOf(
                        BundleEntry(
                            id = FHIRString("id"),
                            resource = practitionerRoleOne,
                        ),
                    ),
            )
        val bundle2 =
            Bundle(
                id = Id("1232"),
                type = BundleType.SEARCHSET.asCode(),
                total = UnsignedInt(1),
                entry =
                    listOf(
                        BundleEntry(
                            id = FHIRString("id"),
                            resource = practitionerRoleTwo,
                        ),
                    ),
            )

        val merged = mergeBundles(bundle1, bundle2)
        assertEquals(2, merged.total?.value)
        assertEquals(
            listOf(
                BundleEntry(
                    id = FHIRString("id"),
                    resource = practitionerRoleOne,
                ),
                BundleEntry(
                    id = FHIRString("id"),
                    resource = practitionerRoleTwo,
                ),
            ),
            merged.entry,
        )
    }

    @Test
    fun `merge can de-duplicate bundle entries`() {
        /*
        De-dup happens at the id level of the resource within a bundleEntry within a bundle. Last resource wins.
         */
        val practitionerRoleOne =
            PractitionerRole(
                id = Id("practitionerOne"),
                identifier = listOf(Identifier(value = FHIRString("id"))),
            )
        val practitionerRoleTwo =
            PractitionerRole(
                id = Id("practitionerTwo"),
                identifier = listOf(Identifier(value = FHIRString("id"))),
            )
        val practitionerRoleThree =
            PractitionerRole(
                id = Id("practitionerOne"),
                identifier = listOf(Identifier(value = FHIRString("id"))),
            )
        val practitionerRoleFour =
            PractitionerRole(
                id = Id("practitionerThree"),
                identifier = listOf(Identifier(value = FHIRString("id"))),
            )
        val practitionerRoleFive =
            PractitionerRole(
                id = Id("practitionerThree"),
                identifier = listOf(Identifier(value = FHIRString("id"))),
            )
        val practitionerRoleSix =
            PractitionerRole(
                id = Id("practitionerThree"),
                identifier = listOf(Identifier(value = FHIRString("id"))),
            )

        val bundle1 =
            Bundle(
                id = Id("bundleOne"),
                type = BundleType.SEARCHSET.asCode(),
                total = UnsignedInt(1),
                entry =
                    listOf(
                        BundleEntry(
                            id = FHIRString("bundleEntryOne"),
                            resource = practitionerRoleOne,
                        ),
                    ),
            )
        val bundle2 =
            Bundle(
                id = Id("bundleTwo"),
                type = BundleType.SEARCHSET.asCode(),
                total = UnsignedInt(1),
                entry =
                    listOf(
                        BundleEntry(
                            id = FHIRString("bundleEntryTwo"),
                            resource = practitionerRoleTwo,
                        ),
                    ),
            )
        val bundle3 =
            Bundle(
                id = Id("bundleThree"),
                type = BundleType.SEARCHSET.asCode(),
                total = UnsignedInt(1),
                entry =
                    listOf(
                        BundleEntry(
                            id = FHIRString("bundleEntryThree"),
                            resource = practitionerRoleThree,
                        ),
                    ),
            )
        val bundle4 =
            Bundle(
                id = Id("bundleFour"),
                type = BundleType.SEARCHSET.asCode(),
                total = UnsignedInt(1),
                entry =
                    listOf(
                        BundleEntry(
                            id = FHIRString("bundleEntryFour"),
                            resource = practitionerRoleFour,
                        ),
                    ),
            )
        val bundle5 =
            Bundle(
                id = Id("bundleFive"),
                type = BundleType.SEARCHSET.asCode(),
                total = UnsignedInt(1),
                entry =
                    listOf(
                        BundleEntry(
                            id = FHIRString("bundleEntryFive"),
                            resource = practitionerRoleFive,
                        ),
                    ),
            )
        val bundle6 =
            Bundle(
                id = Id("bundleSix"),
                type = BundleType.SEARCHSET.asCode(),
                total = UnsignedInt(1),
                entry =
                    listOf(
                        BundleEntry(
                            id = FHIRString("bundleEntrySix"),
                            resource = practitionerRoleSix,
                        ),
                    ),
            )

        val bundles = listOf(bundle1, bundle2, bundle3, bundle4, bundle5, bundle6)
        val merged = bundles.reduce { mergedBundle, currentBundle -> mergeBundles(mergedBundle, currentBundle) }

        assertEquals(
            listOf(
                BundleEntry(
                    id = FHIRString("bundleEntryThree"),
                    resource = practitionerRoleOne,
                ),
                BundleEntry(
                    id = FHIRString("bundleEntryTwo"),
                    resource = practitionerRoleTwo,
                ),
                BundleEntry(
                    id = FHIRString("bundleEntrySix"),
                    resource = practitionerRoleFour,
                ),
            ),
            merged.entry,
        )
    }
}
