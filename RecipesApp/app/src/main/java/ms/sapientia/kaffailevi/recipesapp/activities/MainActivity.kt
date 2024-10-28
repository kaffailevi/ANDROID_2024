package ms.sapientia.kaffailevi.recipesapp.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ms.sapientia.kaffailevi.recipesapp.R
import ms.sapientia.kaffailevi.recipesapp.databinding.ActivityMainBinding
import ms.sapientia.kaffailevi.recipesapp.databinding.ActivitySplashBinding

class MainActivity : AppCompatActivity() {
    private lateinit var message: String;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d(TAG,"onCreate: MainActivity created" )
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



}