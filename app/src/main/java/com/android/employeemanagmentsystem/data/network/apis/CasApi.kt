package com.android.employeemanagmentsystem.data.network.apis


import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.models.responses.casData
import com.android.employeemanagmentsystem.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.MultipartBody

import okhttp3.RequestBody

import retrofit2.http.*


interface CasApi {

    //api request for adding personal details
    @Multipart
    @POST("Cas/save_personal_details")
    suspend fun add_personalDetails(
        @Part("surname") surname: RequestBody,
        @Part("firstname") firstname: RequestBody,
        @Part("middlename") middlename: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("date_of_birth") date_of_birth: RequestBody,
        @Part("age") age: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("date_of_joining") date_of_joining: RequestBody,
        @Part("EmployeeCode") employee_code: RequestBody,
        @Part("branch") branch: RequestBody,
        @Part("address") address: RequestBody,
        @Part("state") state: RequestBody,
        @Part("city") city: RequestBody,
        @Part("taluka") taluka: RequestBody,
        @Part("village") village: RequestBody
    ): Response<StatusResponse>

    //api request for apply to training
    @Multipart
    @POST("Cas/save_professional_details")
    suspend fun add_professional_details(
        @Part("probession") probession: RequestBody,
        @Part("grno") grno: RequestBody,
        @Part("insititute_name") insitute_name: RequestBody,
        @Part("ServiceStartDate") service_start_date: RequestBody,
        @Part("ServiceEndDate") service_end_date: RequestBody,
        @Part("training_name") training_name: RequestBody,
        @Part("training_type") training_type: RequestBody,
        @Part("training_duration") training_duration: RequestBody,
        @Part("training_start_date") training_start_date: RequestBody,
        @Part("training_end_date") training_end_date: RequestBody,
        toMultipartReq: RequestBody
    ): Response<StatusResponse>


    @GET("Cas/save_cr_details")
    suspend fun add_crDetails(
        @Part("institute_name") insitute_name: RequestBody,
        @Part("crStartDate") crStartDate: RequestBody,
        @Part("crEndDate") crEndDate: RequestBody,
        @Part("grade") grade: RequestBody
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("Cas/update_application_status")
    suspend fun updateCasStatus(
        @Field("app_id") app_id: String,
        @Field("application_status_id") trainingStatusId: String,
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("Cas/get_applications")
    suspend fun get_applications(
        @Field("sevarth_id") sevarth_id: String,
        status_id: String
    ): Response<List<casData>>

    @FormUrlEncoded
    @POST("Cas/upload_signature_photo")
    suspend fun upload_signature_and_photo(
        @Field("application_id") application_id: String,
        @Field("application_status_id") application_status_id: String
    ): Response<StatusResponse>


//to chechk the status
    @FormUrlEncoded
    @POST("Cas/get_trainings_by_principal")
    suspend fun getApplicationByPrincipal(
        @Field("principal_id") princial_id: String,
        @Field("status_id") statusId: String
    ): Response<List<casData>>

    //api request for apply to training


    companion object{
        operator fun invoke(
        ) : CasApi {
            return Retrofit.Builder()
                .client(provideHttpClint(provideInterceptor()))
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CasApi::class.java)
        }

        fun provideInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        fun provideHttpClint(
            interceptor: HttpLoggingInterceptor
        ): OkHttpClient =
            OkHttpClient.Builder().also {
                it.addInterceptor(interceptor)
            }.build()
    }

}

