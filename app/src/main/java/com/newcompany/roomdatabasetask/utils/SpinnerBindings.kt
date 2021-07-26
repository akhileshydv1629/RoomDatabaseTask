package com.newcompany.roomdatabasetask.utils

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.newcompany.roomdatabasetask.MainActivity
import com.newcompany.roomdatabasetask.MainActivityViewModel
import com.newcompany.roomdatabasetask.utils.SpinnerExtensions.setSpinnerEntries
import com.newcompany.roomdatabasetask.utils.SpinnerExtensions.setSpinnerItemSelectedListener
import com.newcompany.roomdatabasetask.utils.SpinnerExtensions.setSpinnerValue

class SpinnerBindings {

    @BindingAdapter("entries")
    fun Spinner.setEntries(entries: List<Any>?) {
        setSpinnerEntries(entries)
    }

    @BindingAdapter("onItemSelected")
    fun Spinner.setOnItemSelectedListener(itemSelectedListener: SpinnerExtensions.ItemSelectedListener?) {
        setSpinnerItemSelectedListener(itemSelectedListener)
    }

    @BindingAdapter("newValue")
    fun Spinner.setNewValue(newValue: Any?) {
        setSpinnerValue(newValue)
    }
}