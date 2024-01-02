package hr.algebra.cryptotracker.handler

import android.content.Context
import android.util.Log
import hr.algebra.cryptotracker.factory.createHttpGetUrlConnection
import java.io.File
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths

fun downloadImageAndStore(context: Context, url: String) : String? {
    val fileName = url.substring(url.lastIndexOf(File.separatorChar) + 1, url.lastIndexOf('?'))
    val file: File = createLocalFile(context, fileName)

    try {
        val con: HttpURLConnection = createHttpGetUrlConnection(url)
        Files.copy(con.inputStream, Paths.get(file.toURI()))

        return file.absolutePath
    }catch (e: Exception){
        Log.e("IMAGES HANDLER", e.toString(), e)
    }

    return null
}

fun createLocalFile(context: Context, fileName: String): File {
    val dir = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, fileName)

    if(file.exists()) file.delete()

    return file
}
