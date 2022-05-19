package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.cas_applications.AppliedCasAdapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.models.responses.casData
import com.android.employeemanagmentsystem.databinding.ItemCasApplicationBinding
import com.android.employeemanagmentsystem.databinding.ItemTrainingsBinding
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.cas_applications.CasApplications
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.training_applications.AppliedTrainingsAdapter
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_applications.AppliedIoApplicationsAdapter
import com.android.employeemanagmentsystem.utils.getCasApplicationStatusById
import com.android.employeemanagmentsystem.utils.getDurationInWeeks

class AppliedCasAdapter(
    private val applications: List<casData>,
    private val listener: ApplicationClickListener
): RecyclerView.Adapter<AppliedCasAdapter.ApplicationViewHolder>() {

    interface ApplicationClickListener{
        fun onApplicationClicked(application: casData)
    }

    inner class ApplicationViewHolder(val binding: ItemCasApplicationBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onApplicationClicked(applications[adapterPosition])
                }
            }
        }
/*
        @SuppressLint("SetTextI18n")
        fun bindDate(application: casData){
            binding.apply {
                avName.text = application.firstname + application.surname
                avApplicationType.text = application.type.
                avStatus.text = application.application_status_id.getCasApplicationStatusById()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val binding = ItemTrainingsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrainingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bindDate(
            trainings[position]
        )
    }

    override fun getItemCount() = trainings.size*/

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}