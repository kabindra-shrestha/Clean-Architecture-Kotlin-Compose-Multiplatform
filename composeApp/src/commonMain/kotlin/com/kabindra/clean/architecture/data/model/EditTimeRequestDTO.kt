package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.EditTimeDocument
import com.kabindra.clean.architecture.domain.entity.EditTimeRequest
import com.kabindra.clean.architecture.domain.entity.EditTimeRequestInfo
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class EditTimeRequestDTO(
    val response: EditTimeRequestInfoDTO?
) : BaseResponse()

@Serializable
data class EditTimeRequestInfoDTO(
    val time_request_id: Int? = 0,
    val verifier_id: Int? = 0,
    val nep_date: String? = "",
    val check_in_time: String? = "",
    val check_out_time: String? = "",
    val actual_in_time: String? = "",
    val actual_in_time_remarks: String? = "",
    val actual_out_time: String? = "",
    val actual_out_time_remarks: String? = "",
    val documents: List<EditTimeDocumentDTO>? = listOf(),

    )

@Serializable
data class EditTimeDocumentDTO(
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

// Mapper function
fun EditTimeRequestDTO.toDomain(): EditTimeRequest {
    return EditTimeRequest(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun EditTimeRequestInfoDTO.toDomain(): EditTimeRequestInfo {
    return EditTimeRequestInfo(
        time_request_id = time_request_id,
        verifier_id = verifier_id,
        nep_date = nep_date,
        check_in_time = check_in_time,
        check_out_time = check_out_time,
        actual_in_time = actual_in_time,
        actual_in_time_remarks = actual_in_time_remarks,
        actual_out_time = actual_out_time,
        actual_out_time_remarks = actual_out_time_remarks,
        documents = documents?.map { it.toDomain() },
    )
}

fun EditTimeDocumentDTO.toDomain(): EditTimeDocument {
    return EditTimeDocument(
        id = id,
        name = name,
        path = path,
        owner_name = owner_name,
        icon = icon,
        state = state,
        date_added = date_added,
        canRemove = canRemove,
        extension = extension,
    )
}






