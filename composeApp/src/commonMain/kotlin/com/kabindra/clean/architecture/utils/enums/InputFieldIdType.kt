package com.kabindra.clean.architecture.utils.enums

enum class InputFieldIdType(val id: String) {
    InputFieldType("input_field_type"),
}

inline fun <reified T : Enum<T>> getInputFieldIdType(id: String): InputFieldIdType {
    return enumValues<T>().find { (it as InputFieldIdType).id == id } as InputFieldIdType
}