package com.lubulwa.kulio.base

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.lubulwa.kulio.R.style.DialogTheme
import com.lubulwa.kulio.R
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.*




open class BaseActivity : AppCompatActivity() {

    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}