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

            }
            drawerLayout.closeDrawers()
            true
        }
    }
}