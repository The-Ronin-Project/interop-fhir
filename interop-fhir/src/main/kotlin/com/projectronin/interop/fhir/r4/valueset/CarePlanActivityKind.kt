package com.projectronin.interop.fhir.r4.valueset

import com.projectronin.interop.common.enums.CodedEnum

/**
 * See [FHIR Spec](http://hl7.org/fhir/valueset-care-plan-activity-kind.html)
 */
enum class CarePlanActivityKind(override val code: String) : CodedEnum<CarePlanActivityKind> {
    /**
     * A booking of a healthcare event among patient(s), practitioner(s), related person(s) and/or device(s) for a
     * specific date/time. This may result in one or more Encounter(s).
     */
    APPOINTMENT("Appointment"),

    /**
     * A request to convey information; e.g. the CDS system proposes that an alert be sent to a responsible provider,
     * the CDS system proposes that the public health agency be notified about a reportable condition.
     */
    COMMUNICATIONREQUEST("CommunicationRequest"),

    /**
     * Represents a request for a patient to employ a medical device. The device may be an implantable device, or an
     * external assistive device, such as a walker.
     */
    DEVICEREQUEST("DeviceRequest"),

    /**
     * An order or request for both supply of the medication and the instructions for administration of the medication
     * to a patient. The resource is called "MedicationRequest" rather than "MedicationPrescription" or
     * "MedicationOrder" to generalize the use across inpatient and outpatient settings, including care plans, etc.,
     * and to harmonize with workflow patterns.
     */
    MEDICATIONREQUEST("MedicationRequest"),

    /**
     * A request to supply a diet, formula feeding (enteral) or oral nutritional supplement to a patient/resident.
     */
    NUTRITIONORDER("NutritionOrder"),

    /**
     * A task to be performed.
     */
    TASK("Task"),

    /**
     * A record of a request for service such as diagnostic investigations, treatments, or operations to be performed.
     */
    SERVICEREQUEST("ServiceRequest"),

    /**
     * An authorization for the provision of glasses and/or contact lenses to a patient.
     */
    VISIONPRESCRIPTION("VisionPrescription"),
}
