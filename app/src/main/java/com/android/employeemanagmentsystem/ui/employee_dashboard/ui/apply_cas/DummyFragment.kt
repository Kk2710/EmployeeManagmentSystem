package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.apply_cas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.TrainingTypes
import com.android.employeemanagmentsystem.data.network.apis.CasApi
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.CasRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentDummyBinding
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.apply_training.TrainingTypesAdapter
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.*

class DummyFragment: Fragment(R.layout.fragment_dummy) {
    private lateinit var binding: FragmentDummyBinding

    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private  var casApi: CasApi = CasApi()
    private lateinit var casRepo: CasRepository

    /*
    public fun getTrainingTypes() {
        GlobalScope.launch {
            val types: List<TrainingTypes> = casRepo.getTrainingTypes(casApi)

            val adapter = TrainingTypesAdapter(requireContext(), types)

            withContext(Dispatchers.Main) {
                binding.spinnerTrainingTypes.adapter = adapter

                binding.spinnerTrainingTypes.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            selectedType = types[p2].id
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }

                    }

            }
        }
    }*/



    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDummyBinding.bind(view)


        binding.idBtnRegister.setOnClickListener {
            val surname = binding.idEdtSurName.text.toString()
            val firstname = binding.idEdtFirstname.text.toString()
            val middlename = binding.idEdtMiddlename.text.toString()
            val email = binding.idEdtEmail.text.toString()
            val phone = binding.idEdtPhnNumber.text.toString()
            val date_of_birth = binding.idEdtDate.text.toString()
            val age = binding.idEdtAge.text.toString()
            val gender = binding.idEdtGender.text.toString()
            val date_of_joining = binding.idEdtDOJ.text.toString()
            val sevarth_id = binding.idEdtSevid.text.toString()
            val branch = binding.idEdtBranch.text.toString()
            val address = binding.idEdtAddress.text.toString()
            val state = binding.idEdtState.text.toString()
            val city = binding.idEdtCity.text.toString()
            val village = binding.idEdtVillage.text.toString()
            val pincode = binding.idEdtPincode.text.toString()

            authRepository = AuthRepository()
            employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()
            casRepo = CasRepository()


            when {

                surname.isBlank() -> requireContext().toast("Please Enter Surname")
                firstname.isBlank() -> requireContext().toast("Please Enter First name")
                middlename.isBlank() -> requireContext().toast("Please Enter MiddleName")
                email.isBlank() -> requireContext().toast("Please Select Email")
                phone.isBlank() -> requireContext().toast("Please Select Phone")
                date_of_birth.isBlank() -> requireContext().toast("Please Select Birth Date")
                age.isBlank() -> requireContext().toast("Please Select age")
                gender.isBlank() -> requireContext().toast("Please Select gender")
                date_of_joining.isBlank() -> requireContext().toast("Please Select Joining Date")
                sevarth_id.isBlank() -> requireContext().toast("Please Select Sevarth ID")
                branch.isBlank() -> requireContext().toast("Please Select branch")
                address.isBlank() -> requireContext().toast("Please Enter Address")
                state.isBlank() -> requireContext().toast("Please Select State")
                city.isBlank() -> requireContext().toast("Please Select City")
                village.isBlank() -> requireContext().toast("Please Enter Village")
                pincode.isBlank() -> requireContext().toast("Please Enter Pincode")

                else -> {
                    GlobalScope.launch {

                        try {

                            withContext(Dispatchers.Main) {
                                binding.progressBarCasPersonal.isVisible = true
                            }



                            //getting employee details from room database
                            val employee = authRepository.getEmployee(employeeDao)

                            val casResponse = casRepo.add_personalDetails(
                                surname = surname,
                                firstname = firstname,
                                middlename = middlename,
                                email = email,
                                phone = phone,
                                date_of_birth = date_of_birth,
                                age = age,
                                gender = gender,
                                date_of_joining = date_of_birth,
                                sevarth_id = employee.sevarth_id,
                                branch = branch,
                                address = address,
                                state = state,
                                city = city,
                                village = village,
                                pincode = pincode,
                                CasApi = casApi
                            )


                            withContext(Dispatchers.Main) {
                                binding.progressBarCasPersonal.isVisible = false
                                requireContext().toast("in contect dispatchers"  + casResponse.status)
                            }

                            delay((1 * 1000).toLong())

                            withContext(Dispatchers.Main) {
                                findNavController().popBackStack(R.id.nav_personal_details, true)
                                findNavController().navigate(R.id.nav_professional_details)
                            }

                        } catch (e: Exception) {

                            withContext(Dispatchers.Main) {
                                binding.progressBarCasPersonal.isVisible = false
                            }

                            requireContext().handleException(e)

                        }

                    }
                }
            }


        }
    }
}