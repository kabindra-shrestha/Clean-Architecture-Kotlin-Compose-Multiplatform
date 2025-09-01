package com.kabindra.clean.architecture.data.request

import io.github.vinceglb.filekit.PlatformFile

data class ApplyLeaveRequestDataRequest(
    val leave_type_id: String,
    val leave_option_id: String,
    val nep_start_date: String,
    val nep_end_date: String,
    val verifier_id: String,
    val documents: List<PlatformFile>,
    val remarks: String,
)