package com.example.demo.util

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.core.graphics.scale
import androidx.fragment.app.Fragment
import com.example.demo.R
import com.example.demo.data.Program
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Blob
import java.io.ByteArrayOutputStream

// ----------------------------------------------------------------------------
// Spinner Extensions
// ----------------------------------------------------------------------------

// Usage: Select spinner item based on condition
fun <T> Spinner.setSelection(fn: (T) -> Boolean) {
    // TODO(18): Select spinner item based on condition
    for (i in 0..< count) {
        val item = getItemAtPosition(i) as T
        if (fn(item)){
            setSelection(i)
            break
        }
    }

}

// ----------------------------------------------------------------------------
// Fragment Extensions
// ----------------------------------------------------------------------------

// Usage: Create an array adapter for spinner
fun <T> Fragment.getArrayAdapter(): ArrayAdapter<T> {
    // TODO(11B): Create an array adapter for spinner
    val adapter = ArrayAdapter<T>(context!!, android.R.layout.simple_spinner_item)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    return adapter

}

// Usage: Show a toast from fragment
fun Fragment.toast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

// Usage: Show a snackbar from fragment
fun Fragment.snackbar(text: String) {
    Snackbar.make(view!!, text, Snackbar.LENGTH_SHORT).show()
}

// Usage: Show an error dialog from fragment
fun Fragment.errorDialog(text: String) {
    AlertDialog.Builder(context)
        .setIcon(R.drawable.ic_error)
        .setTitle("Error")
        .setMessage(text)
        .setPositiveButton("Dismiss", null)
        .show()
}

// Usage: Show an information dialog from fragment
fun Fragment.infoDialog(text: String) {
    AlertDialog.Builder(context)
        .setIcon(R.drawable.ic_info)
        .setTitle("Information")
        .setMessage(text)
        .setPositiveButton("Dismiss", null)
        .show()
}

// ----------------------------------------------------------------------------
// Bitmap Extensions
// ----------------------------------------------------------------------------

// Usage: Crop and resize bitmap (upscale)
fun Bitmap.crop(width: Int, height: Int): Bitmap {
    // Source width, height and ratio
    val sw = this.width
    val sh = this.height
    val sratio = 1.0 * sw / sh

    // Target offset (x, y), width, height and ratio
    val x: Int
    val y: Int
    val w: Int
    val h: Int
    val ratio = 1.0 * width / height

    if (ratio >= sratio) {
        // Retain width, calculate height
        w = sw
        h = (sw / ratio).toInt()
        x = 0
        y = (sh - h) / 2
    }
    else {
        // Retain height, calculate width
        w = (sh * ratio).toInt()
        h = sh
        x = (sw - w) / 2
        y = 0
    }

    return Bitmap
        .createBitmap(this, x, y, w, h) // Crop
        .scale(width, height) // Resize
}

// Usage: Convert from Bitmap to Firebase Blob
fun Bitmap.toBlob(): Blob {
    ByteArrayOutputStream().use {
        compress(Bitmap.CompressFormat.WEBP_LOSSY, 80, it)
        return Blob.fromBytes(it.toByteArray())
    }
}

// ----------------------------------------------------------------------------
// Firebase Blob Extensions
// ----------------------------------------------------------------------------

// Usage: Convert from Blob to Bitmap
fun Blob.toBitmap(): Bitmap? {
    val bytes = toBytes()
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

// ----------------------------------------------------------------------------
// ImageView Extensions
// ----------------------------------------------------------------------------

// Usage: Crop to Firebase Blob
fun ImageView.cropToBlob(width: Int, height: Int): Blob {
    return drawable?.toBitmapOrNull()?.crop(width, height)?.toBlob() ?: Blob.fromBytes(ByteArray(0))
}

// Usage: Load Firebase Blob
fun ImageView.setImageBlob(blob: Blob) {
    setImageBitmap(blob.toBitmap())
}
