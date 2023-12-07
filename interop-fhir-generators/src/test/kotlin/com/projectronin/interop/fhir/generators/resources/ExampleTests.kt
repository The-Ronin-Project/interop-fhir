package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.name
import com.projectronin.interop.fhir.generators.primitives.date
import com.projectronin.interop.fhir.generators.primitives.of
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ExampleTests {
    @Test
    fun `example using data generators`() {
        // You are building a person, Jane Doe, who is female, and needs a specific birthdate for searching.
        val patient =
            patient {
                // You only need to specify the features you required.
                // For Example the name
                name of
                    listOf(
                        name {
                            given of listOf("Jane")
                            family of "Doe"
                        },
                    )
                // Gender
                gender of "Female"

                // And birthdate
                birthDate of
                    date {
                        month of 10
                        day of 27
                        year of 1990
                    }
            }

        // Now you have a valid patient that reflects everything you need for your tests and fills in any other required elements with random data.
        println(patient)

        // And your test can leverage that information for its logic and validation.
        assertEquals("Jane", patient.name.first().given.first().value)
        assertEquals("Doe", patient.name.first().family?.value)
        assertEquals("Female", patient.gender?.value)
        assertTrue(patient.birthDate?.value?.startsWith("1990")!!)
    }
}
