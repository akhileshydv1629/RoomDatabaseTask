package com.newcompany.roomdatabasetask.utils

import androidx.databinding.DataBindingComponent

class BindingComponent: DataBindingComponent {
    override fun getSpinnerBindings(): SpinnerBindings {
            return SpinnerBindings()
    }

    override fun getInverseSpinnerBinding(): InverseSpinnerBinding {
        return InverseSpinnerBinding()
    }
}