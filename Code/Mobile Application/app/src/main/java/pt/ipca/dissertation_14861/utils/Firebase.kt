package pt.ipca.dissertation_14861.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pt.ipca.dissertation_14861.ui.activities.LoginActivity
import pt.ipca.dissertation_14861.ui.activities.MainActivity

class Firebase: AppCompatActivity()  {

    companion object {

        var healthInstitutions: ArrayList<String> = arrayListOf("")
        private var mListUserInformation = arrayOf("", "", "", "")

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
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
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
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Get user info now so you don't have lag issues when showing in Main
                        val user: FirebaseUser = mAuth.currentUser!!

                        mListUserInformation = getUserInformation(user)

                        Handler().postDelayed({

                        // Send information to Main
                            MainActivity.name = mListUserInformation[0]
                            MainActivity.nCertificate = mListUserInformation[2]
                            //Login
                            Utils.moveMainPage(task.result?.user, mContext, "Main")
                        }, 1000)
                    } else {
                        //Show the error message
                        Toast.makeText(mContext, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }

        /*
            Function that sends the password credentials to the user email - reset password
        */
        fun sendResetPassword(mAuth: FirebaseAuth, email: String, mContext: Context){
            mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
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
            val maps = mutableMapOf<String,Any?>()

            maps["Name"] = listUserInformation[0]
            maps["Surname"] = listUserInformation[1]
            maps["Job"] = listUserInformation[2]
            maps["N Certificate Professional"] = listUserInformation[3]
            maps["Health Institution"] = listUserInformation[4]
            maps["Email"] = listUserInformation[5]

            // Remove special characters
            var newEmail = Utils.changeSpecialCharacter(listUserInformation[5], true)

            val refdatabase = FirebaseDatabase.getInstance()
            refdatabase.reference
                .child("Users")
                .child(newEmail)
                .updateChildren(maps)
        }

        /*
            Function to get health institutions from firebase to user select
        */
        fun getHealthInstitutions() {
            val databaseReference = FirebaseDatabase.getInstance()
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

        /*
            Function to get user information
        */
        fun getUserInformation(user: FirebaseUser): Array<String> {
            val email: String = user.email.toString()

            val newEmail = Utils.changeSpecialCharacter(email, true)

            val databaseReference = FirebaseDatabase.getInstance()
            var mListUserInformation = arrayOf("", "", "", "", "")
            databaseReference.getReference("Users").child(newEmail).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // send data in array
                    mListUserInformation[0] = snapshot.child("Name").value.toString() + " " + snapshot.child("Surname").value.toString()
                    mListUserInformation[1] = snapshot.child("Job").value.toString()
                    mListUserInformation[2] = snapshot.child("N Certificate Professional").value.toString()
                    mListUserInformation[3] = snapshot.child("Health Institution").value.toString()
                    mListUserInformation[4] = snapshot.child("Email").value.toString()

                    println("           iiiiiiiiiiiiiiiiiiiiiiiiiii" + mListUserInformation[0])

                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

            return mListUserInformation
        }

        /*
            Function to update user profile
        */
        fun updateUserProfile(email: String, fullname: String){
            val maps = mutableMapOf<String,Any?>()

            // Split string to obtain name and surname
            val name = fullname.split(" ")

            maps["Name"] = name[0]
            maps["Surname"] = name[1].trim()

            // Remove special characters
            var newEmail = Utils.changeSpecialCharacter(email, true)

            val refdatabase = FirebaseDatabase.getInstance()
            refdatabase.reference
                .child("Users")
                .child(newEmail)
                .updateChildren(maps)
        }
    }
}