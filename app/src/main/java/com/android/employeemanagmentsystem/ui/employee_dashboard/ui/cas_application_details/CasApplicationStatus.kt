package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.cas_application_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Application
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.network.apis.CasApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.CasRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentApplicationStatusBinding


import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("ResourceType")
class CasApplicationStatus : Fragment(R.layout.fragment_application_status) {
    private lateinit var binding: FragmentApplicationStatusBinding

    private lateinit var application: Application

    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private lateinit var casRepo: CasRepository
    private lateinit var casApplicationApi: CasApi

    private lateinit var curr_user: Employee

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentApplicationStatusBinding.bind(view)
        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()
        casApplicationApi = CasApi.invoke()


        application = arguments?.get("Application") as Application




        handleApplyOrDecline()
    }


    private fun handleApplyOrDecline() {

        GlobalScope.launch {
            curr_user = authRepository.getEmployee(employeeDao)


            //if current user is principle and registrar had forwarded application
            var principle_cond =
                (curr_user.role_id.toInt() == ROLE_Principle) and (application.status_id.toInt() == CAS_FORWARDED_TO_PRINCIPAL)

            //if current user is joint_director and had forwarded to director
            var joint_director_cond =
                (curr_user.role_id.toInt() == ROLE_Joint_Director) and (application.status_id.toInt() == CAS_FORWARDED_TO_JOINT_DIRECTOR)

            //if current user is director and had approved
            var director_cond =
                (curr_user.role_id.toInt() == ROLE_Director) and (application.status_id.toInt() == CAS_APPROVED)
/*
            withContext(Dispatchers.Main) {
                //show approve or decline button if any one is true
                binding.LinearButtonLayout.isVisible = hod_cond or registrar_cond or principle_cond
                binding.linearRemarkLayout.isVisible = hod_cond or registrar_cond or principle_cond
            }

        }

        binding.btnApply.setOnClickListener {


            if (binding.etRemark.text.isEmpty()) {
                requireContext().toast("Please Add Remark");
            } else {

                GlobalScope.launch {


                    // if hod click approved button change status id to approve_by_hod
                    if (curr_user.role_id.toInt() == ROLE_HOD) {
                        val status = casRepo.updateStatusId(
                            application.id,
                            IO_APPROVED_BY_HOD.toString(),
                            binding.etRemark.text.toString(),
                            ioApplicationApi
                        )


                        withContext(Dispatchers.Main) {
                            requireContext().toast(status.status)
                        }

                    }
                    // if registrar click approve button change status id to approve by registrar
                    else if (curr_user.role_id.toInt() == ROLE_Registrar) {
                        val status = ioApplicationRepository.updateStatusId(
                            application.id,
                            IO_APPROVED_BY_REGISTRAR.toString(),
                            binding.etRemark.text.toString(),
                            ioApplicationApi
                        )
                        withContext(Dispatchers.Main) {
                            requireContext().toast(status.status)
                        }

                    }
                    // if principle click approve button change status id to approve by principle
                    else if (curr_user.role_id.toInt() == ROLE_Principle) {
                        val status = ioApplicationRepository.updateStatusId(
                            application.id,
                            IO_APPROVED_BY_PRINCIPLE.toString(),
                            binding.etRemark.text.toString(),
                            ioApplicationApi
                        )
                        withContext(Dispatchers.Main) {
                            requireContext().toast(status.status)
                        }

                    }
                    withContext(Dispatchers.Main) {
                        findNavController().popBackStack()
                    }
                }
            }
        }

        binding.btnDecline.setOnClickListener {

            if (binding.etRemark.text.isEmpty()) {
                requireContext().toast("Please Add Remark");
            } else {
                GlobalScope.launch {
                    // if hod click approved button change status id to approve_by_hod
                    if (curr_user.role_id.toInt() == ROLE_HOD) {
                        val status = ioApplicationRepository.updateStatusId(
                            application.id,
                            IO_Declined_BY_Hod.toString(),
                            binding.etRemark.text.toString(),
                            ioApplicationApi
                        )
                        withContext(Dispatchers.Main) {
                            requireContext().toast(status.status)
                        }

                    }
                    // if registrar click approve button change status id to approve by registrar
                    else if (curr_user.role_id.toInt() == ROLE_Registrar) {
                        val status = ioApplicationRepository.updateStatusId(
                            application.id,
                            IO_Declined_BY_Hod.toString(),
                            binding.etRemark.text.toString(),
                            ioApplicationApi
                        )
                        withContext(Dispatchers.Main) {
                            requireContext().toast(status.status)
                        }

                    }
                    // if principle click approve button change status id to approve by principle
                    else if (curr_user.role_id.toInt() == ROLE_Principle) {
                        val status = ioApplicationRepository.updateStatusId(
                            application.id,
                            IO_Declined_BY_Hod.toString(),
                            binding.etRemark.text.toString(),
                            ioApplicationApi
                        )
                        withContext(Dispatchers.Main) {
                            requireContext().toast(status.status)
                        }

                    }
                    withContext(Dispatchers.Main) {
                        findNavController().popBackStack()
                    }
                }
            }


        }


    }

    private fun setApplicationData() {
        binding.apply {

            tvIoTitle.text = application.title
            tvDesc.text = application.description
            tvDate.text = application.date
            tvPdf.text = application.application
            tvApplicationType.text = application.getApplicationStringType

        }
    }

 */

        }
    }
}