package com.example.recipeapp

import android.graphics.Bitmap
import android.util.LruCache

class Cache(maxSize: Int) : LruCache<String, Bitmap>(maxSize) {
    override fun sizeOf(key: String?, value: Bitmap): Int {
        return value.byteCount / 1024
    }

    companion object {
        var maxMemorySize = Runtime.getRuntime().maxMemory().toInt() / 1024
        var cacheSize = maxMemorySize / 10
        var mMemoryCache: LruCache<String, Bitmap> = LruCache<String, Bitmap>(cacheSize)
        fun getBitMapFromMemoryCache (key: String): Bitmap {
            return mMemoryCache.get(key)
        }
        fun setBitmapToMemoryCache (key: String, bitmap: Bitmap) {
            if (getBitMapFromMemoryCache(key) != null) {
                mMemoryCache.put(key, bitmap)
            }
        }
    }
}