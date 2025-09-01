package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class EditLeaveRequest(
    val response: EditLeaveRequestInfo?,
) : BaseResponse()

@Serializable
data class EditLeaveRequestInfo(
    val leave_id: Int? = 0,
    val verifier_id: Int? = 0,
    val leave_option_id: Int? = 0,
    val leave_type_id: Int? = 0,
    val nep_start_date: String? = "",
    val nep_end_date: String? = "",
    val remarks: String? = "",
    val documents: List<EditLeaveDocument>? = listOf(),
)

@Serializable
data class EditLeaveDocument(
    val id: Int? = 0,
    val name: String? = "",
    val path: String? = "",
    val owner_name: String? = "",
    val icon: String? = "",
    val state: String? = "",
    val date_added: String? = "",
    val canRemove: Boolean? = false,
    val extension: String? = ""
)
