package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.EditLeaveDocument
import com.kabindra.clean.architecture.domain.entity.EditLeaveRequest
import com.kabindra.clean.architecture.domain.entity.EditLeaveRequestInfo
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class EditLeaveRequestDTO(
    val response: EditLeaveRequestInfoDTO?
) : BaseResponse()

@Serializable
data class EditLeaveRequestInfoDTO(
    val leave_id: Int = 0,
    val verifier_id: Int = 0,
    val leave_option_id: Int = 0,
    val leave_type_id: Int = 0,
    val nep_start_date: String = "",
    val nep_end_date: String = "",
    val remarks: String = "",
    val documents: List<EditLeaveDocumentDTO>? = listOf(),
)

@Serializable
data class EditLeaveDocumentDTO(
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
fun EditLeaveRequestDTO.toDomain(): EditLeaveRequest {
    return EditLeaveRequest(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun EditLeaveRequestInfoDTO.toDomain(): EditLeaveRequestInfo {
    return EditLeaveRequestInfo(
        leave_id = leave_id,
        verifier_id = verifier_id,
        leave_option_id = leave_option_id,
        leave_type_id = leave_type_id,
        nep_start_date = nep_start_date,
        nep_end_date = nep_end_date,
        remarks = remarks,
        documents = documents?.map { it.toDomain() },
    )
}

fun EditLeaveDocumentDTO.toDomain(): EditLeaveDocument {
    return EditLeaveDocument(
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