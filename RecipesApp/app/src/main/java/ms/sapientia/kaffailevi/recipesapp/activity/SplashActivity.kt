package ms.sapientia.kaffailevi.recipesapp.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ms.sapientia.kaffailevi.recipesapp.R
import ms.sapientia.kaffailevi.recipesapp.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val ANIMATION_TIME_OUT: Long = 2000L;
    private val ANIMATION_DURATION: Long = 1000L;
    private val SPLASH_TIME_OUT: Long = ANIMATION_TIME_OUT + ANIMATION_DURATION;


    private var logoYPosition: Float = -600F;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d(TAG, "onCreate: SplashActivity created.")
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val handlerThread = HandlerThread("SplashHandlerThread", -9)
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val floatingImage: ImageView = findViewById(R.id.imageView3)
        val backgroundImageRelativeLayout: RelativeLayout = findViewById(R.id.relativeLayout)

        val floatingImageAnimator = ObjectAnimator.ofFloat(
            floatingImage, "translationY", 0f, logoYPosition
        )
        val backgroundAnimator = ObjectAnimator.ofFloat(
            backgroundImageRelativeLayout, "alpha", 1f, 0f
        )
        floatingImageAnimator.duration = ANIMATION_DURATION
        backgroundAnimator.duration = ANIMATION_DURATION
        handler.postDelayed(
            {
                floatingImageAnimator.start()
                backgroundAnimator.start()
            }, ANIMATION_TIME_OUT
        )

    }


    override fun onStart() {
        super.onStart()
        // Use a HandlerThread to create a background thread
        val handlerThread = HandlerThread("SplashHandlerThread", -10)
        handlerThread.start() // Create a Handler on the new HandlerThread
        val handler = Handler(handlerThread.looper)
        handler.postDelayed(
            {
                // Navigate to MainActivity after the delay
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, SPLASH_TIME_OUT
        )

    }


}