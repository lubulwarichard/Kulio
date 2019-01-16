package com.lubulwa.kulio.base

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.lubulwa.kulio.R.style.DialogTheme
import com.lubulwa.kulio.R
import android.widget.DatePicker
import java.util.*




open class BaseActivity : AppCompatActivity() {

    val depart_calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun openDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DialogTheme,
            date_depart,
            depart_calendar.get(Calendar.YEAR),
            depart_calendar.get(Calendar.MONTH),
            depart_calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()

    }

    var date_depart: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            depart_calendar.set(Calendar.YEAR, year)
            depart_calendar.set(Calendar.MONTH, monthOfYear)
            depart_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }


//    private fun updateDepartLabel() {
//        val myFormat = "yyyy-MM-dd" //In which you need put here
//        val sdf = SimpleDateFormat(myFormat, Locale.US)
//        depart_date_tv.setText(sdf.format(depart_calendar.time))
//    }
//
//    private fun updateReturnLabel() {
//        val myFormat = "yyyy-MM-dd" //In which you need put here
//        val sdf = SimpleDateFormat(myFormat, Locale.US)
//        return_date_tv.setText(sdf.format(return_calendar.getTime()))
//    }

}