package tech.hackcity.educarts.uitls

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

/**
 *Created by Victor Loveday on 9/6/23
 */

@RequiresApi(Build.VERSION_CODES.N)
fun formatDateTime(
    dateTimeString: String,
    showDate: Boolean = true,
    showTime: Boolean = true
): String {
    // Parse the input date-time string
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date = inputFormat.parse(dateTimeString)

    // Format date
    val outputFormatDate = if (showDate) "MMMM d, yyyy" else ""
    val formattedDate = if (showDate) {
        SimpleDateFormat(outputFormatDate).format(date)
    } else {
        ""
    }

    // Format time
    val outputFormatTime = if (showTime) "h:mm a" else ""
    val timeZone = TimeZone.getTimeZone("UTC")
    val timeFormat = SimpleDateFormat(outputFormatTime)
    timeFormat.timeZone = timeZone
    val formattedTime = if (showTime) {
        timeFormat.format(date)
    } else {
        ""
    }

    // Combine date and time if both are shown
    val separator = if (showDate && showTime) " - " else ""
    return "$formattedDate$separator$formattedTime"
}
