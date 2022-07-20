package pt.ipca.dissertation_14861.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.dissertation_14861.R

/*
    Class to created splash screen
*/
class SplashScreenActivity : AppCompatActivity() {

    private val splashtime: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Hide toolbar
        supportActionBar?.hide()

        Handler().postDelayed({
            // Check if the user has read the intro of the App
            startActivity(Intent(this, LoginActivity::class.java))
        }, splashtime)
    }
}