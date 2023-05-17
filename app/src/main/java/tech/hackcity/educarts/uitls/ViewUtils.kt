package tech.hackcity.educarts.uitls

import android.content.Context
import android.view.View
import android.widget.Toast

/**
 *Created by Victor Loveday on 5/10/23
 */


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun hideViews(views: List<View>) {
    for (view in views) {
        view.visibility = View.INVISIBLE
    }
}

fun showViews(views: List<View>) {
    for (view in views) {
        view.visibility = View.VISIBLE
    }
}