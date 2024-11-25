package com.mrsy.commons.extensions

import android.content.Context
import com.mrsy.commons.models.FileDirItem

fun FileDirItem.isRecycleBinPath(context: Context): Boolean {
    return path.startsWith(context.recycleBinPath)
}
