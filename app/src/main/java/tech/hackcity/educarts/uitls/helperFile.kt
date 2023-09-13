package tech.hackcity.educarts.uitls

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.io.InputStream

/**
 *Created by Victor Loveday on 7/7/23
 */

fun createFilePart(context: Context, name: String, fileUri: Uri?): MultipartBody.Part? {
    if (fileUri != null) {
        val file = fileUri.toImageFile(context)
        val fileRequestBody = file?.let {
            RequestBody.create(
                "image/*".toMediaTypeOrNull(),
                it
            )
        }
        return fileRequestBody?.let {
            MultipartBody.Part.createFormData(
                name,
                file.name,
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


@Throws(IOException::class)
fun uriToFile(context: Context, uri: Uri): File {
    val destinationFilename = File(
        context.filesDir.path + File.separatorChar + queryName(context, uri)
    )
    context.contentResolver.openInputStream(uri)?.use { ins ->
        createFileFromStream(ins, destinationFilename)
    } ?: throw IOException("Failed to open InputStream for URI: $uri")
    return destinationFilename
}

private fun createFileFromStream(ins: InputStream, destination: File) {
    destination.outputStream().use { os ->
        val buffer = ByteArray(4096)
        var length: Int
        while (ins.read(buffer).also { length = it } > 0) {
            os.write(buffer, 0, length)
        }
        os.flush()
    }
}

private fun queryName(context: Context, uri: Uri): String {
    context.contentResolver.query(
        uri,
        null,
        null,
        null,
        null
    )?.use { returnCursor ->
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        return returnCursor.getString(nameIndex)
    }
    throw IllegalArgumentException("Failed to query name for URI: $uri")
}

fun fileToBytes(file: File): ByteArray {
    return file.inputStream().use { inputStream ->
        inputStream.readBytes()
    }
}