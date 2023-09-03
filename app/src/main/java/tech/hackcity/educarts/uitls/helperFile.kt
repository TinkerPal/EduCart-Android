package tech.hackcity.educarts.uitls

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.core.net.toFile
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream
import android.content.ContentResolver

/**
 *Created by Victor Loveday on 7/7/23
 */

fun createFilePart(context: Context, name: String, fileUri: Uri?): MultipartBody.Part? {
    if (fileUri != null) {
        val profilePictureFile = fileUri.toImageFile(context)
        val profilePictureRequestBody = profilePictureFile?.let {
            RequestBody.create(
                "image/*".toMediaTypeOrNull(),
                it
            )
        }
        return profilePictureRequestBody?.let {
            MultipartBody.Part.createFormData(
                name,
                profilePictureFile.name,
                it
            )
        }
    }
    return null
}


//fun createFilePart(context: Context, name: String, fileUri: Uri?): MultipartBody.Part? {
//    if (fileUri != null) {
//        val contentResolver: ContentResolver = context.contentResolver
//        val mediaType = getMediaType(contentResolver, fileUri)
//        val profilePictureRequestBody = getFileRequestBody(context, contentResolver, fileUri)
//        if (profilePictureRequestBody != null) {
//            return MultipartBody.Part.createFormData(
//                name,
//                getFileName(contentResolver, fileUri),
//                profilePictureRequestBody
//            )
//        }
//    }
//    return null
//}
//
//private fun getMediaType(contentResolver: ContentResolver, uri: Uri): MediaType {
//    // Determine media type based on URI
//    val mimeType = contentResolver.getType(uri)
//    return mimeType?.toMediaTypeOrNull() ?: "application/octet-stream".toMediaType()
//}
//
//private fun getFileRequestBody(context: Context, contentResolver: ContentResolver, uri: Uri): RequestBody? {
//    try {
//        val inputStream = contentResolver.openInputStream(uri)
//        if (inputStream != null) {
//            val file = File(context.cacheDir, getFileName(contentResolver, uri))
//            file.copyInputStreamToFile(inputStream)
//            return RequestBody.create(getMediaType(contentResolver, uri), file)
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//    return null
//}
//
//@SuppressLint("Range")
//private fun getFileName(contentResolver: ContentResolver, uri: Uri): String {
//    // Extract the file name from the URI
//    val cursor = contentResolver.query(uri, null, null, null, null)
//    return try {
//        if (cursor != null && cursor.moveToFirst()) {
//            val displayName = cursor.getString(cursor.getColumnIndex("_display_name"))
//            displayName ?: "file"
//        } else {
//            "file"
//        }
//    } finally {
//        cursor?.close()
//    }
//}


fun Uri?.toImageFile(context: Context): File? {
    if (this == null) {
        return null
    }
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(this)
    val file = File(context.cacheDir, "temp_image.jpg")
    file.copyInputStreamToFile(inputStream)
    return file
}

fun File.copyInputStreamToFile(inputStream: InputStream?) {
    this.outputStream().use { fileOut ->
        inputStream?.copyTo(fileOut)
    }
}
