package tech.hackcity.educarts.ui.payment.applicationfee

/**
 *Created by Victor Loveday on 6/1/23
 */
interface ApplicationFeeListener {
    fun onRequestStarted()
    fun onRequestFailed(message: String)
    fun onRequestSuccessful()
}