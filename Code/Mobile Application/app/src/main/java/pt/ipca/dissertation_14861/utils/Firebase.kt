package pt.ipca.dissertation_14861.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class Firebase: AppCompatActivity()  {

    companion object {

        /*
            Function for the sign in with user credentials
        */
        fun signinAndSignup(
            mAuth: FirebaseAuth,
            mContext: Context,
            email: String,
            password: String
        ) {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                mAuth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            //Creating a user account
                            Utils.moveMainPage(task.result?.user, mContext)
                        } else if (task.exception?.message.isNullOrEmpty()) {
                            //Show the error message
                            Toast.makeText(mContext, task.exception?.message, Toast.LENGTH_LONG)
                                .show()
                        } else {
                            //Login if you have account
                            signinEmail(mAuth, mContext, email, password)
                        }
                    }
            } else {
                val alert = Alerts()
                val builder = AlertDialog.Builder(mContext)
                alert.showAlertSignInError(
                    builder,
                    "An authentication error has occurred. Please set an email and/or password"
                )
            }
        }

        /*
            Function for the login with Emails created by the users
        */
        fun signinEmail(mAuth: FirebaseAuth, mContext: Context, email: String, password: String) {
            mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Login
                        Utils.moveMainPage(task.result?.user, mContext)
                    } else {
                        //Show the error message
                        Toast.makeText(mContext, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}