package pt.ipca.dissertation_14861.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.ui.fragments.*
import pt.ipca.dissertation_14861.utils.Alerts
import pt.ipca.dissertation_14861.utils.connections.ConnectionReceiver
import pt.ipca.dissertation_14861.utils.connections.ReceiverConnection


class MainActivity : AppCompatActivity(), ConnectionReceiver.ConnectionReceiverListener {

    // Inicialization of the Project variables
    //toolbar
/*    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var hview: View*/

    private lateinit var bottomNavigationView: BottomNavigationView

    //Username
    private lateinit var user_iv_photo: ImageView
    private lateinit var user_tv_name: TextView
    private lateinit var user_tv_id: TextView

    //Firebase
    private lateinit var  auth : FirebaseAuth

    //Fragments
    private var settingsFragment: SettingsFragment = SettingsFragment()
    private var mainFragment: MainFragment = MainFragment()
    private var gamepadJoystickFragment: GamepadJoystickFragment = GamepadJoystickFragment()
    private var trackingCameraFragment: TrackingCameraFragment = TrackingCameraFragment()
    private var addProblemFragment: AddProblemFragment = AddProblemFragment()

    companion object{
        lateinit var name: String
        lateinit var nCertificate: String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide toolbar
        supportActionBar?.hide()

        //Internet Connection
        baseContext.registerReceiver(
            ConnectionReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        ReceiverConnection.instance.setConnectionListener(this)

        bottomNavigationView = findViewById(R.id.nav_view)

        supportFragmentManager.beginTransaction().replace(R.id.frame_Layout,mainFragment).commit()

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                /*
                    Button to open main
                */
                R.id.transport_icon -> {
                    replaceFragment(mainFragment)
                }
                /*
                Button to open transport list
                */
                R.id.transport_list_icon -> {
                    replaceFragment(addProblemFragment)
                }
                /*
                Button to open robot tracking camera
                */
                R.id.camera_icon -> {
                    replaceFragment(trackingCameraFragment)
                }
                /*
                Button to open gamepad joystick
                */
                R.id.ros_icon -> {
                    replaceFragment(gamepadJoystickFragment)
                }
                /*
                    Button to open more information
                */
                R.id.more_icon -> {
                    openPopUp()
                }
            }
            return@setOnItemSelectedListener true
        }
    //        setUpNavigationDrawer()
    }

    /*
        Function to open popup menu and show more information
    */
    private fun openPopUp() {

        val popupMenu = PopupMenu(this, findViewById(R.id.more_icon))
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.report_icon -> {
                    replaceFragment(addProblemFragment)
                    true
                }
                R.id.settings_icon -> {
                    replaceFragment(settingsFragment)
                    true
                }
                R.id.logout_icon -> {
                    // clear user information
                    name = ""
                    nCertificate = ""
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        popupMenu.inflate(R.menu.popup_menu)

        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popupMenu)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (e: Exception){
            Log.e("Main", "Error showing menu icons.", e)
        } finally {
            popupMenu.show()
        }
    }

    private fun replaceFragment(fragment: Fragment){

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_Layout, fragment, null)
            .addToBackStack(null)
            .commit()    }

    /*
        Function that checks the Internet connection
    */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if(!isConnected){
            val alert = Alerts()
            val builder = AlertDialog.Builder(this)
            alert.showAlertConnectionInternet(builder, this)
        }
    }

    /*   private fun setUpNavigationDrawer() {
   //        toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)

           drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
           navigationView = findViewById<NavigationView>(R.id.nav_view)
           hview = navigationView.getHeaderView(0)

           user_tv_id = hview.findViewById<TextView>(R.id.user_tv_id)
           user_tv_name = hview.findViewById<TextView>(R.id.user_tv_name)
           user_iv_photo = hview.findViewById<ImageView>(R.id.user_iv_photo)

           //Firebase info
           auth = FirebaseAuth.getInstance()

           // Show user information
           user_tv_name.text = name

           if(nCertificate == "Administrator"){
               user_tv_id.text = nCertificate
           } else {
               user_tv_id.text = "Certificate Professional nº " + nCertificate
           }

   *//*        // rounded corner image
        Glide.with(this).load(image).override(400, 400).apply(RequestOptions.circleCropTransform()).into(
            user_iv_photo
        )*//*

        user_tv_id = hview.findViewById<TextView>(R.id.user_tv_id)
        user_tv_name = hview.findViewById<TextView>(R.id.user_tv_name)
        user_iv_photo = hview.findViewById<ImageView>(R.id.user_iv_photo)

*//*        var actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openNavDrawer,
            R.string.closeNavDrawer
        )*//*

*//*        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*//*

        mainFragment = MainFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.drawable_frameLayout, mainFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        *//*
            Button Fuctions - Here the function is defined for each button
         *//*
        navigationView.setNavigationItemSelectedListener{
            when (it.itemId) {
                *//*
                    Button to open add problems
                *//*
                R.id.add_icon -> {
                    addProblemFragment = AddProblemFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.drawable_frameLayout, addProblemFragment, null).addToBackStack(null)
                        .commit()
                }
                *//*
                    Button to open robot tracking camera
                *//*
                R.id.camera_icon -> {
                    trackingCameraFragment = TrackingCameraFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.drawable_frameLayout, trackingCameraFragment, null).addToBackStack(null)
                        .commit()
                }
                *//*
                    Button to open gamepad joystick
                *//*
                R.id.ros_icon -> {
                    gamepadJoystickFragment = GamepadJoystickFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.drawable_frameLayout, gamepadJoystickFragment, null).addToBackStack(null)
                        .commit()
                }
*//*                *//**//*
                    Button to open settings
                 *//**//*
                R.id.settings_icon -> {
                    settingsFragment = SettingsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.drawable_frameLayout, settingsFragment, null).addToBackStack(null)
                        .commit()
                }
                *//**//*
                    Button to log out
                *//**//*
                R.id.logout_icon -> {
                    // clear user information
                    name = ""
                    nCertificate = ""
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }*//*
            }
            drawerLayout.closeDrawers()
            true
        }
    }*/
}