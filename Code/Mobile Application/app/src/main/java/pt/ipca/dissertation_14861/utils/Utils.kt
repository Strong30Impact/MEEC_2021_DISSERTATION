package pt.ipca.dissertation_14861.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseUser
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.ui.activities.LoginActivity
import pt.ipca.dissertation_14861.ui.activities.MainActivity
import java.util.regex.Pattern

class Utils: AppCompatActivity() {

    companion object {

        /*
            Function to move us from other Activity
        */
        fun moveMainPage(user: FirebaseUser?, mContext: Context, page: String) {
//                progressDialog(mContext)
            when (page) {
                "Main" -> {
                    mContext.startActivity(Intent(mContext, MainActivity::class.java))
                }
                "Login" -> {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                }
            }
        }

        /*
            Function to give us the option of watching the password
        */
        fun showPassword(
            isShow: Boolean,
            login_et_password: EditText,
            password_iv_show: ImageView
        ) {
            if (isShow) {
                login_et_password.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                password_iv_show.setImageResource(R.drawable.icon_hide_password)
            } else {
                login_et_password.transformationMethod = PasswordTransformationMethod.getInstance()
                password_iv_show.setImageResource(R.drawable.icon_hide_password)
            }
            login_et_password.setSelection(login_et_password.text.toString().length)
        }

        /*
            Function for the transition between activities
        */
        fun progressDialog(mContext: Context) {

            var progressDialog: ProgressDialog
            //Initialize Progress Dialog
            progressDialog = ProgressDialog(mContext)
            //Show Dialog
            progressDialog.show()
            //Set Content View
            progressDialog.setContentView(R.layout.dialog_progress)
            //Set Transparent background
            progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        /*
            Function that validates the password with the necessary credentials
            -At least one upper case, one lower case, one number, and a combination of 8 or more characters
        */
        fun validatePassword(password: String): String? {
            val upperCase = Pattern.compile("[A-Z]")
            val lowerCase = Pattern.compile("[a-z]")
            val digitCase = Pattern.compile("[0-9]")
            var validate: String = "false"
            validate = if (lowerCase.matcher(password).find()
                && upperCase.matcher(password).find()
                && digitCase.matcher(password).find()
                && password.length >= 8) {
                "false"
            } else {
                "true"
            }
            return validate
        }

        /*
            Function to validate email and alert user
        */
        fun validateEmailAddress(email: String) : Boolean {

            return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        /*
            Function to change special character
        */
        fun changeSpecialCharacter(email: String, remove: Boolean): String {
            var newEmail: String
            if(remove){
                newEmail = email.replace('@', 'A', false)
                newEmail = newEmail.replace('.', 'P', false)
                newEmail = newEmail.replace('_', 'U', false)
                newEmail = newEmail.replace('-', 'H', false)
            } else {
                newEmail = email.replace('A', '@', false)
                newEmail = newEmail.replace('P', '.', false)
                newEmail = newEmail.replace('U', '_', false)
                newEmail = newEmail.replace('H', '-', false)
            }
            return newEmail

        }

        /*
            Function to replace fragment
        */
        fun replaceFragment(fragment: Fragment, manager: FragmentManager){
            manager
                .beginTransaction()
                .replace(R.id.frame_Layout, fragment, null)
                .addToBackStack(null)
                .commit()
        }
    }
}
