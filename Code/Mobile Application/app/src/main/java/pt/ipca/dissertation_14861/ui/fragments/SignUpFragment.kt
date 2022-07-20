package pt.ipca.dissertation_14861.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.utils.Alerts
import pt.ipca.dissertation_14861.utils.Firebase
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment(), View.OnClickListener {
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
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        lateinit var name: String
        lateinit var surname: String
        lateinit var certificate: String
        var job: String = "doctor"
    }

    private lateinit var loginFragment: LoginFragment
    private lateinit var signUp2Fragment: SignUp2Fragment
    private lateinit var transaction: FragmentTransaction
    private lateinit var login_tv_signup: TextView
    private lateinit var next_btn_signup: Button
    private lateinit var signup_et_name: EditText
    private lateinit var signup_et_surname: EditText
    private lateinit var signup_et_certificate: EditText
    private lateinit var signup_rb_job: RadioGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_tv_signup = view.findViewById(R.id.login_tv_signup)
        next_btn_signup = view.findViewById(R.id.signup_btn_next)
        signup_et_name = view.findViewById(R.id.signup_et_name)
        signup_et_surname = view.findViewById(R.id.signup_et_surname)
        signup_et_certificate = view.findViewById(R.id.signup_et_certificate)
        signup_rb_job = view.findViewById(R.id.signup_rb_job)

        login_tv_signup.setOnClickListener(this)
        next_btn_signup.setOnClickListener(this)
        signup_rb_job.setOnClickListener(this)

        signup_rb_job.setOnCheckedChangeListener { _, checkedId ->
            val radioButtonSelected = view.findViewById<RadioButton>(checkedId)
            job = radioButtonSelected.text.toString()
        }

        // Clear list to don't add same health institutions
        Firebase.healthInstitutions = arrayListOf<String>("")

        //get information health institutions so there is no delay when presenting the list of health institutions to the user
        Firebase.getHealthInstitutions(requireContext())
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.signup_btn_next -> {
                if (signup_et_name.text.isNotEmpty() && signup_et_surname.text.isNotEmpty()
                    && signup_et_certificate.text.isNotEmpty()
                ) {
                    // Send information to signup2Fragment
                    name = signup_et_name.text.toString()
                    surname = signup_et_surname.text.toString()
                    certificate = signup_et_certificate.text.toString()

                    signUp2Fragment = SignUp2Fragment()
                    transaction = fragmentManager?.beginTransaction()!!
                    transaction.replace(R.id.drawable_frameLayout, signUp2Fragment, null)
                    transaction.commit()

                } else {
                    authenticationError()
                }
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
            signup_et_name.text.isEmpty() -> {
                msg = "An authentication error has occurred. Please define a name."
                alert.showAlertAuthenticationError(builder, msg)
            }
            signup_et_surname.text.isEmpty() -> {
                msg = "An authentication error has occurred. Please define a username."
                alert.showAlertAuthenticationError(builder, msg)
            }
            signup_et_certificate.text.isEmpty() -> {
                msg = "An authentication error has occurred. Please define a professional certificate number."
                alert.showAlertAuthenticationError(builder, msg)
            }
        }
    }
}