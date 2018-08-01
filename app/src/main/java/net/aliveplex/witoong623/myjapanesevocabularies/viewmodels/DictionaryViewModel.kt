package net.aliveplex.witoong623.myjapanesevocabularies.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import net.aliveplex.witoong623.myjapanesevocabularies.databases.CurrentAppDb
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Vocabulary
import net.aliveplex.witoong623.myjapanesevocabularies.utils.isAllKana
import net.aliveplex.witoong623.myjapanesevocabularies.utils.isAllKanji

class DictionaryViewModel : ViewModel() {
    private val foundVocabs: MutableLiveData<List<Vocabulary>> = MutableLiveData()

    fun getFoundVocabs(): LiveData<List<Vocabulary>> {
        return foundVocabs
    }

    fun init() {
        val vocabs: List<Vocabulary> = listOf(
                Vocabulary(1, "負ける", "まける", "แพ้", "v1,vi"),
                Vocabulary(2, "勝つ", "勝つ", "ชนะ", "v1,vi"),
                Vocabulary(3, "好き", "すき", "ชอบ", "adj-na,n")
        )
        foundVocabs.value = vocabs
    }

    fun searchKeyword(keyword: String) {
        val dao = CurrentAppDb.Dao()
        var result: List<Vocabulary>
        if (keyword.isAllKana) {
            result = dao.getMeaningByKana(keyword)
        } else {
            result = dao.getMeaningByKanji(keyword)
        }
        foundVocabs.postValue(result)
    }
}
