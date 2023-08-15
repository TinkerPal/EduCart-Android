package tech.hackcity.educarts.uitls

import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 *Created by Victor Loveday on 7/7/23
 */

fun createPartFromString(string: String): RequestBody {
    return string.toRequestBody("multipart/form-data".toMediaType())
}

fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
    val file = File(fileUri.path)
    val requestFile = file.asRequestBody("multipart/form-data".toMediaType())
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}

