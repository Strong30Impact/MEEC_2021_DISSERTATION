package pt.ipca.dissertation_14861.utils.connections

import android.app.Application

/*
    Class related with network connection
*/

class ReceiverConnection: Application() {
    companion object {
        @get:Synchronized
        lateinit var instance: ReceiverConnection
    }

    override fun onCreate(){
        super.onCreate()
        instance = this
    }

    fun setConnectionListener(listener: ConnectionReceiver.ConnectionReceiverListener){
        ConnectionReceiver.connectionReceiverListener = listener
    }
}