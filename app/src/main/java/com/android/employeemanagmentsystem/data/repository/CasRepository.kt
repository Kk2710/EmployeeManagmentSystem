package com.android.employeemanagmentsystem.data.repository

import com.android.employeemanagmentsystem.data.models.responses.casData
import com.android.employeemanagmentsystem.data.network.SafeApiRequest
import com.android.employeemanagmentsystem.data.network.apis.CasApi
import com.android.employeemanagmentsystem.utils.toMultipartReq

class CasRepository: SafeApiRequest() {

    suspend fun add_personalDetails(
        surname: String,
        firstname: String,
        middlename: String,
        email: String,
        phone: String,
        date_of_birth: String,
        age: String,
        gender: String,
        date_of_joining: String,
        sevarth_id: String,
        branch: String,
        address: String,
        state: String,
        city: String,
        village: String,
        pincode: String,
        CasApi: CasApi
    ) = apiRequest {
        CasApi.add_personalDetails(
            surname.toMultipartReq(),
            firstname.toMultipartReq(),
            middlename.toMultipartReq(),
            email.toMultipartReq(),
            phone.toMultipartReq(),
            date_of_birth.toMultipartReq(),
            age.toMultipartReq(),
            gender.toMultipartReq(),
            date_of_joining.toMultipartReq(),
            sevarth_id.toMultipartReq(),
            branch.toMultipartReq(),
            address.toMultipartReq(),
            state.toMultipartReq(),
            city.toMultipartReq(),
            village.toMultipartReq(),
            pincode.toMultipartReq()
        )
    }

    suspend fun add_professionalDetails(
        sevarth_id: String,
        probession : String,
        grno: String,
        institute_name: String,
        ServiceStartDate: String,
        ServiceEndDate: String,
        training_name: String,
        sponsored_by: String,
        training_type: String,
        training_duration: String,
        training_start_date: String,
        training_end_date: String,
        casApi: CasApi
    ) = apiRequest{
        casApi.add_professional_details(
        probession.toMultipartReq(),
        grno.toMultipartReq(),
        institute_name.toMultipartReq(),
        ServiceStartDate.toMultipartReq(),
        ServiceEndDate.toMultipartReq(),
        training_name.toMultipartReq(),
        sponsored_by.toMultipartReq(),
        training_type.toMultipartReq(),
        training_duration.toMultipartReq(),
        training_start_date.toMultipartReq(),
        training_end_date.toMultipartReq()
    )}

    suspend fun add_crDetails(
        institute_name: String,
        crStartDate: String,
        crEndDate: String,
        grade: String,
        casApi: CasApi
    ) = apiRequest{
        casApi.add_crDetails(
            institute_name.toMultipartReq(),
            crStartDate.toMultipartReq(),
            crEndDate.toMultipartReq(),
            grade.toMultipartReq()
        )
    }


    suspend fun getApplicationToAdmin(
        roleId: Int,
        sevarth_id: String,
        status_id: String,
        casApi: CasApi
    ): List<casData> {
        if (roleId == 3) return apiRequest { casApi.get_applications(sevarth_id,status_id) }
        else return apiRequest { casApi.get_applications(sevarth_id, status_id) }
    }

    suspend fun updateApplicationStatus(
        applicationId: String,
        application_status_id: String,
        casApi: CasApi
    ) = apiRequest { casApi.updateCasStatus(applicationId, application_status_id) }


}