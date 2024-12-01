package com.example.listdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(val index: Int,
    val title: String,
                  val date: String,
                  val description: String): Parcelable
