package com.kabindra.clean.architecture.presentation.ui.items

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kabindra.clean.architecture.domain.entity.EducationInformation
import com.kabindra.clean.architecture.domain.entity.EmergencyContactInformation
import com.kabindra.clean.architecture.domain.entity.Experience
import com.kabindra.clean.architecture.domain.entity.FamilyInformation
import com.kabindra.clean.architecture.domain.entity.GeneralInformation
import com.kabindra.clean.architecture.domain.entity.PersonalInformation
import com.kabindra.clean.architecture.presentation.ui.component.CardBorderInside
import com.kabindra.clean.architecture.presentation.ui.component.ImageHandlerURL
import com.kabindra.clean.architecture.presentation.ui.component.TextComponent
import com.kabindra.clean.architecture.presentation.ui.component.TextSize
import com.kabindra.clean.architecture.presentation.ui.component.timeline.TimelineNode
import com.kabindra.clean.architecture.presentation.ui.component.timeline.defaults.CircleParametersDefaults
import com.kabindra.clean.architecture.presentation.ui.component.timeline.getLineBrush
import com.kabindra.clean.architecture.presentation.ui.component.timeline.mapToTimelineNodePosition
import com.kabindra.clean.architecture.presentation.ui.screen.profile.EducationData
import com.kabindra.clean.architecture.presentation.ui.screen.profile.ExperienceData
import com.kabindra.clean.architecture.presentation.ui.theme.AppTheme
import com.kabindra.clean.architecture.presentation.ui.theme.imageBorder
import com.kabindra.clean.architecture.presentation.ui.theme.profileTimelineBorder

