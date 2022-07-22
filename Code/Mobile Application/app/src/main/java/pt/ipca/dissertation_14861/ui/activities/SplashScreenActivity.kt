package pt.ipca.dissertation_14861.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.utils.Firebase
import pt.ipca.dissertation_14861.utils.Utils

/*
    Class to created splash screen
*/
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private var mListUserInformation = arrayOf("", "", "", "")

    private val splashtime: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        auth = FirebaseAuth.getInstance()

        // Hide toolbar
        supportActionBar?.hide()

        // Get user info now so you don't have lag issues when showing in Main
        if(auth.currentUser != null) {
            val user: FirebaseUser = auth.currentUser!!

            mListUserInformation = Firebase.getUserInformation(user)
        }

        Handler().postDelayed({
            // Check if the user has authenticated or not
            if(auth.currentUser != null) {

                // Send information to Main
                MainActivity.name = mListUserInformation[0]
                MainActivity.nCertificate = mListUserInformation[2]
                MainActivity.email = mListUserInformation[4]
                MainActivity.id = Utils.changeSpecialCharacter(mListUserInformation[4], true)
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
/*            // Check if the user has read the intro of the App
            startActivity(Intent(this, LoginActivity::class.java))*/
        }, splashtime)
    }
}