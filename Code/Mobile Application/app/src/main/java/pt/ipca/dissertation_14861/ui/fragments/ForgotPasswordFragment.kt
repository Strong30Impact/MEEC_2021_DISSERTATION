package pt.ipca.dissertation_14861.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.utils.Alerts
import pt.ipca.dissertation_14861.utils.Firebase
import pt.ipca.dissertation_14861.utils.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgotPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgotPasswordFragment : Fragment(), View.OnClickListener {
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
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForgotPasswordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForgotPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private lateinit var fp_et_email: EditText
    private lateinit var fp_tv_signup: TextView
    private lateinit var fp_btn_password: Button
    private lateinit var fp_btn_back: Button
    private lateinit var transaction: FragmentTransaction
    private lateinit var mAuth : FirebaseAuth
    private lateinit var loginFragment: LoginFragment
    private lateinit var signUpFragment: SignUpFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        fp_et_email = view.findViewById(R.id.fp_et_email)
        fp_tv_signup = view.findViewById(R.id.fp_tv_signup)
        fp_btn_password = view.findViewById(R.id.fp_btn_password)
        fp_btn_back = view.findViewById(R.id.fp_btn_back)

        fp_tv_signup.setOnClickListener(this)
        fp_btn_password.setOnClickListener(this)
        fp_btn_back.setOnClickListener(this)

        loginFragment = LoginFragment()
        signUpFragment = SignUpFragment()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fp_btn_password -> {
                val emailCorrect = Utils.validateEmailAddress(fp_et_email.text.toString())
                if (emailCorrect) {
                    Firebase.sendResetPassword(mAuth, fp_et_email.text.toString(), requireContext())
                } else {
                    val alert = Alerts()
                    val builder = AlertDialog.Builder(requireContext())
                    alert.showAlertSignInError(builder,"An authentication error has occurred. Please define a validated email.")
                }
            }
            R.id.fp_btn_back -> {
                Utils.replaceFragment(loginFragment, parentFragmentManager, 2)
            }
            R.id.fp_tv_signup -> {
                Utils.replaceFragment(signUpFragment, parentFragmentManager, 2)
            }
        }
    }
}