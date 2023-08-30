package tech.hackcity.educarts.uitls

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream

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
