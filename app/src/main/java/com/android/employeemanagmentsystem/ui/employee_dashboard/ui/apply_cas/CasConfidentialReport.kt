package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.apply_cas

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.network.apis.CasApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.CasRepository
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentConfidentialReportBinding
import com.android.employeemanagmentsystem.utils.handleException
import com.android.employeemanagmentsystem.utils.toPdfRequestBody
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.android.synthetic.main.fragment_confidential_report.*
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import java.time.LocalDateTime


@SuppressLint("ResourceType")
class CasConfidentialReport : Fragment(R.id.fragment_confidential_report){
    lateinit var button: Button
    private val pickImage = 100
    private var imageUri: Uri? = null

    private val PICK_PDF_REQUEST = 112
    private var isPdfSelected = false
    private lateinit var byteArray: ByteArray

    private lateinit var binding: FragmentConfidentialReportBinding

    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private lateinit var casApi: CasApi
    private lateinit var casRepo: CasRepository


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstance: Bundle?){
        super.onViewCreated(view, savedInstance)
        binding = FragmentConfidentialReportBinding.bind(view)

        binding.apply {
            idBtnSubmit.setOnClickListener {

                val institute_name = idEdtInstitude1.text.toString()
                val crstart_date = idEdtDate1.text.toString()
                val crend_date = idEdtDate2.text.toString()
                val grade = idGrade.text.toString()
                when {
                    institute_name.isBlank() -> requireContext().toast("Enter Institute Name")
                    crstart_date.isBlank() -> requireContext().toast("Enter Institute Name")
                    crend_date.isBlank() -> requireContext().toast("Enter Institute Name")
                    grade.isBlank() -> requireContext().toast("Enter Institute Name")
                    !isPdfSelected -> requireContext().toast("Please select PDF to Upload")

                    else -> {
                        GlobalScope.launch {
                            try {

                                withContext(Dispatchers.Main) {
                                    binding.progressBarCasCr.isVisible = true
                                }
                                val employee = authRepository.getEmployee(employeeDao)

                                val casResponse = casRepo.add_crDetails(
                                    institute_name = institute_name,
                                    crStartDate = crstart_date,
                                    crEndDate = crend_date,
                                    grade = grade,
                                    selectPDF = convertBytesToMultipart(),
                                    casApi = casApi
                                )

                                withContext(Dispatchers.Main) {
                                    binding.progressBarCasCr.isVisible = false
                                    requireContext().toast("in context dispatchers" + casResponse.status)
                                }

                                delay((1 * 1000).toLong())

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack(
                                        R.id.nav_professional_details,
                                        true
                                    )
                                    findNavController().navigate(R.id.nav_confidential_report)
                                }

                            } catch (e: Exception) {

                                withContext(Dispatchers.Main) {
                                    binding.progressBarCasCr.isVisible = false
                                }

                                requireContext().handleException(e)

                            }
                        }
                    }

                }
            }
            idBtn1.setOnClickListener {
                val intent = Intent()
                intent.type = "application/pdf"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST)
            }
        }
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




