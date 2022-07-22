package pt.ipca.dissertation_14861.ui.fragments

import android.os.Bundle
import android.os.TestLooperManager
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.ui.activities.LoginActivity
import pt.ipca.dissertation_14861.ui.activities.MainActivity
import pt.ipca.dissertation_14861.ui.fragments.Settings.ChangePasswordFragment
import pt.ipca.dissertation_14861.ui.fragments.Settings.EditProfileFragment
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
class SettingsFragment : Fragment(), View.OnClickListener {
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

    private lateinit var mainFragment: MainFragment
    private lateinit var changePasswordFragment: ChangePasswordFragment
    private lateinit var editProfileFragment: EditProfileFragment
    private lateinit var settings_iv_back: ImageView
    private lateinit var settings_tv_profile: TextView
    private lateinit var settings_iv_profile: ImageView
    private lateinit var settings_iv_passwordNext: ImageView
    private lateinit var settings_iv_forgotNext: ImageView
    private lateinit var settings_iv_languageNext: ImageView
    private lateinit var settings_iv_aboutNext: ImageView
    private lateinit var user_tv_name: TextView
    private lateinit var user_tv_id: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings_iv_back = view.findViewById(R.id.settings_iv_back)
        settings_tv_profile = view.findViewById(R.id.settings_tv_profile)
        settings_iv_profile = view.findViewById(R.id.settings_iv_profile)
        settings_iv_passwordNext = view.findViewById(R.id.settings_iv_passwordNext)
        settings_iv_forgotNext = view.findViewById(R.id.settings_iv_forgotNext)
        settings_iv_languageNext = view.findViewById(R.id.settings_iv_languageNext)
        settings_iv_aboutNext = view.findViewById(R.id.settings_iv_aboutNext)
        user_tv_id = view.findViewById(R.id.user_tv_id)
        user_tv_name = view.findViewById(R.id.user_tv_name)

        settings_iv_back.setOnClickListener(this)
        settings_tv_profile.setOnClickListener(this)
        settings_iv_profile.setOnClickListener(this)
        settings_iv_passwordNext.setOnClickListener(this)
        settings_iv_forgotNext.setOnClickListener(this)
        settings_iv_languageNext.setOnClickListener(this)

        mainFragment = MainFragment()
        changePasswordFragment = ChangePasswordFragment()
        editProfileFragment = EditProfileFragment()

        user_tv_name.text = MainActivity.name
        user_tv_id.text = MainActivity.nCertificate
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.settings_iv_back -> {
                Utils.replaceFragment(mainFragment, parentFragmentManager, 1)
            }
            R.id.settings_tv_profile -> {
                Utils.replaceFragment(editProfileFragment, parentFragmentManager, 1)
            }
            R.id.settings_iv_profile -> {
                Utils.replaceFragment(editProfileFragment, parentFragmentManager, 1)
            }
            R.id.settings_iv_passwordNext -> {
                Utils.replaceFragment(changePasswordFragment, parentFragmentManager, 1)
            }
            R.id.settings_iv_forgotNext -> {
                Utils.replaceFragment(mainFragment, parentFragmentManager, 1)
            }
            R.id.settings_iv_password -> {
                Utils.replaceFragment(mainFragment, parentFragmentManager, 1)
            }
            R.id.settings_iv_aboutNext -> {
                Utils.replaceFragment(mainFragment, parentFragmentManager, 1)
            }
        }
    }
}