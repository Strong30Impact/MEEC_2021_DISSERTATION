package pt.ipca.dissertation_14861.ui.fragments.Settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.ui.activities.MainActivity
import pt.ipca.dissertation_14861.ui.fragments.MainFragment
import pt.ipca.dissertation_14861.ui.fragments.SettingsFragment
import pt.ipca.dissertation_14861.utils.Firebase
import pt.ipca.dissertation_14861.utils.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : Fragment(), View.OnClickListener {
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private lateinit var settingsFragment: SettingsFragment
    private lateinit var mainFragment: MainFragment
    private lateinit var user_iv_photo: ImageView
    private lateinit var settings_tv_confirm: TextView
    private lateinit var user_et_name: EditText
    private lateinit var user_tv_id: TextView
    private lateinit var settings_iv_back: ImageView

    //Firebase
/*    private lateinit var auth : FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var uid: String
    private lateinit var fileref: StorageReference
    private lateinit var storageReference: StorageReference
    private lateinit var database: FirebaseDatabase
    var imageUri: Uri? = null*/
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user_iv_photo = view.findViewById(R.id.user_iv_photo)
        settings_tv_confirm = view.findViewById(R.id.settings_tv_confirm)
        user_et_name = view.findViewById(R.id.user_et_name)
        user_tv_id = view.findViewById(R.id.user_tv_id)
        settings_iv_back = view.findViewById(R.id.settings_iv_back)

        user_iv_photo.setOnClickListener(this)
        settings_tv_confirm.setOnClickListener(this)
        settings_iv_back.setOnClickListener(this)

        user_et_name.hint = MainActivity.name
        user_tv_id.text = MainActivity.nCertificate

        mainFragment = MainFragment()
        settingsFragment = SettingsFragment()

/*        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        val user = auth?.currentUser
        val image = user?.photoUrl
        storageReference = FirebaseStorage.getInstance().reference.child("Users Image").child(MainActivity.id)
        fileref = storageReference.child("picture.jpg")

        try {
            fileref?.downloadUrl?.addOnSuccessListener { task ->
                Glide.with(this).load(task).override(300,300).apply(RequestOptions.circleCropTransform()).into(user_iv_photo)
            }
        }catch (e: Exception){
            e.printStackTrace()
            Glide.with(this).load(image).override(300,300).apply(RequestOptions.circleCropTransform()).into(user_iv_photo)
        }

        Glide.with(this).load(image).override(300,300).apply(RequestOptions.circleCropTransform()).into(user_iv_photo)*/

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.user_iv_photo -> {
/*                var openGalleryIntent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(openGalleryIntent,1000)*/
            }
            R.id.settings_tv_confirm -> {
                updateProfile(user_et_name.text.toString())
                Utils.replaceFragment(settingsFragment, parentFragmentManager, 1)
            }
            R.id.settings_iv_back -> {
                Utils.replaceFragment(mainFragment, parentFragmentManager, 1)
            }
        }
    }

/*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            user_iv_photo.setImageURI(imageUri)
            println(imageUri)
        }
    }*/

    private fun updateProfile(name: String){

        // Get new name and change in Main
        MainActivity.name = name

        // Send new name to Firebase
        Firebase.updateUserProfile(MainActivity.email, name)

/*        uploadImage()*/
    }

/*    private fun uploadImage(){
        if (imageUri != null){

            var uploadTask: StorageTask<*>
            uploadTask = fileref.putFile(imageUri!!)

            uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>> Contiuation@{ task ->
                if(!task.isSuccessful ){
                    task.exception?.let{
                        throw it
                    }
                }
                return@Contiuation fileref.downloadUrl
            }).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val downloadUrl = task.result
                    val url = downloadUrl.toString()
                    val map = HashMap<String, Any>()
                    map["photo"] = url
                    FirebaseDatabase.getInstance()
                        .reference
                        .child("Users")
                        .child(MainActivity.id)
                        .updateChildren(map)

                    fileref.downloadUrl.addOnSuccessListener { task ->
                        Glide.with(this).load(task).override(300,300).apply(RequestOptions.circleCropTransform()).into(user_iv_photo)
                    }
                }
            }
        }
    }*/
}