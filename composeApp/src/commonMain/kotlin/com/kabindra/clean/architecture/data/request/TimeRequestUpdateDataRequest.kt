package com.kabindra.clean.architecture.data.request

import io.github.vinceglb.filekit.PlatformFile

data class TimeRequestUpdateDataRequest(
    val ticket_id: String,
    val nep_date: String,
    val actual_in_time: String,
    val actual_in_time_remarks: String,
    val actual_out_time: String,
    val actual_out_time_remarks: String,
    val verifier_id: String,
    val documents: List<PlatformFile>,
)