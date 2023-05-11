package tech.hackcity.educarts.uitls

import android.content.Context
import android.widget.Toast

/**
 *Created by Victor Loveday on 5/10/23
 */


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}