package net.aliveplex.witoong623.myjapanesevocabularies.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Vocabulary

class DictionaryRepository {
    private val currentDictionarySearchResults = MutableLiveData<Vocabulary>()

    fun getCurrentDictionarySearchResults(): LiveData<Vocabulary> = currentDictionarySearchResults

    fun searchVocabulary(keyword: String) {

    }
}

class DictionarySearchWorker