package com.soszynski.mateusz.losowanko

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.defaultSharedPreferences

class MainActivity : AppCompatActivity() {
    var lastNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = defaultSharedPreferences

        picker_min.minValue = 0
        picker_min.maxValue = 50
        picker_min.value = prefs.getInt("random_min", 1)
        picker_min.wrapSelectorWheel = false

        picker_max.minValue = 0
        picker_max.maxValue = 50
        picker_max.value = prefs.getInt("random_max", 25)
        picker_max.wrapSelectorWheel= false

        picker_min.setOnValueChangedListener { picker, oldVal, newVal ->
            if(newVal >= picker_max.value){
                picker.value = oldVal
            }
            prefs.edit()
                .putInt("random_min", picker.value)
                .apply()
        }

        picker_max.setOnValueChangedListener { picker, oldVal, newVal ->
            if(newVal <= picker_min.value){
                picker.value = oldVal
            }
            prefs.edit()
                .putInt("random_max", picker.value)
                .apply()
        }

        button_random.setOnClickListener {
            val min = prefs.getInt("random_min", 1)
            val max = prefs.getInt("random_max", 25)

            var number: Int
            do {
                number = (min..max).random()
            } while(number == lastNumber)
            lastNumber = number

            textView_output.text = number.toString()
        }
    }
}
