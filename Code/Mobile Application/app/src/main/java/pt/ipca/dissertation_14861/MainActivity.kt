package pt.ipca.dissertation_14861

import android.app.AlertDialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pt.ipca.dissertation_14861.Utils.Alerts
import pt.ipca.dissertation_14861.Utils.connections.ConnectionReceiver
import pt.ipca.dissertation_14861.Utils.connections.ReceiverConnection

class MainActivity : AppCompatActivity(), ConnectionReceiver.ConnectionReceiverListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Internet Connection
        baseContext.registerReceiver(
            ConnectionReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        ReceiverConnection.instance.setConnectionListener(this)

    }

    /*
    Function that checks the Internet connection
    */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if(!isConnected){
            var alert = Alerts()
            val builder = AlertDialog.Builder(this)
            alert.showAlertConnectionInternet(builder, this)
        }
    }
}