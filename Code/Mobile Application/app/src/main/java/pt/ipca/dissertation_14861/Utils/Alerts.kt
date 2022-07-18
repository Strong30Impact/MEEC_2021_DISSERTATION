package pt.ipca.dissertation_14861.Utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import pt.ipca.dissertation_14861.R

class Alerts: AppCompatActivity() {

    fun showAlertConnectionInternet(builder: AlertDialog.Builder, context: Context){
        builder.setTitle("No internet connection")
        builder.setMessage("Please choose the type of internet connection you want.")
        builder.setCancelable(true)
        builder.setPositiveButton(
            R.string.wifi,
            DialogInterface.OnClickListener { dialog, id ->
                try {
                    context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
            })

        builder.setNegativeButton(R.string.mobiledata,
            DialogInterface.OnClickListener { dialog, id ->
                try {
                    context.startActivity(Intent(Settings.ACTION_DATA_USAGE_SETTINGS))
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
            })
        val dialog: AlertDialog = builder.create()

        dialog.show()
    }

    
/*
    fun showAlertConnectionError(builder: AlertDialog.Builder, msg: String) {
        builder.setTitle("No connection")
        builder.setMessage("Please set up a connection to the $msg")
        builder.setCancelable(true)
        builder.setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.cancel()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }*/
}
