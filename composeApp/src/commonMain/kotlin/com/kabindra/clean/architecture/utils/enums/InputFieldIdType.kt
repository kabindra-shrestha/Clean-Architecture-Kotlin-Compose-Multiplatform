package com.kabindra.clean.architecture.utils.enums

enum class InputFieldIdType(val id: String) {
    LeaveRequestLeaveType("leave_type_id"),
    LeaveRequestLeaveOption("leave_option_id"),
    LeaveRequestStartDate("nep_start_date"),
    LeaveRequestEndDate("nep_end_date"),
    LeaveRequestVerifier("verifier_id"),
    LeaveRequestRemarks("remarks"),
    LeaveRequestDocuments("documents.0"),
    EditLeaveRequestLeaveType("leave_type_id"),
    EditLeaveRequestLeaveOption("leave_option_id"),
    EditLeaveRequestStartDate("nep_start_date"),
    EditLeaveRequestEndDate("nep_end_date"),
    EditLeaveRequestVerifier("verifier_id"),
    EditLeaveRequestRemarks("remarks"),
    EditLeaveRequestDocuments("documents.0"),

    TimeRequestDate("nep_date"),
    TimeRequestActualInTime("actual_in_time"),
    TimeRequestActualInTimeRemarks("actual_in_time_remarks"),
    TimeRequestActualOutTime("actual_out_time"),
    TimeRequestActualOutTimeRemarks("actual_out_time_remarks"),
    TimeRequestVerifiedId("verifier_id"),
    TimeRequestDocument("documents.0"),
    EditTimeRequestDate("nep_date"),
    EditTimeRequestActualInTime("actual_in_time"),
    EditTimeRequestActualInTimeRemarks("actual_in_time_remarks"),
    EditTimeRequestActualOutTime("actual_out_time"),
    EditTimeRequestActualOutTimeRemarks("actual_out_time_remarks"),
    EditTimeRequestVerifiedId("verifier_id"),
    EditTimeRequestDocument("documents.0"),

}

inline fun <reified T : Enum<T>> getInputFieldIdType(id: String): InputFieldIdType {
    return enumValues<T>().find { (it as InputFieldIdType).id == id } as InputFieldIdType
}