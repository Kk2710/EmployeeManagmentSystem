package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.apply_cas


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.network.apis.CasApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.CasRepository
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentProfessionalDetailsBinding
//import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.completed_trainings.TAG
import com.android.employeemanagmentsystem.utils.getOriginalFileName
import com.android.employeemanagmentsystem.utils.handleException
import com.android.employeemanagmentsystem.utils.toPdfRequestBody
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDateTime


class CasProfessionalDetail : Fragment(R.layout.fragment_professional_details) {


    private val TAG = "CompletedTrainingsFragment"
    private val PICK_PDF_REQUEST = 112
    private var isPdfSelected = false
    private lateinit var byteArray: ByteArray
    private var selectedType = "-1"
    private var parentLinearLayout: LinearLayout? = null
    private var parentLinearLayout2: LinearLayout? = null


    private lateinit var binding: FragmentProfessionalDetailsBinding

    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private lateinit var casApi: CasApi
    private lateinit var casRepo: CasRepository

    override fun onViewCreated(view: View, savedInstance: Bundle?) {
        super.onViewCreated(view, savedInstance)


        binding.idBtnNext.setOnClickListener {
            val probession = binding.idEdtdateOfProbession.text.toString()
            val grno = binding.idEdtGRNO.text.toString()
            val institute_name = binding.idEdtIName.text.toString()
            val service_start_date = binding.idEdtServiceStartDate.text.toString()
            val service_end_date = binding.idEdtServiceEndDate.text.toString()
            val training_name = binding.idTrainingName.text.toString()
            val sponsored_by = binding.idSponseredby.text.toString()
            val training_type = binding.idTrainingType.text.toString()
            val training_duration = binding.idEdtDuration.text.toString()
            val training_start_date = binding.idEdtTrainingStart.text.toString()
            val training_end_date = binding.idEdtServiceEndDate.text.toString()


            when {

                probession.isBlank() -> requireContext().toast("Please Enter Probession period in years")
                grno.isBlank() -> requireContext().toast("Please Enter GR number")
                institute_name.isBlank() -> requireContext().toast("Please Enter Institute Name")
                service_start_date.isBlank() -> requireContext().toast("Please Select Service start date")
                service_end_date.isBlank() -> requireContext().toast("Please Select Service End Date")
                training_name.isBlank() -> requireContext().toast("Please Select Training Name")
                sponsored_by.isBlank() -> requireContext().toast("Please Select Sponsor Name")
                training_type.isBlank() -> requireContext().toast("Please Select Training Type")
                training_duration.isBlank() -> requireContext().toast("Please Select Training Duration")
                training_start_date.isBlank() -> requireContext().toast("Please Select Training Start Date")
                training_end_date.isBlank() -> requireContext().toast("Please Select Training End Date")

            else -> {
                    GlobalScope.launch {

                        try {

                            withContext(Dispatchers.Main) {
                                binding.progressBarCasProfessional.isVisible = true
                            }

                            val employee = authRepository.getEmployee(employeeDao)

                            val casResponse = casRepo.add_professionalDetails(
                                sevarth_id = employee.sevarth_id,
                                probession = probession,
                                grno = grno,
                                institute_name = institute_name,
                                ServiceStartDate = service_start_date,
                                ServiceEndDate = service_end_date,
                                training_name = training_name,
                                sponsored_by = sponsored_by,
                                training_type = training_type,
                                training_duration = training_duration,
                                training_start_date = training_start_date,
                                training_end_date = training_end_date,
                                casApi = casApi
                            )


                            withContext(Dispatchers.Main) {
                                binding.progressBarCasProfessional.isVisible = false
                                requireContext().toast("in context dispatchers" + casResponse.status)
                            }

                            delay((1 * 1000).toLong())

                            withContext(Dispatchers.Main) {
                                findNavController().popBackStack(R.id.nav_professional_details, true)
                                findNavController().navigate(R.id.nav_confidential_report)
                            }

                        } catch (e: Exception) {

                            withContext(Dispatchers.Main) {
                                binding.progressBarCasProfessional.isVisible = false
                            }

                            requireContext().handleException(e)

                        } } }
            } }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun convertBytesToMultipart(): MultipartBody.Part {

        val localDateTime = LocalDateTime.now()
        val fileName = "${localDateTime.hour}${localDateTime.minute}${localDateTime.second}.pdf"

        val filePart =
            MultipartBody.Part.createFormData(
                "training_application",
                fileName,
                byteArray.toPdfRequestBody()
            )

        return filePart


    }
}
