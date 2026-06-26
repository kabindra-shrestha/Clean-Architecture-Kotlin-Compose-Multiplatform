package com.kabindra.clean.architecture.utils.enums

import com.kabindra.clean.architecture.utils.subscribeToTopic
import com.kabindra.clean.architecture.utils.unsubscribeFromTopic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

fun subscribeToTopics(topics: List<String>) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            topics.forEach { topic ->
                subscribeToTopic(topic)
            }
        } catch (e: Exception) {
        }
    }
}

fun unsubscribeFromTopics(topics: List<String>) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            topics.forEach { topic ->
                unsubscribeFromTopic(topic)
            }
        } catch (e: Exception) {
        }
    }
}