package com.projectigti.irontime.data.db.model

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "students")
data class SubscriberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val phone: String,
    @Nullable
    val classes: Int? = 0,
    @Nullable
    val checkins: List<Date>? = listOf()
) : Parcelable