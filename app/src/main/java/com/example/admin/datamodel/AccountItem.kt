package com.example.admin.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class AccountItem(
    val id: String,
    val name: String,
    val owner: String,
    val imageResId: Int
) : Parcelable