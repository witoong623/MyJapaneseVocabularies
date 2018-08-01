package net.aliveplex.witoong623.myjapanesevocabularies.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import net.aliveplex.witoong623.myjapanesevocabularies.databases.CurrentAppDb
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Tag

class AddTagViewModel : ViewModel() {
    val mAddtagResult = MutableLiveData<AddTagResult>()

    val addTagResultObserable: LiveData<AddTagResult>
        get() = mAddtagResult

    fun addNewTag(tag: Tag) {
        AsyncTask.execute {
            val dao = CurrentAppDb.Dao()
            dao.insertTag(tag)
            // here nothing happen means success
            mAddtagResult.postValue(AddTagResult(AddTagStatus.Success, null))
        }
    }
}

data class AddTagResult(val status: AddTagStatus, val msg: String?)

enum class AddTagStatus {
    Success, Error
}
