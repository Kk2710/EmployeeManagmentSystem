package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.cas_applications

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.models.responses.casData
import com.android.employeemanagmentsystem.data.network.apis.CasApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.CasRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentCasApplicationsBinding
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.cas_applications.AppliedCasAdapter.AppliedCasAdapter
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.training_applications.AppliedTrainingsAdapter
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.training_applications.TrainingApplicationsFragmentDirections
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.apply_cas.CasProfessionalDetailDirections
import com.android.employeemanagmentsystem.utils.CAS_ALL_STATUS
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

private const val TAG = "CasApplicationsFra"

class CasApplications: Fragment(R.layout.fragment_cas_applications),
    AppliedCasAdapter.ApplicationClickListener {
    private var status_id = CAS_ALL_STATUS
    lateinit var applications: List<casData>

    lateinit var status: List<String>
    lateinit var paint: Paint

    private lateinit var binding: FragmentCasApplicationsBinding


    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private lateinit var casApi: CasApi
    private lateinit var casRepo: CasRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCasApplicationsBinding.bind(view)

        binding.apply {
            btnGeneratePdf.setOnClickListener {
                createTabularPdf()
            }
        }

        setSpinner()
    }

    override fun onApplicationClicked(appybhai: casData) {
        CasViewFormDirections.actionNavCasApplicationsToNavCasViewForm().apply {
            findNavController().navigate(this)
        }
    }

    private fun createTabularPdf() {

        GlobalScope.launch {

            withContext(Dispatchers.IO) {

                withContext(Dispatchers.Main) {
                    binding.progressBarCasApplication.isVisible = true
                }

                val myPdfDocument = PdfDocument()
                paint = Paint().apply {
                    textSize = 55F
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                    color = ContextCompat.getColor(requireContext(), R.color.black)
                }

                //setting data in pdf page
                var pageNumber = 1
                var i = 0
                val rows = 24
                while (i < applications.size) {

                    if (i + rows > applications.size) {
                        setPage(myPdfDocument, pageNumber, i, applications.size - 1);
                        pageNumber += 1
                        i += rows
                        continue
                    }

                    setPage(myPdfDocument, pageNumber, i, i + rows);
                    pageNumber += 1
                    i += rows;

                }

                storePdfOffline(myPdfDocument)

                withContext(Dispatchers.Main) {
                    binding.progressBarCasApplication.isVisible = false
                }
            }


        }
    }

    private fun setSpinner() {
        binding.apply {

            status = listOf(
                "All Status",
                "Forwarded",
                "Reverted"
            )

            val ad: ArrayAdapter<*> = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                status
            )


            ad.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )


            spinnerStatus.adapter = ad

            spinnerStatus.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        status_id = p2
                        Log.e(TAG, "onItemSelected: " + status_id)
                        getTrainings()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }
        }
    }

    private fun getTrainings() {


        val empDao = AppDatabase.invoke(requireContext()).getEmployeeDao()

        binding.progressBarCasApplication.isVisible = true
        GlobalScope.launch {
            val employee = authRepository.getEmployee(empDao)

            val cApplication = casRepo.getApplicationToAdmin(
                employee.role_id.toInt(),
                employee.sevarth_id,
                status_id.toString(),
                casApi
            )

            withContext(Dispatchers.Main) {

                binding.progressBarCasApplication.isVisible = true

                binding.tvNotAvailable.isVisible = applications.isEmpty()

                binding.recyclerView.apply {
                    adapter = AppliedCasAdapter(applications, this@CasApplications)

                    layoutManager = LinearLayoutManager(requireContext())
                }


            }

        }


    }

    private fun setPage(myPdfDocument: PdfDocument, pageNumber: Int, low: Int, high: Int) {

        var myPageInfo: PdfDocument.PageInfo =
            PdfDocument.PageInfo.Builder(2000, 3010, pageNumber).create()
        var myPage = myPdfDocument.startPage(myPageInfo)
        var canvas = myPage.canvas

        canvas.drawText("Government Polytechnic Amravati", 800F, 100F, paint)

        paint.apply {
            textSize = 35f
            style = Paint.Style.STROKE
            strokeWidth = 2F
        }

        canvas.drawRect(20f, 180f, 1980f, 260f, paint)

        canvas.drawText("Sevarth-Id", 23f, 230f, paint)
        canvas.drawText("Training Name", 260f, 230f, paint)
        canvas.drawText("Duration ", 650f, 230f, paint)
        canvas.drawText("Start Date ", 820f, 230f, paint)
        canvas.drawText("End Date ", 1050f, 230f, paint)
        canvas.drawText("Status", 1350f, 230f, paint)
        canvas.drawText("Organized By", 1600f, 230f, paint)

        var x = 20f
        var y = 280f
        var offset_y = 100f

        for (i in low..high) {

            y += offset_y
            x = 50f


            canvas.drawText(applications[i].sevarth_id, x, y, paint)
            x += 230f
            // canvas.drawText(applications[i].name, x, y, paint)
            // x += 400f
            canvas.drawText(applications[i].duration, x, y, paint)
            x += 150f
            canvas.drawText(applications[i].start_date, x, y, paint)
            x += 230f
            canvas.drawText(applications[i].end_date, x, y, paint)
            x += 200f
            //  canvas.drawText(applications[i].myStatus, x, y, paint)
            // x += 350f
            // canvas.drawText(applications[i].organized_by, x, y, paint)


        }


        myPdfDocument.finishPage(myPage)
    }


    private suspend fun storePdfOffline(myPdfDocument: PdfDocument) {

        withContext(Dispatchers.IO) {
            val name = status[status_id] + ".pdf"
            val file =
                File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/${name}")

            try {

                myPdfDocument.writeTo(FileOutputStream(file))
                withContext(Dispatchers.Main) {
                    requireContext().toast("Document Created Successfully")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    requireContext().toast(e.toString())
                    e.printStackTrace()
                }
            } finally {
                myPdfDocument.close()
            }
        }
    }

}
