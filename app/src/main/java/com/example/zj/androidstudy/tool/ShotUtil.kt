package com.example.zj.androidstudy.tool

import android.app.Activity
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.LruCache
import android.view.PixelCopy
import android.view.View
import android.view.View.MeasureSpec
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

/**
 * 截屏工具
 * ref: https://cloud.tencent.com/developer/article/1735861
 */
object ShotUtil {
    /**
     * shot the current screen ,with the status but the status is trans
     */
    @Deprecated("使用getDrawingCache方法获取截屏已过时，目前采用Canvas或Pixel方式获取")
    fun getBitmap(activity: Activity): Bitmap {
        val view: View = activity.getWindow().getDecorView()
        view.setDrawingCacheEnabled(true)
        view.buildDrawingCache()
        val bp: Bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(),
                view.getMeasuredHeight())
        view.setDrawingCacheEnabled(false)
        view.destroyDrawingCache()
        return bp
    }

    fun getBitmap(v: View?): Bitmap? {
        if (null == v) {
            return null
        }
        v.isDrawingCacheEnabled = true
        v.buildDrawingCache()
        v.measure(MeasureSpec.makeMeasureSpec(v.width,
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                v.height, MeasureSpec.EXACTLY))
        v.layout(v.x.toInt(), v.y.toInt(),
                v.x.toInt() + v.measuredWidth,
                v.y.toInt() + v.measuredHeight)
        val b = Bitmap.createBitmap(v.drawingCache, 0, 0, v.measuredWidth, v.measuredHeight)
        v.isDrawingCacheEnabled = false
        v.destroyDrawingCache()
        return b
    }

    fun shotView(view: View, defaultColor: Int = Color.TRANSPARENT): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(defaultColor)
        view.draw(canvas)
        return bitmap
    }

    fun shotView(view: View, activity: Activity, callback: (Bitmap) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.window?.let { window ->
                val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
                val locationOfViewInWindow = IntArray(2)
                view.getLocationInWindow(locationOfViewInWindow)
                try {
                    PixelCopy.request(window, Rect(locationOfViewInWindow[0], locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width, locationOfViewInWindow[1] + view.height), bitmap, { copyResult ->
                        if (copyResult == PixelCopy.SUCCESS) {
                            callback(bitmap)
                        }
                        // possible to handle other result codes ...
                    }, Handler(Looper.getMainLooper()))
                } catch (e: IllegalArgumentException) {
                    // PixelCopy may throw IllegalArgumentException, make sure to handle it
                    e.printStackTrace()
                }
            }
        }
    }

    fun shotScrollView(scrollView: NestedScrollView): Bitmap? {
        var h = 0
        var bitmap: Bitmap? = null
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"))
        }
        bitmap = Bitmap.createBitmap(scrollView.width, h, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        scrollView.draw(canvas)
        return bitmap
    }

    fun shotRecyclerView(view: RecyclerView): Bitmap? {
        val adapter = view.adapter
        var bigBitmap: Bitmap? = null
        if (adapter != null) {
            val size = adapter.itemCount
            var height = 0
            val paint = Paint()
            var iHeight = 0f
            val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
            // Use 1/8th of the available memory for this memory cache.
            val cacheSize = maxMemory / 8
            val bitmapCache: LruCache<String, Bitmap> = LruCache(cacheSize)
            for (i in 0 until size) {
                val holder = adapter.createViewHolder(view, adapter.getItemViewType(i))
                adapter.onBindViewHolder(holder, i)
                holder.itemView.measure(
                        MeasureSpec.makeMeasureSpec(view.width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
                holder.itemView.layout(0, 0, holder.itemView.measuredWidth,
                        holder.itemView.measuredHeight)
                holder.itemView.isDrawingCacheEnabled = true
                holder.itemView.buildDrawingCache()
                val drawingCache = holder.itemView.drawingCache
                if (drawingCache != null) {
                    bitmapCache.put(i.toString(), drawingCache)
                }
                height += holder.itemView.measuredHeight
            }
            bigBitmap = Bitmap.createBitmap(view.measuredWidth, height, Bitmap.Config.ARGB_8888)
            val bigCanvas = Canvas(bigBitmap)
            val lBackground: Drawable? = view.background
            if (lBackground != null && lBackground is ColorDrawable) {
                val lColorDrawable: ColorDrawable = lBackground
                val lColor: Int = lColorDrawable.color
                bigCanvas.drawColor(lColor)
            }
            for (i in 0 until size) {
                val bitmap: Bitmap = bitmapCache.get(i.toString())
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint)
                iHeight += bitmap.height
                bitmap.recycle()
            }
        }
        return bigBitmap
    }
}