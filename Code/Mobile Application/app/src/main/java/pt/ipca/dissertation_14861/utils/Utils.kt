package pt.ipca.dissertation_14861.utils

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.dissertation_14861.R

class Utils: AppCompatActivity() {

    companion object {

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
    }
}