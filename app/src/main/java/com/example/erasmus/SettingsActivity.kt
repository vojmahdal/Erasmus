package com.example.erasmus

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.erasmus.language.LangPreference


class SettingsActivity : AppCompatActivity() {

    lateinit var langPreference: LangPreference
    val languageList = arrayOf("en","cs")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        applyTheme()

        langPreference = LangPreference(this)

        val spinner = findViewById<Spinner>(R.id.spinner)

        spinner.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,languageList)

        val lang = langPreference.getLoginCount()
        val index = languageList.indexOf(lang)
        if(index >= 0){
            spinner.setSelection(index)
        }

        val button = findViewById<Button>(R.id.button)
        val radiogrp = findViewById<RadioGroup>(R.id.radioGroup)
        val radiolight = findViewById<RadioButton>(R.id.btnRadioLight)
        val radiodark = findViewById<RadioButton>(R.id.btnRadioDark)
        val radiodevice = findViewById<RadioButton>(R.id.btnRadioDevice)
        button.setOnClickListener {
            val selectedId = radiogrp.checkedRadioButtonId

            when(selectedId){
                radiolight.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                radiodark.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                radiodevice.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            saveThemePreference(selectedId)

            //for language set
            langPreference.setLoginCount(languageList[spinner.selectedItemPosition])
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }



    }

    private fun applyTheme() {

        val radiogrp = findViewById<RadioGroup>(R.id.radioGroup)
        val radiolight = findViewById<RadioButton>(R.id.btnRadioLight)
        val radiodark = findViewById<RadioButton>(R.id.btnRadioDark)
        val radiodevice = findViewById<RadioButton>(R.id.btnRadioDevice)


        val sharedPreferences: SharedPreferences = getPreferences(MODE_PRIVATE)
        val selectedId = sharedPreferences.getInt("selected_theme", radiodevice.id)

        radiogrp.check(selectedId)
        when(selectedId){
            radiolight.id -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            radiodark.id -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            radiodevice.id -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

    }

    private fun saveThemePreference(selectedId: Int) {
        val sharedPreferences: SharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selected_theme", selectedId)
        editor.apply()
    }
}