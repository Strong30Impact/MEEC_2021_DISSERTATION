package pt.ipca.dissertation_14861.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import pt.ipca.dissertation_14861.R
import java.util.logging.Logger

/*
    Class to created splash screen
*/
class SplashScreenActivity : AppCompatActivity() {

    val splashtime: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            // Check if the user has read the intro of the App
            startActivity(Intent(this, MainActivity::class.java))
        }, splashtime)
    }
}