package net.aliveplex.witoong623.myjapanesevocabularies.interfaces

import android.view.View

interface ClickListener {
    fun onClick(view: View, position: Int)

    fun onLongClick(view: View, position: Int)
}