package pt.ipca.dissertation_14861.ui.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.utils.Alerts
import pt.ipca.dissertation_14861.utils.connections.ConnectionReceiver
import pt.ipca.dissertation_14861.utils.connections.ReceiverConnection
import java.io.File

class MainActivity : AppCompatActivity(), ConnectionReceiver.ConnectionReceiverListener {

    // Inicialization of the Project variables
    //toolbar
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var hview: View

    //Username
    private lateinit var user_iv_photo: ImageView
    private lateinit var user_tv_name: TextView
    private lateinit var user_tv_id: TextView

    //Firebase
    private lateinit var  auth : FirebaseAuth

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

        setUpNavigationDrawer()

    }

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

    private fun setUpNavigationDrawer() {
        toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)

        //hide toolbar
        supportActionBar?.hide()

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        navigationView = findViewById<NavigationView>(R.id.nav_view)
        hview = navigationView.getHeaderView(0)

        user_tv_id = hview.findViewById<TextView>(R.id.user_tv_id)
        user_tv_name = hview.findViewById<TextView>(R.id.user_tv_name)
        user_iv_photo = hview.findViewById<ImageView>(R.id.user_iv_photo)

        //Firebase info
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth?.currentUser
        val name:String? = user?.displayName
        val id:String? = user?.uid
        val image = user?.photoUrl

        user_tv_name.text = name

        println("           wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" + name)
//        user_tv_id.text = id

        // rounded corner image
        Glide.with(this).load(image).override(400, 400).apply(RequestOptions.circleCropTransform()).into(
            user_iv_photo
        )

        user_tv_id = hview.findViewById<TextView>(R.id.user_tv_id)
        user_tv_name = hview.findViewById<TextView>(R.id.user_tv_name)
        user_iv_photo = hview.findViewById<ImageView>(R.id.user_iv_photo)

        var actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openNavDrawer,
            R.string.closeNavDrawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*
            Button Fuctions - Here the function is defined for each button
         */
        navigationView.setNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.logout_icon -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}