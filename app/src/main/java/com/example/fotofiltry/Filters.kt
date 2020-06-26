package com.example.fotofiltry

import android.content.Context
import android.graphics.*
import android.media.ExifInterface
import android.renderscript.*
import java.io.FileOutputStream
import kotlin.math.round


object Filters {


    fun blurFilter(context: Context, inputPath: String, outputPath: String) {
        val BITMAP_SCALE = 0.7f;
        val BLUR_RADIUS = 15f;
        val originalBitmap = BitmapFactory.decodeFile(inputPath)
        var width = round(originalBitmap.width * BITMAP_SCALE)
        var height = (originalBitmap.height * BITMAP_SCALE)
        val outputBitmap: Bitmap = Bitmap.createBitmap(originalBitmap)


        val rs = RenderScript.create(context)
        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, originalBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(BLUR_RADIUS)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        val outputStream = FileOutputStream(outputPath)
        outputBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        val exifI = ExifInterface(inputPath)
        val newExifI = ExifInterface(outputPath)
        newExifI.setAttribute(
            ExifInterface.TAG_ORIENTATION,
            exifI.getAttribute(ExifInterface.TAG_ORIENTATION)
        )
        newExifI.saveAttributes()


    }

    fun grayscaleFilter(inputPath: String):Bitmap {
        val originalBitmap = BitmapFactory.decodeFile(inputPath)
        var width = originalBitmap.width
        var height = originalBitmap.height
        val modifiedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(modifiedBitmap)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val colorMatrixFilter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = colorMatrixFilter
        canvas.drawBitmap(originalBitmap, 0f,0f, paint)
        val exifI = ExifInterface(inputPath)
        val rotation = exifToDegrees(exifI.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL))
        val matrix =  Matrix();
        if (rotation != 0) {matrix.preRotate(rotation.toFloat())}
        val rotatedBitmap = Bitmap.createBitmap(modifiedBitmap,0,0,width,height,matrix,false)
        return rotatedBitmap
    }

    fun sharpFilter(context: Context, inputPath: String, outputPath: String) {
        val sharp_values = floatArrayOf(0f, -1f, 0f, -1f, 5f, -1f, 0f, -1f, 0f)


        val originalBitmap = BitmapFactory.decodeFile(inputPath)
        var width = originalBitmap.width
        var height = originalBitmap.height
        val modifiedBitmap = Bitmap.createBitmap(
            width, height, originalBitmap.config
        )

        val renderScript = RenderScript.create(context)

        val input = Allocation.createFromBitmap(renderScript, originalBitmap)
        val output = Allocation.createFromBitmap(renderScript, modifiedBitmap)

        val convolution = ScriptIntrinsicConvolve3x3
            .create(renderScript, Element.U8_4(renderScript))
        convolution.setInput(input)
        convolution.setCoefficients(sharp_values)
        convolution.forEach(output)

        output.copyTo(modifiedBitmap)
        renderScript.destroy()
        val exifI = ExifInterface(inputPath)
        val rotation = exifToDegrees(exifI.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL))
        val matrix =  Matrix();
        if (rotation != 0) {matrix.preRotate(rotation as Float)}
        val rotatedBitmap = Bitmap.createBitmap(modifiedBitmap,0,0,width,height,matrix,false)
        val outputStream = FileOutputStream(outputPath)
        modifiedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

    }
    private fun exifToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0
    }
}