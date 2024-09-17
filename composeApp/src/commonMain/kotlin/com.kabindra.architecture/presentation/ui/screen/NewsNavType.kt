package com.kabindra.architecture.presentation.ui.screen

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import com.kabindra.architecture.domain.entity.Article
import com.kabindra.architecture.utils.decode
import com.kabindra.architecture.utils.encode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object NewsNavType {

    val ArticleType = object : NavType<Article>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Article? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Article {
            return Json.decodeFromString(decode(value))
        }

        override fun serializeAsValue(value: Article): String {
            return encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Article) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

}