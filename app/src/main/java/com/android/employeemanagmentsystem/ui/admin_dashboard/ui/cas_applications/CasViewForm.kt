package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.cas_applications

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.employeemanagmentsystem.R

import com.android.employeemanagmentsystem.databinding.FragmentCasViewFormBinding

@SuppressLint("ResourceType")
class CasViewForm: Fragment(R.id.fragment_cas_view_form) {
    private  lateinit var binding: FragmentCasViewFormBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCasViewFormBinding.bind(view)


    }
}