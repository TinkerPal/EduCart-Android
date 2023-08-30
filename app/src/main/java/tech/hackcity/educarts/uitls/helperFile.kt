package tech.hackcity.educarts.uitls

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream

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

//fun Uri.toImageFile(context: Context): File? {
//    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//    val cursor = context.contentResolver.query(this, filePathColumn, null, null, null)
//    if (cursor != null) {
//        if (cursor.moveToFirst()) {
//            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
//            val filePath = cursor.getString(columnIndex)
//            cursor.close()
//            return File(filePath)
//        }
//        cursor.close()
//    }
//    return null
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
