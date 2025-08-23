package com.countrylist.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.countrylist.data.country.model.CountryModel
import com.countrylist.preferences.ApplicationPreferences
import com.countrylist.ui.navigation.NavGraph
import com.countrylist.ui.theme.CountryListTheme
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    val appPreferences: ApplicationPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(appPreferences.favoritesCountries.isEmpty()){
            getFirebaseData()
        }

        enableEdgeToEdge()
        setContent {
            CountryListTheme {
                NavGraph(navController = rememberNavController())
            }
        }
    }

    @SuppressLint("HardwareIds")
    fun getFirebaseData(){
        val androidId: String = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        Log.d("DEVICE ID", androidId)

        FirebaseDatabase.getInstance().getReference().child(androidId).get().addOnCompleteListener {task ->
            if(task.isSuccessful){
                val data = task.getResult()
                val array = data.getValue<List<CountryModel>>()
                if(array != null){
                    appPreferences.favoritesCountries = array
                }
            }
        }
    }
}