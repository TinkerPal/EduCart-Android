package tech.hackcity.educarts.ui.settings.pin

/**
 *Created by Victor Loveday on 5/31/23
 */
interface CreatePinListener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
}