package com.android.employeemanagmentsystem.utils


//Base URL
const val BASE_URL = "http://192.168.248.47" + "/ems/";



const val TRAINING_ALL_STATUS = 0
const val TRAINING_APPLIED_TO_HOD = 1
const val TRAINING_APPLIED_TO_PRINCIPLE = 2
const val TRAINING_APPROVED_BY_HOD = 3
const val TRAINING_DECLINE_BY_HOD = 4
const val TRAINING_APPROVED_BY_PRINCIPAL = 5
const val TRAINING_DECLINED_BY_PRINCIPLE = 6
const val TRAINING_COMPLETED = 7

val APPLICATION_TYPE_INWARD = 1
val APPLICATION_TYPE_OUTWARD = 2

const val IO_APPLICATION_APPLIED = 1;
const val IO_APPROVED_BY_HOD = 2
const val IO_APPROVED_BY_REGISTRAR = 3
const val IO_APPROVED_BY_PRINCIPLE = 4
const val IO_Declined_BY_Hod = 5
const val IO_Declined_BY_Registrar = 6
const val IO_Declined_BY_Principle = 7
const val IO_APPLIED_BY_HOD = 8
const val IO_APPLIED_BY_Principle = 9
const val IO_APPLIED_BY_REGISTRAR = 10

const val ROLE_EMPLOYEE = 1
const val ROLE_HOD = 2
const val ROLE_Principle = 3
const val ROLE_Registrar = 4
const val ROLE_Joint_Director = 5
const val ROLE_Director = 6


//cas application status constants
const val CAS_ALL_STATUS = 0
const val CAS_FORWARDED_TO_PRINCIPAL = 1
const val CAS_FORWARDED_TO_JOINT_DIRECTOR = 2
const val CAS_FORWARDED_TO_DIRECTOR = 3
const val CAS_APPROVED = 4
const val CAS_REVERTED_BY_PRINCIPAL = 5
const val CAS_REVERTED_BY_JOINT_DIRECTOR = 6
const val CAS_REVERTED_BY_DIRECTOR = 7

//error in approve button click in io application using hod