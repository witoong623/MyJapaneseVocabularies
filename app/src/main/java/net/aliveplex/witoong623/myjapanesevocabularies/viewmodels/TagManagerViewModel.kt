package net.aliveplex.witoong623.myjapanesevocabularies.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Tag
import net.aliveplex.witoong623.myjapanesevocabularies.databases.CurrentAppDb
import net.aliveplex.witoong623.myjapanesevocabularies.utils.SingleThreadExecutor

class TagManagerViewModel : ViewModel() {
    private var mTagList: LiveData<List<Tag>>? = null

    val tagList: LiveData<List<Tag>>
        get() {
            if (mTagList == null) {
                mTagList = CurrentAppDb.Dao().getTagListObservable()
            }
            return mTagList!!
        }

    fun deleteTag(tag: Tag) {
        SingleThreadExecutor.execute(Runnable {
            val dao = CurrentAppDb.Dao()
            dao.deleteTag(tag)
        })
    }
}