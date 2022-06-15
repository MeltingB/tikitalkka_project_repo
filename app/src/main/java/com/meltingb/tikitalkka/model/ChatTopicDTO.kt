package com.meltingb.tikitalkka.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatTopicDTO(
    val topicCode: String = "",
    val chatContent: String = "",
    val createdDT: Timestamp = Timestamp.now()
): Parcelable
