package com.android.employeemanagmentsystem.data.repository

import com.android.employeemanagmentsystem.data.models.responses.Application
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.models.responses.EmployeeDetails
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.network.SafeApiRequest
import com.android.employeemanagmentsystem.data.network.apis.IOApplicationApi
import com.android.employeemanagmentsystem.utils.toMultipartReq
import okhttp3.MultipartBody

class IOApplicationRepository : SafeApiRequest() {

    //converting strings to multipart response
    suspend fun applyIOApplication(
        sevarth_id: String,
        title: String,
        desc: String,
        date: String,
        org_id: String,
        department_id: String,
        application_type: String,
        from_department: String,
        role_id: String,
        applyPdf: MultipartBody.Part,
        iOApplicationApi: IOApplicationApi
    ): StatusResponse = apiRequest {
        iOApplicationApi.applyIOApplication(
            sevarth_id.toMultipartReq(),
            title.toMultipartReq(),
            desc.toMultipartReq(),
            date.toMultipartReq(),
            org_id.toMultipartReq(),
            department_id.toMultipartReq(),
            application_type.toMultipartReq(),
            from_department.toMultipartReq(),
            role_id.toMultipartReq(),
            applyPdf,


        )
    }

    suspend fun getEmployeeDetails(sevarth_id: String, iOApplicationApi: IOApplicationApi): EmployeeDetails{
        return apiRequest { iOApplicationApi.getEmployeeDetails(sevarth_id) }
    }

    suspend fun getDepartments(iOApplicationApi: IOApplicationApi) = apiRequest { iOApplicationApi.getDepartments() }


    suspend fun getApplication(
        application_id: String,
        iOApplicationApi: IOApplicationApi
    ) = apiRequest {
        iOApplicationApi.getApplication(application_id)
    }

    suspend fun updateStatusId(
        application_id: String,
        status_id: String,
        remark: String,
        iOApplicationApi: IOApplicationApi
    ): StatusResponse = apiRequest {
        iOApplicationApi.updateStatusId(application_id, status_id, remark)
    }


    suspend fun getAppliedApplications(
        sevarth_id: String,
        role_id: String,
        iOApplicationApi: IOApplicationApi
    ):List<Application> = apiRequest {
        iOApplicationApi.getAppliedApplications(sevarth_id, role_id)
    }

}