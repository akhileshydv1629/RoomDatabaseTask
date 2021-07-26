package com.newcompany.roomdatabasetask.utils

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.newcompany.roomdatabasetask.utils.SpinnerExtensions.getSpinnerValue
import com.newcompany.roomdatabasetask.utils.SpinnerExtensions.setSpinnerInverseBindingListener
import com.newcompany.roomdatabasetask.utils.SpinnerExtensions.setSpinnerValue

class InverseSpinnerBinding {

    @BindingAdapter("bind:selectedValue")
    fun Spinner.setSelectedValue(selectedValue: Any?) {
        setSpinnerValue(selectedValue)

    }

    @BindingAdapter("selectedValueAttrChanged")
    fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
        setSpinnerInverseBindingListener(inverseBindingListener)
    }

    companion object InverseSpinnerBinding {

        @JvmStatic
        @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
        fun Spinner.getSelectedValue(): Any? {
            return getSpinnerValue()
        }
    }
}