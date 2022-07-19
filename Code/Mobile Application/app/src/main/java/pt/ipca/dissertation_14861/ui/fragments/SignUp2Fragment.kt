package pt.ipca.dissertation_14861.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.utils.Alerts
import pt.ipca.dissertation_14861.utils.Firebase
import pt.ipca.dissertation_14861.utils.Utils
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUp2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUp2Fragment : Fragment(), View.OnClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up2, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUp2Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUp2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        lateinit var signup_spinner_health: Spinner

    }

    private lateinit var loginFragment: LoginFragment
    private lateinit var transaction: FragmentTransaction
    private lateinit var login_tv_signup: TextView
    private lateinit var signup_et_email: EditText
    private lateinit var signup_et_password: EditText
    private lateinit var signup_et_confirmpassword: EditText
    private lateinit var signup_btn_signup: Button
    private lateinit var password_iv_show: ImageView
    private lateinit var password_iv_confirmshow: ImageView

    var misshowpass = false
    var misshowconfirmpass = false
    var validationpassword: String? = "false"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_tv_signup = view.findViewById(R.id.login_tv_signup)
        signup_et_email = view.findViewById(R.id.signup_et_email)
        signup_et_password = view.findViewById(R.id.signup_et_password)
        signup_et_confirmpassword = view.findViewById(R.id.signup_et_confirmpassword)
        signup_btn_signup = view.findViewById(R.id.signup_btn_signup)
        password_iv_show = view.findViewById(R.id.password_iv_show)
        password_iv_confirmshow = view.findViewById(R.id.password_iv_confirmshow)
        signup_spinner_health = view.findViewById(R.id.signup_spinner_health)

        login_tv_signup.setOnClickListener(this)
        signup_btn_signup.setOnClickListener(this)
        password_iv_show.setOnClickListener(this)
        password_iv_confirmshow.setOnClickListener(this)


        // Check if the password are the same
        signup_et_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val pass = signup_et_password.text.toString()
                validationpassword = Utils.validatePassword(pass)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        // Sends health institutions to spinner
        signup_spinner_health.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,Firebase.healthInstitutions)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.signup_btn_signup -> {
                if (signup_et_email.text.isNotEmpty() && signup_et_password.text.isNotEmpty()
                    && signup_et_confirmpassword.text.isNotEmpty()
                    && (signup_et_confirmpassword.text.toString() == signup_et_password.text.toString())
                    && validationpassword == "false"
                ) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        signup_et_email.text.toString(),
                        signup_et_password.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {

                            //send information to firebase
                            val list = arrayOf("", "", "", "", "", "")
                            list[0] = SignUpFragment.name
                            list[1] = SignUpFragment.surname
                            list[2] = SignUpFragment.job
                            list[3] = SignUpFragment.certificate
                            list[4] = signup_spinner_health.selectedItem.toString()
                            list[5] = signup_et_email.text.toString()

                            Firebase.sendUserInformation(list)

                            loginFragment = LoginFragment()
                            transaction = fragmentManager?.beginTransaction()!!
                            transaction.replace(R.id.drawable_frameLayout, loginFragment, null)
                            transaction.commit()
                        }
                    }
                } else {
                    authenticationError()
                }
            }
            R.id.password_iv_show -> {
                misshowpass = !misshowpass
                Utils.showPassword(misshowpass, signup_et_password, password_iv_show)
            }
            R.id.password_iv_confirmshow -> {
                misshowconfirmpass = !misshowconfirmpass
                Utils.showPassword(misshowconfirmpass, signup_et_confirmpassword, password_iv_confirmshow)
            }
            R.id.login_tv_signup -> {
                loginFragment = LoginFragment()
                transaction = fragmentManager?.beginTransaction()!!
                transaction.replace(R.id.drawable_frameLayout, loginFragment, null)
                transaction.commit()
            }
        }
    }

    /*
        Function to check if exist an error in authentication
    */
    private fun authenticationError() {
        var msg: String
        val alert = Alerts()
        val builder = AlertDialog.Builder(requireContext())
        when {
            signup_et_email.text.isEmpty() -> {
                msg = "An authentication error has occurred. Please define a email."
                alert.showAlertAuthenticationError(builder, msg)
            }
            signup_et_password.text.isEmpty() -> {
                msg = "An authentication error has occurred. Please define password."
                alert.showAlertAuthenticationError(builder, msg)
            }
            ((signup_et_confirmpassword.text.isEmpty()) || (signup_et_confirmpassword.text.toString() != signup_et_password.text.toString())) -> {
                msg = "An authentication error has occurred. Passwords do not match."
                alert.showAlertAuthenticationError(builder, msg)
            }
            ((signup_et_confirmpassword.text.toString() == signup_et_password.text.toString()) && (validationpassword != "false")) -> {
                msg =
                    "An password verification error has occurred. Please choose a password with 8 or more characters including uppercase, lowercase and digits."
                alert.showAlertAuthenticationError(builder, msg)
            }
        }
    }
}