package com.amigotrip.android

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.NumberPicker


/**
 * Created by Zimincom on 2017. 10. 28..
 */
class NumberPickerDialog : DialogFragment() {

    private var valueChangeListener: NumberPicker.OnValueChangeListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val numberPicker = NumberPicker(activity)

        numberPicker.minValue = 20
        numberPicker.maxValue = 60

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Choose Value")
        builder.setMessage("Choose a number :")


        builder.setPositiveButton("ok") { _, _ ->
            valueChangeListener?.onValueChange(numberPicker, numberPicker.value, numberPicker
                    .value)
        }

        builder.setNegativeButton("CANCEL") { dialog, which ->
            valueChangeListener?.onValueChange(numberPicker,
                    numberPicker.value, numberPicker.value)
        }
        builder.setView(numberPicker)
        return builder.create()
    }

    fun setValueChangeListener(valueChangeListener: NumberPicker.OnValueChangeListener) {
        this.valueChangeListener = valueChangeListener
    }
}