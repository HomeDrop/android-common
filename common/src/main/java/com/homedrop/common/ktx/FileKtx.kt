package com.homedrop.common.ktx

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.net.toFile
import java.io.File

private const val LARGE_FILE_SIZE = 500 //in MegaBytes
private const val SMALL_FILE_SIZE = 1 //in KiloBytes

val Uri.size get() = toFile().size
val File.size get() = if (!exists()) 0 else length()
private val File.sizeInKb get() = size / 1000
private val File.sizeInMb get() = sizeInKb / 1000
val File.isLargeFile get() = sizeInMb > LARGE_FILE_SIZE

/**
 * Size value should be in bytes
 * */
private val Long.sizeInKb get() = this / 1000
private val Long.sizeInMb get() = sizeInKb / 1000
val Long.isLargeFile get() = sizeInMb > LARGE_FILE_SIZE
val Long.isSmallFile get() = sizeInKb < SMALL_FILE_SIZE

fun Uri.getMimeType(context: Context): String? {
    var type = context.contentResolver.getType(this)
    if (type == null) {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(this.toString())
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.lowercase())
    }
    return type
}

fun Collection<Uri>.getMimeTypes(context: Context): Array<String> {
    val arr = arrayListOf<String>()
    forEach {
        val mimeType = it.getMimeType(context)
        if (!mimeType.isNullOrEmpty() && !arr.contains(mimeType)) {
            arr.add(mimeType)
        }
    }
    return arr.toTypedArray()
}