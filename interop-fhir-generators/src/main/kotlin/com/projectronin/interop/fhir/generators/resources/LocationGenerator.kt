package com.projectronin.interop.fhir.generators.resources

import com.projectronin.interop.fhir.generators.datatypes.CodeableConceptGenerator
import com.projectronin.interop.fhir.generators.datatypes.ContactPointGenerator
import com.projectronin.interop.fhir.generators.datatypes.ExtensionGenerator
import com.projectronin.interop.fhir.generators.datatypes.IdentifierGenerator
import com.projectronin.interop.fhir.generators.primitives.CodeGenerator
import com.projectronin.interop.fhir.generators.primitives.DecimalGenerator
import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Coding
import com.projectronin.interop.fhir.r4.datatype.ContactPoint
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Decimal
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.datatype.primitive.asFHIR
import com.projectronin.interop.fhir.r4.resource.Location
import com.projectronin.interop.fhir.r4.resource.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.resource.LocationPosition
import com.projectronin.interop.fhir.r4.resource.Resource
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.NullDataGenerator
import com.projectronin.test.data.generator.collection.ListDataGenerator
import com.projectronin.test.data.generator.util.HospitalNameGenerator

class LocationGenerator(
    override val id: DataGenerator<Id?> = NullDataGenerator(),
    override val meta: DataGenerator<Meta?> = NullDataGenerator(),
    override val implicitRules: DataGenerator<Uri?> = NullDataGenerator(),
    override val language: DataGenerator<Code?> = NullDataGenerator(),
    override val text: DataGenerator<Narrative?> = NullDataGenerator(),
    override val contained: ListDataGenerator<Resource<*>?> = ListDataGenerator(0, NullDataGenerator()),
    override val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    override val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator()),
    val identifier: ListDataGenerator<Identifier> = ListDataGenerator(0, IdentifierGenerator()),
    val status: DataGenerator<Code?> = NullDataGenerator(),
    val operationalStatus: DataGenerator<Coding?> = NullDataGenerator(),
    val name: DataGenerator<String> = HospitalNameGenerator(),
    val alias: ListDataGenerator<String> = ListDataGenerator(0, HospitalNameGenerator()),
    val description: DataGenerator<FHIRString?> = NullDataGenerator(),
    val mode: DataGenerator<Code?> = NullDataGenerator(),
    val type: ListDataGenerator<CodeableConcept> = ListDataGenerator(0, CodeableConceptGenerator()),
    val telecom: ListDataGenerator<ContactPoint> = ListDataGenerator(0, ContactPointGenerator()),
    val address: DataGenerator<Address?> = NullDataGenerator(),
    val physicalType: DataGenerator<CodeableConcept?> = NullDataGenerator(),
    val position: DataGenerator<LocationPosition?> = NullDataGenerator(),
    val managingOrganization: DataGenerator<Reference?> = NullDataGenerator(),
    val partOf: DataGenerator<Reference?> = NullDataGenerator(),
    val hoursOfOperation: ListDataGenerator<LocationHoursOfOperation> = ListDataGenerator(
        0,
        LocationHoursOfOperationGenerator()
    ),
    val availabilityExceptions: DataGenerator<FHIRString?> = NullDataGenerator(),
    val endpoint: ListDataGenerator<Reference?> = ListDataGenerator(0, NullDataGenerator())
) : DomainResource<Location> {
    override fun toFhir(): Location =
        Location(
            id = id.generate(),
            meta = meta.generate(),
            implicitRules = implicitRules.generate(),
            language = language.generate(),
            text = text.generate(),
            contained = contained.generate().filterNotNull(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            identifier = identifier.generate(),
            status = status.generate(),
            operationalStatus = operationalStatus.generate(),
            name = name.generate().asFHIR(),
            alias = alias.generate().map { it.asFHIR() },
            description = description.generate(),
            mode = mode.generate(),
            type = type.generate(),
            telecom = telecom.generate(),
            address = address.generate(),
            physicalType = physicalType.generate(),
            position = position.generate(),
            managingOrganization = managingOrganization.generate(),
            partOf = partOf.generate(),
            hoursOfOperation = hoursOfOperation.generate(),
            availabilityExceptions = availabilityExceptions.generate(),
            endpoint = endpoint.generate().filterNotNull()
        )
}

class LocationPositionGenerator : DataGenerator<LocationPosition>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val longitude: DataGenerator<Decimal?> = DecimalGenerator()
    val latitude: DataGenerator<Decimal?> = DecimalGenerator()
    val altitude: DataGenerator<Decimal?> = NullDataGenerator()

    override fun generateInternal(): LocationPosition =
        LocationPosition(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            longitude = longitude.generate(),
            latitude = latitude.generate(),
            altitude = altitude.generate()
        )
}

class LocationHoursOfOperationGenerator : DataGenerator<LocationHoursOfOperation>() {
    val id: DataGenerator<FHIRString?> = NullDataGenerator()
    val extension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val modifierExtension: ListDataGenerator<Extension> = ListDataGenerator(0, ExtensionGenerator())
    val daysOfWeek: ListDataGenerator<Code?> = ListDataGenerator(0, CodeGenerator(DayOfWeek::class))
    val allDay: DataGenerator<FHIRBoolean?> = NullDataGenerator()
    val openingTime: DataGenerator<Time?> = NullDataGenerator()
    val closingTime: DataGenerator<Time?> = NullDataGenerator()

    override fun generateInternal(): LocationHoursOfOperation =
        LocationHoursOfOperation(
            id = id.generate(),
            extension = extension.generate(),
            modifierExtension = modifierExtension.generate(),
            daysOfWeek = daysOfWeek.generate().filterNotNull(),
            allDay = allDay.generate(),
            openingTime = openingTime.generate(),
            closingTime = closingTime.generate()
        )
}

fun location(block: LocationGenerator.() -> Unit): Location {
    val location = LocationGenerator()
    location.apply(block)
    return location.toFhir()
}

fun locationPosition(block: LocationPositionGenerator.() -> Unit): LocationPosition {
    val locationPosition = LocationPositionGenerator()
    locationPosition.apply(block)
    return locationPosition.generate()
}

fun locationHoursOfOperation(block: LocationHoursOfOperationGenerator.() -> Unit): LocationHoursOfOperation {
    val locationHoursOfOperation = LocationHoursOfOperationGenerator()
    locationHoursOfOperation.apply(block)
    return locationHoursOfOperation.generate()
}
