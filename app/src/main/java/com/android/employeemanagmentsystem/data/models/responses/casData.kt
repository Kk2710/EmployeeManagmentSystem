package com.android.employeemanagmentsystem.data.models.responses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.employeemanagmentsystem.utils.APPLICATION_TYPE_INWARD
import com.android.employeemanagmentsystem.utils.getIoApplicationStatusById
import com.android.employeemanagmentsystem.utils.then
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "Cas_personal_info")
data class casData (
    val surname: String,
    val middlename: String,
    val firstname: String,
    val email: String,
    val phone: String,
    val date_of_birth : Date,
    val age: Int,
    val gender: String,
    val date_of_joining: Date,
    val sevarth_id: String,
    val branch: String,
    val address: String,
    val state: String,
    val city: String,
    val taluka: String,
    val village: String,
    val pincode: String,
    val probession: String,
    val grno: String,
    val institute_name: String,
    val serviceStartDate: String,
    val serviceEndDate: String,
    val trainingName: String,
    val sponsored_by: String,
    val type: String,
    val duration: String,
    val start_date: String,
    val end_date: String,
    val application_status_id: Int,

    @PrimaryKey
    val ID: Int = 0

    )

