package sk.rafig.mhdke.util

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

object Barcoder {
    fun createQrCodeFromString(text: String): Bitmap {
        var bitmap: Bitmap
        try {
            val bits: BitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 200, 200)
            val encoder = BarcodeEncoder()
            bitmap = encoder.createBitmap(bits)

        } catch (e: WriterException) {
            bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
            Log.e("ZGING", "Write Error", e)
        }

        return bitmap
    }

    fun fillViewWithBarcodeFromString(view: ImageView, text: String) {
        view.setImageBitmap(createQrCodeFromString(text))
    }
}