package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val response: ProfileInfo?,
) : BaseResponse()

@Serializable
data class ProfileInfo(
    val general_information: GeneralInformation?,
    val profile: ProfileDetails?,
    val documents: List<Document>? = listOf(),
    val training: List<Training>? = listOf(),
    val misc: Misc?,
    val salary_slip: List<String>? = listOf(),
    val payslip: List<String>? = listOf(),
    val approver: List<String>? = listOf()
)

@Serializable
data class GeneralInformation(
    val address: String? = "",
    val birthday: String? = "",
    val branch: String? = "",
    val company: String? = "",
    val department: String? = "",
    val designation: String? = "",
    val email: String? = "",
    val employee_code: String? = "",
    val gender: String? = "",
    val name: String? = "",
    val pan: String? = "",
    val phone: String? = "",
    val profile_img: String? = "",
    val reports_to: String? = "",
    val sub_branch: String? = "",
    val unit: String? = "",
    val username: String? = "",
    val working_hours: String? = ""
)

@Serializable
data class ProfileDetails(
    val personal_information: PersonalInformation?,
    val family_information: FamilyInformation?,
    val emergency_contact_information: EmergencyContactInformation?,
    val experience: List<Experience>? = listOf(),
    val education_information: List<EducationInformation>? = listOf()
)

@Serializable
data class Document(
    val name: String? = "",
    val path: String? = ""
)

@Serializable
data class Training(
    val name: String? = "",
    val course: String? = "",
    val time: String? = ""
)

@Serializable
data class Misc(
    val bank_information: BankInformation?,
    val biometric_information: List<BiometricInformation>? = listOf(),
    val retirement_fund: RetirementFund?
)

@Serializable
data class BankInformation(
    val bank: String? = "",
    val account_number: String? = "",
    val outsource_company_name: String? = ""
)

@Serializable
data class BiometricInformation(
    val branch: String? = "",
    val device: String? = "",
    val updated_at: String? = ""
)

@Serializable
data class RetirementFund(
    val cit_number: String? = "",
    val retirement_fund_name: String? = "",
    val retirement_fund_nuumber: String? = ""
)

@Serializable
data class EducationInformation(
    val name: String? = "",
    val time: String? = ""
)

@Serializable
data class EmergencyContactInformation(
    val name: String? = "",
    val phone: String? = "",
    val relation: String? = ""
)

@Serializable
data class Experience(
    val name: String? = "",
    val time: String? = ""
)

@Serializable
data class FamilyInformation(
    val father: String? = "",
    val grandfather: String? = "",
    val mother: String? = "",
    val spouse: String? = ""
)

@Serializable
data class PersonalInformation(
    val phone: String? = "",
    val email: String? = "",
    val marital_status: String? = "",
    val nationality: String? = "",
    val citizenship_no: String? = "",
    val permanent_address: String? = "",
    val temporary_address: String? = ""
)