@Composable
fun ItemProfileInformation(generalInformation: GeneralInformation?) {
    CardBorderInside(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(AppTheme.dimens.paddingLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    ImageHandlerURL(
                        modifier = Modifier
                            .size(120.dp)
                            .border(
                                2.dp,
                                imageBorder,
                                CircleShape
                            )
                            .aspectRatio(1f / 1f),
                        image = generalInformation?.profile_img.takeIf { !it.isNullOrEmpty() }
                            ?: "",
                        contentDescription = "Profile Icon",
                        contentScale = ContentScale.Crop,
                        placeholder = Icons.Default.Person,
                        circular = true
                    )
                }
                TextComponent(
                    text = "${generalInformation?.employee_code ?: ""})",
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth().padding(bottom = AppTheme.dimens.paddingNormal),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /* Row(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(14.dp)
                 ) {
                     Column(
                         modifier = Modifier
                             .weight(1f)
                             .padding(end = 8.dp),
                         horizontalAlignment = Alignment.Start
                     ) {
                         TextComponent(text = generalInformation?.name.takeIf { !it.isNullOrEmpty() }
                             ?: "N/A", fontWeight = FontWeight.Bold)
                         Spacer(modifier = Modifier.height(4.dp))
                         TextComponent(text = generalInformation?.designation.takeIf { !it.isNullOrEmpty() }
                             ?: "N/A", maxLines = 2)
                     }
                     Column(
                         modifier = Modifier
                             .weight(1f)
                             .padding(start = 8.dp),
                         horizontalAlignment = Alignment.Start
                     ) {
                         TextComponent(text = generalInformation?.employee_code.takeIf { !it.isNullOrEmpty() }
                             ?: "N/A", fontWeight = FontWeight.Bold)
                         Spacer(modifier = Modifier.height(4.dp))
                         TextComponent(text = generalInformation?.address.takeIf { !it.isNullOrEmpty() }
                             ?: "N/A")
                         Spacer(modifier = Modifier.height(4.dp))
                         TextComponent(text = generalInformation?.company.takeIf { !it.isNullOrEmpty() }
                             ?: "N/A")
                     }
                 }*/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp, bottom = 5.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(end = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextComponent(
                            text = "Name",
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Branch",
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Department",
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Unit",
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Phone",
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Email"
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.7f)
                            .padding(start = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextComponent(text = generalInformation?.name.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = generalInformation?.branch.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = generalInformation?.department.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = generalInformation?.unit.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = generalInformation?.phone.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = generalInformation?.email.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.dimens.paddingNormal,
                            vertical = AppTheme.dimens.paddingNormal
                        ),
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    TextComponent(
                        text = generalInformation?.company.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextComponent(
                        text = "The card certifies that the holder is an employee of ${generalInformation?.company}",
                        size = TextSize.Medium, maxLines = 2
                    )
                }
            }
        }

    }
}

@Composable
fun ItemProfilePersonalInformation(
    personalInformation: PersonalInformation?,
    familyInformation: FamilyInformation?,
    emergencyContactInformation: EmergencyContactInformation?,
    experience: List<Experience>?,
    educationInformation: List<EducationInformation>?
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        CardBorderInside(
            modifier = Modifier.fillMaxWidth(),
            sides = listOf(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(AppTheme.dimens.paddingNormal)
            ) {
                TextComponent(
                    modifier = Modifier,
                    text = "Personal Information",
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(top = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.7f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextComponent(
                            text = "Phone"
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Email"
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Marital Status"
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Nationality"
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Citizenship No."
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Permanent Address"
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(
                            text = "Temporary Address "
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextComponent(text = personalInformation?.phone.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = personalInformation?.email.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = personalInformation?.marital_status.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = personalInformation?.nationality.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = personalInformation?.citizenship_no.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = personalInformation?.permanent_address.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        TextComponent(text = personalInformation?.temporary_address.takeIf { !it.isNullOrEmpty() }
                            ?: "N/A",
                            fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        CardBorderInside(
            modifier = Modifier.fillMaxWidth(),
            sides = listOf(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(AppTheme.dimens.paddingNormal)
            ) {
                TextComponent(
                    modifier = Modifier,
                    text = "Family Information",
                    fontWeight = FontWeight.Bold
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(0.7f)
                                .padding(end = 8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextComponent(
                                text = "Father"
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            TextComponent(
                                text = "Mother"
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            TextComponent(
                                text = "GrandFather"
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextComponent(text = familyInformation?.father.takeIf { !it.isNullOrEmpty() }
                                ?: "N/A",
                                fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            TextComponent(text = familyInformation?.mother.takeIf { !it.isNullOrEmpty() }
                                ?: "N/A",
                                fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            TextComponent(text = familyInformation?.grandfather.takeIf { !it.isNullOrEmpty() }
                                ?: "N/A",
                                fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        CardBorderInside(
            modifier = Modifier.fillMaxWidth(),
            sides = listOf(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(AppTheme.dimens.paddingNormal)
            ) {
                TextComponent(
                    modifier = Modifier,
                    text = "Emergency Contact Information",
                    fontWeight = FontWeight.Bold
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(0.7f)
                                .padding(end = 8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextComponent(
                                text = "Name"
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            TextComponent(
                                text = "Phone"
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            TextComponent(
                                text = "Relation"
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextComponent(text = emergencyContactInformation?.name.takeIf { !it.isNullOrEmpty() }
                                ?: "N/A",
                                fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            TextComponent(text = emergencyContactInformation?.phone.takeIf { !it.isNullOrEmpty() }
                                ?: "N/A",
                                fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            TextComponent(text = emergencyContactInformation?.relation.takeIf { !it.isNullOrEmpty() }
                                ?: "N/A",
                                fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (!experience.isNullOrEmpty()) {

            CardBorderInside(
                modifier = Modifier.fillMaxWidth(),
                sides = listOf()
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth().padding(AppTheme.dimens.paddingNormal)
                ) {
                    TextComponent(
                        modifier = Modifier,
                        text = "Experience",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        experience.forEachIndexed { index, item ->
                            TimelineNode(
                                position = mapToTimelineNodePosition(index, experience.size),
                                circleParameters = CircleParametersDefaults.circleParameters(
                                    backgroundColor = profileTimelineBorder
                                ),
                                lineParameters = getLineBrush(lastIndex = index != experience.lastIndex)
                            ) { modifier ->
                                ExperienceData(modifier, item)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (!educationInformation.isNullOrEmpty()) {

            CardBorderInside(modifier = Modifier.fillMaxWidth(), sides = listOf()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(AppTheme.dimens.paddingNormal)
                ) {
                    TextComponent(
                        modifier = Modifier,
                        text = "Education Information",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        educationInformation.forEachIndexed { index, item ->
                            TimelineNode(
                                position = mapToTimelineNodePosition(
                                    index,
                                    educationInformation.size
                                ),
                                circleParameters = CircleParametersDefaults.circleParameters(
                                    backgroundColor = profileTimelineBorder
                                ),
                                lineParameters = getLineBrush(lastIndex = index != educationInformation.lastIndex)
                            ) { modifier ->
                                EducationData(modifier, item)
                            }
                        }
                    }
                }
            }
        }
    }
}