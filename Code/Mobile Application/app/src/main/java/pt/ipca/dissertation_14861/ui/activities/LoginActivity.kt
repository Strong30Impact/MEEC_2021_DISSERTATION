package pt.ipca.dissertation_14861.ui.activities

import android.app.AlertDialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import pt.ipca.dissertation_14861.R
import pt.ipca.dissertation_14861.ui.fragments.LoginFragment
import pt.ipca.dissertation_14861.utils.Alerts
import pt.ipca.dissertation_14861.utils.connections.ConnectionReceiver
import pt.ipca.dissertation_14861.utils.connections.ReceiverConnection

class LoginActivity : AppCompatActivity(), ConnectionReceiver.ConnectionReceiverListener {

    lateinit var loginFragment: LoginFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Hide toolbar
        supportActionBar?.hide()

        //Internet Connection
        baseContext.registerReceiver(
            ConnectionReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        ReceiverConnection.instance.setConnectionListener(this)

        loginFragment = LoginFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.drawable_frameLayout, loginFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
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
}