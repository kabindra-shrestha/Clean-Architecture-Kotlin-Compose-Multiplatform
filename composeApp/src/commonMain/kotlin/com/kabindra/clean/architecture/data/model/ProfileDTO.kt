package com.kabindra.clean.architecture.data.model


import com.kabindra.clean.architecture.domain.entity.BankInformation
import com.kabindra.clean.architecture.domain.entity.BiometricInformation
import com.kabindra.clean.architecture.domain.entity.Document
import com.kabindra.clean.architecture.domain.entity.EducationInformation
import com.kabindra.clean.architecture.domain.entity.EmergencyContactInformation
import com.kabindra.clean.architecture.domain.entity.Experience
import com.kabindra.clean.architecture.domain.entity.FamilyInformation
import com.kabindra.clean.architecture.domain.entity.GeneralInformation
import com.kabindra.clean.architecture.domain.entity.Misc
import com.kabindra.clean.architecture.domain.entity.PersonalInformation
import com.kabindra.clean.architecture.domain.entity.Profile
import com.kabindra.clean.architecture.domain.entity.ProfileDetails
import com.kabindra.clean.architecture.domain.entity.ProfileInfo
import com.kabindra.clean.architecture.domain.entity.RetirementFund
import com.kabindra.clean.architecture.domain.entity.Training
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDTO(
    val response: ProfileInfoDTO?
) : BaseResponse()

@Serializable
data class ProfileInfoDTO(
    val general_information: GeneralInformationDTO?,
    val profile: ProfileDetailsDTO?,
    val documents: List<DocumentDTO>? = listOf(),
    val training: List<TrainingDTO>? = listOf(),
    val misc: MiscDTO?,
    val salary_slip: List<String>? = listOf(),
    val payslip: List<String>? = listOf(),
    val approver: List<String>? = listOf()
)

@Serializable
data class GeneralInformationDTO(
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
data class ProfileDetailsDTO(
    val education_information: List<EducationInformationDTO>? = listOf(),
    val emergency_contact_information: EmergencyContactInformationDTO?,
    val experience: List<ExperienceDTO>? = listOf(),
    val family_information: FamilyInformationDTO?,
    val personal_information: PersonalInformationDTO?
)

@Serializable
data class DocumentDTO(
    val name: String? = "",
    val path: String? = ""
)

@Serializable
data class TrainingDTO(
    val course: String? = "",
    val name: String? = "",
    val time: String? = ""
)

@Serializable
data class MiscDTO(
    val bank_information: BankInformationDTO?,
    val biometric_information: List<BiometricInformationDTO>? = listOf(),
    val retirement_fund: RetirementFundDTO?
)

@Serializable
data class BankInformationDTO(
    val account_number: String? = "",
    val bank: String? = "",
    val outsource_company_name: String? = ""
)

@Serializable
data class BiometricInformationDTO(
    val branch: String? = "",
    val device: String? = "",
    val updated_at: String? = ""
)

@Serializable
data class RetirementFundDTO(
    val cit_number: String? = "",
    val retirement_fund_name: String? = "",
    val retirement_fund_nuumber: String? = ""
)

@Serializable
data class EducationInformationDTO(
    val name: String? = "",
    val time: String? = ""
)

@Serializable
data class EmergencyContactInformationDTO(
    val name: String? = "",
    val phone: String? = "",
    val relation: String? = ""
)

@Serializable
data class ExperienceDTO(
    val name: String? = "",
    val time: String? = ""
)

@Serializable
data class FamilyInformationDTO(
    val father: String? = "",
    val grandfather: String? = "",
    val mother: String? = "",
    val spouse: String? = ""
)

@Serializable
data class PersonalInformationDTO(
    val citizenship_no: String? = "",
    val email: String? = "",
    val marital_status: String? = "",
    val nationality: String? = "",
    val permanent_address: String? = "",
    val phone: String? = "",
    val temporary_address: String? = ""
)

fun ProfileDTO.toDomain(): Profile {
    return Profile(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun ProfileInfoDTO.toDomain(): ProfileInfo {
    return ProfileInfo(
        general_information = general_information?.toDomain(),
        profile = profile?.toDomain(),
        documents = documents?.map { it.toDomain() },
        training = training?.map { it.toDomain() },
        misc = misc?.toDomain(),
        salary_slip = salary_slip?.map { it },
        payslip = payslip?.map { it },
        approver = approver?.map { it },
    )
}

fun GeneralInformationDTO.toDomain(): GeneralInformation {
    return GeneralInformation(
        address = address,
        birthday = birthday,
        branch = branch,
        company = company,
        department = department,
        designation = designation,
        email = email,
        employee_code = employee_code,
        gender = gender,
        name = name,
        pan = pan,
        phone = phone,
        profile_img = profile_img,
        reports_to = reports_to,
        sub_branch = sub_branch,
        unit = unit,
        username = username,
        working_hours = working_hours
    )
}

fun ProfileDetailsDTO.toDomain(): ProfileDetails {
    return ProfileDetails(
        education_information = education_information?.map { it.toDomain() },
        emergency_contact_information = emergency_contact_information?.toDomain(),
        experience = experience?.map { it.toDomain() },
        family_information = family_information?.toDomain(),
        personal_information = personal_information?.toDomain(),
    )
}

fun DocumentDTO.toDomain(): Document {
    return Document(
        name = name,
        path = path,
    )
}

fun TrainingDTO.toDomain(): Training {
    return Training(
        course = course,
        name = name,
        time = time,
    )
}

fun EmergencyContactInformationDTO.toDomain(): EmergencyContactInformation {
    return EmergencyContactInformation(
        name = name,
        phone = phone,
        relation = relation,
    )
}

fun ExperienceDTO.toDomain(): Experience {
    return Experience(
        name = name,
        time = time,
    )
}

fun EducationInformationDTO.toDomain(): EducationInformation {
    return EducationInformation(
        name = name,
        time = time,
    )
}

fun FamilyInformationDTO.toDomain(): FamilyInformation {
    return FamilyInformation(
        father = father,
        grandfather = grandfather,
        mother = mother,
        spouse = spouse,
    )
}

fun PersonalInformationDTO.toDomain(): PersonalInformation {
    return PersonalInformation(
        citizenship_no = citizenship_no,
        email = email,
        marital_status = marital_status,
        nationality = nationality,
        permanent_address = permanent_address,
        phone = phone,
        temporary_address = temporary_address,
    )
}

fun MiscDTO.toDomain(): Misc {
    return Misc(
        bank_information = bank_information?.toDomain(),
        biometric_information = biometric_information?.map { it.toDomain() },
        retirement_fund = retirement_fund?.toDomain(),
    )
}

fun BankInformationDTO.toDomain(): BankInformation {
    return BankInformation(
        account_number = account_number,
        bank = bank,
        outsource_company_name = outsource_company_name,
    )
}

fun BiometricInformationDTO.toDomain(): BiometricInformation {
    return BiometricInformation(
        branch = branch,
        device = device,
        updated_at = updated_at,
    )
}

fun RetirementFundDTO.toDomain(): RetirementFund {
    return RetirementFund(
        cit_number = cit_number,
        retirement_fund_name = retirement_fund_name,
        retirement_fund_nuumber = retirement_fund_nuumber,
    )
}


