package com.kabindra.clean.architecture.utils.enums

enum class AttendanceContentType(val title: String, val slug: String) {
    MyAttendance("MyAttendance", "my-attendance"),
    /*TeamMembers("TeamMembers", "team-members"),*/
}

inline fun <reified T : Enum<T>> getAttendanceContentType(slug: String): AttendanceContentType {
    return enumValues<T>().find { (it as AttendanceContentType).slug == slug } as AttendanceContentType
}

