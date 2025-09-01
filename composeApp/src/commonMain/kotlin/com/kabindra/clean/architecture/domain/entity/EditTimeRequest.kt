package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class EditTimeRequest(
    val response: EditTimeRequestInfo?,
) : BaseResponse()

@Serializable
data class EditTimeRequestInfo(
    val time_request_id: Int? = 0,
    val verifier_id: Int? = 0,
    val nep_date: String? = "",
    val check_in_time: String? = "",
    val check_out_time: String? = "",
    val actual_in_time: String? = "",
    val actual_in_time_remarks: String? = "",
    val actual_out_time: String? = "",
    val actual_out_time_remarks: String? = "",
    val documents: List<EditTimeDocument>? = listOf(),
)

@Serializable
data class EditTimeDocument(
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