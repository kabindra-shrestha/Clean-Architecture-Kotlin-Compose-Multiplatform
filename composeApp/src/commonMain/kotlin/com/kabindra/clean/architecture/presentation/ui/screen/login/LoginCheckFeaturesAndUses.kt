package com.kabindra.clean.architecture.presentation.ui.screen.login

import com.kabindra.clean.architecture.domain.entity.Features
import com.kabindra.clean.architecture.domain.entity.FeaturesUsed

fun checkOtpFeatures(
    features: Features,
    featuresUsed: FeaturesUsed,
    onNavigateLoginVerifyOTP: () -> Unit,
    onNavigateMPINSet: () -> Unit,
    onNavigateMPINVerify: () -> Unit,
    onNavigateDashboard: () -> Unit
) {
    when {
        features.otp == null || !features.otp -> checkMPinFeatures(
            features,
            featuresUsed,
            onNavigateMPINSet = { onNavigateMPINSet() },
            onNavigateMPINVerify = { onNavigateMPINVerify() },
            onNavigateDashboard = { onNavigateDashboard() }
        )

        else ->
            checkOtpUses(
                features,
                featuresUsed,
                onNavigateLoginVerifyOTP = {
                    onNavigateLoginVerifyOTP()
                },
                onNavigateMPINSet = { onNavigateMPINSet() },
                onNavigateMPINVerify = { onNavigateMPINVerify() },
                onNavigateDashboard = { onNavigateDashboard() }
            )
    }
}

fun checkOtpUses(
    features: Features,
    featuresUsed: FeaturesUsed,
    onNavigateLoginVerifyOTP: () -> Unit,
    onNavigateMPINSet: () -> Unit,
    onNavigateMPINVerify: () -> Unit,
    onNavigateDashboard: () -> Unit
) {
    when {
        featuresUsed.otp == null -> checkMPinFeatures(
            features,
            featuresUsed,
            onNavigateMPINSet = { onNavigateMPINSet() },
            onNavigateMPINVerify = { onNavigateMPINVerify() },
            onNavigateDashboard = { onNavigateDashboard() }
        )

        !featuresUsed.otp -> onNavigateLoginVerifyOTP()
        else -> checkMPinFeatures(
            features,
            featuresUsed,
            onNavigateMPINSet = { onNavigateMPINSet() },
            onNavigateMPINVerify = { onNavigateMPINVerify() },
            onNavigateDashboard = { onNavigateDashboard() }
        )
    }
}

fun checkMPinFeatures(
    features: Features,
    featuresUsed: FeaturesUsed,
    onNavigateMPINSet: () -> Unit,
    onNavigateMPINVerify: () -> Unit,
    onNavigateDashboard: () -> Unit
) {
    when {
        features.mpin == null || !features.mpin -> onNavigateDashboard()
        else ->
            checkMPinUses(
                featuresUsed,
                onNavigateMPINSet = { onNavigateMPINSet() },
                onNavigateMPINVerify = { onNavigateMPINVerify() },
                onNavigateDashboard = { onNavigateDashboard() }
            )
    }
}

fun checkMPinUses(
    featuresUsed: FeaturesUsed,
    onNavigateMPINSet: () -> Unit,
    onNavigateMPINVerify: () -> Unit,
    onNavigateDashboard: () -> Unit
) {
    when {
        featuresUsed.mpin == null -> onNavigateDashboard()
        !featuresUsed.mpin -> onNavigateMPINSet()
        else -> onNavigateMPINVerify()
    }
}