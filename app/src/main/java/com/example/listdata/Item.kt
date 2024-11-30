package com.example.listdata

import kotlinx.serialization.Serializable

@Serializable
data class DataItem(val index: Int,
                    val title: String,
                    val date: String,
                    val description: String)