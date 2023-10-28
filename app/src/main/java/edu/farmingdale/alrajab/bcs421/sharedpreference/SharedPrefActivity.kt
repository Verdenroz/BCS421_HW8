package edu.farmingdale.alrajab.bcs421.sharedpreference

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import edu.farmingdale.alrajab.bcs421.databinding.ActivitySharedPrefBinding

class SharedPrefActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySharedPrefBinding
    private val sharedPreferences by lazy{
        getSharedPreferences("User", Context.MODE_PRIVATE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPrefBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveSharedprefBtn.setOnClickListener { saveSharedPref() }

        binding.readSharedprefBtn.setOnClickListener { readSharedPref() }


    }

    private fun saveSharedPref(){
        sharedPreferences.edit(commit = true){
            putString(FNAME_KEY, binding.inputFirstname.text.toString())
            putString(LNAME_KEY, binding.inputLastname.text.toString())
        }
        Log.d("SavePref", sharedPreferences.getString(FNAME_KEY, "").toString())
        Log.d("SavePref", sharedPreferences.getString(LNAME_KEY, "").toString())
    }

    private fun readSharedPref(){
        val userName = sharedPreferences.getString(FNAME_KEY, "") + " " +
                sharedPreferences.getString(LNAME_KEY, "")

        binding.sharedprefContent.text = userName

    }

    companion object{
        const val FNAME_KEY = "FIRST_NAME"
        const val LNAME_KEY = "LAST_NAME"
    }
}