package sk.rafig.mhdke.util

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

object Barcoder {
    fun createBarcodeFromString(text: String): Bitmap {
        var bitmap: Bitmap
        try {
            val bits: BitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, 100, 250)
            val encoder = BarcodeEncoder()
            bitmap = encoder.createBitmap(bits)

        } catch (e: WriterException) {
            bitmap = Bitmap.createBitmap(140, 60, Bitmap.Config.ALPHA_8)
            Log.e("ZGING", "Write Error", e)
        }

        return bitmap
    }

    fun fillViewWithBarcodeFromString(view: ImageView, text: String) {
        view.setImageBitmap(createBarcodeFromString(text))
    }
}