package pt.ipca.dissertation_14861.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.utils.Firebase
import pt.ipca.dissertation_14861.utils.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


/*        login_btn_signin = view.findViewById(R.id.login_btn_signin)
        login_et_email = view.findViewById(R.id.login_et_email)
        login_et_password = view.findViewById(R.id.login_et_password)
        password_iv_show = view.findViewById(R.id.password_iv_show)
        login_tv_forgpass = view.findViewById(R.id.login_tv_forgpass)
        login_tv_signup = view.findViewById(R.id.login_tv_signup)


        login_btn_signin.setOnClickListener(this)
        password_iv_show.setOnClickListener(this)
        login_tv_forgpass.setOnClickListener(this)
        login_tv_signup.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()*/

    }

/*    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_btn_signin -> {
                val email = login_et_email.text.toString()
                val password = login_et_password.text.toString()

                Firebase.signinAndSignup(mAuth, requireContext(), email, password)
            }
            R.id.password_iv_show -> {
                misshowpass = !misshowpass
                Utils.showPassword(misshowpass, login_et_password, password_iv_show)
            }
            R.id.login_tv_forgpass -> {
                forgotPasswordFragment = ForgotPasswordFragment()
                transaction = fragmentManager?.beginTransaction()!!
                transaction.replace(R.id.drawable_frameLayout, forgotPasswordFragment, null)
                transaction.commit()
            }
            R.id.login_tv_signup -> {
                signUpFragment = SignUpFragment()
                transaction = fragmentManager?.beginTransaction()!!
                transaction.replace(R.id.drawable_frameLayout, signUpFragment, null)
                transaction.commit()
            }
        }
    }*/
}