package pt.ipca.dissertation_14861.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.ui.activities.MainActivity
import java.util.regex.Pattern

class Utils: AppCompatActivity() {

    companion object {

        /*
            Function to move us from Activity after the login
        */
        fun moveMainPage(user: FirebaseUser?, mContext: Context) {
//                progressDialog(mContext)
                mContext.startActivity(Intent(mContext, MainActivity::class.java))
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
    }
}
