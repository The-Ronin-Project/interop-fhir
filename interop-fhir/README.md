# interop-fhir

Home to all of Interop's FHIR models and core FHIR logic.

## Models

The FHIR models in this project are very forgiving. The models themselves will support completely invalid FHIR to ensure
that we are able to consistently read from all sources even if their data is flawed or problematic.

Models exist for all required R4 elements. There are some non-R4 models when our integrations requiring using pre-R4
sources, but they all provided a translation to the R4 models.

## Validation

Validators are created for all R4 resources necessary for ingestion. The validator for a given resource will provide the
specific validation needed to validate that its core elements meet the R4 spec. The validation logic also will walk
through all of a resource's properties and validate them against expectations.

In addition to the specific validation codified for each resource and datatype with special rules, some annotation-based
validation also exists which will be auto-enforced by the resource and datatype validation process.

### Annotations

* RequiredField
    * Can be used on any datatype.
    * For single valued types, it will require they be non-null.
    * For Collection types, it will require they are both non-null and non-empty.
* RequiredValueSet
    * Can be used on a `Code` or `List<Code>`
    * Ensures that the Code, when non-null, belongs to the provided ValueSet enum.
* SupportedDynamicValueTypes
    * Can be used on a `DyanmicValue`
    * Ensures that the DynamicValue, when non-null, is of one of the provided types.
* SupportedReferenceTypes
    * Can be used on a `Reference`, `List<Reference>` or `DynamicValue` that supports References.
    * Ensures that any provided values with discrete types match one of the listed required types, returning a
      Validation Warning for any items that do not.
