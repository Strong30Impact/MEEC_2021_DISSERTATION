package pt.ipca.dissertation_14861.utils

import android.R
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pt.ipca.dissertation_14861.ui.fragments.LoginFragment
import pt.ipca.dissertation_14861.ui.fragments.SignUp2Fragment
import java.util.*

class Firebase: AppCompatActivity()  {

    companion object {

        var healthInstitutions: ArrayList<String> = arrayListOf<String>("")

        /*
            Function for the sign in with user credentials
        */
        fun signinAndSignup(
            mAuth: FirebaseAuth,
            mContext: Context,
            email: String,
            password: String
        ) {
            val alert = Alerts()
            val builder = AlertDialog.Builder(mContext)

            if (email.isNotEmpty() && password.isNotEmpty()) {

                // Check if email is validated
                if (Utils.validateEmailAddress(email)) {
                    mAuth?.createUserWithEmailAndPassword(email, password)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                //Creating a user account
                                Utils.moveMainPage(task.result?.user, mContext, "Main")
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
                    alert.showAlertSignInError(builder,"An authentication error has occurred. Please define a validated email.")
                }
            } else {
                alert.showAlertSignInError(builder,"An authentication error has occurred. Please set an email and/or password.")
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
                        Utils.moveMainPage(task.result?.user, mContext, "Main")
                    } else {
                        //Show the error message
                        Toast.makeText(mContext, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }

        /*
            Function that sends the password credentials to the user email - reset password
        */
        fun sendResetPassword(mAuth: FirebaseAuth?, email: String, mContext: Context){
            mAuth?.sendPasswordResetEmail(email)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(mContext, "Email sent", Toast.LENGTH_LONG)
                            .show()
                        Utils.moveMainPage(mAuth.currentUser, mContext, "Login")

                    }
                }
        }

        /*
            Function to sends User information for the firebase
        */
        fun sendUserInformation(listUserInformation: Array<String>) {

            var maps = mutableMapOf<String,Any?>()
            maps["Name"] = listUserInformation[0]
            maps["Surname"] = listUserInformation[1]
            maps["job"] = listUserInformation[2]
            maps["Health Institution"] = listUserInformation[4]
            maps["email"] = listUserInformation[5]

            var refdatabase = FirebaseDatabase.getInstance()
            refdatabase.reference
                .child("Users")
                .child(listUserInformation[3])
                .updateChildren(maps)
        }

        /*
            Function to get health institutions from firebase to user select
        */
        fun getHealthInstitutions( mContext: Context) {
            var databaseReference = FirebaseDatabase.getInstance()
            databaseReference.getReference("HealthInstitutions").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    healthInstitutions.removeAt(0)
                    for (postSnapshot in snapshot.children) {
                        val name = postSnapshot.key
                        // Check if exist same health institutions
                        if(!(healthInstitutions.contains(name))){
                            healthInstitutions.add(name.toString())
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}