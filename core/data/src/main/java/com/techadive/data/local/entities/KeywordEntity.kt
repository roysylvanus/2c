package com.techadive.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techadive.common.models.Keyword

@Entity("search_keywords")
data class KeywordEntity(
    @PrimaryKey
    val keyword: String,
    val timeStamp: Long,
)

fun KeywordEntity.convertToKeyword() =
    Keyword(
        name = keyword,
        timestamp = timeStamp
    )
