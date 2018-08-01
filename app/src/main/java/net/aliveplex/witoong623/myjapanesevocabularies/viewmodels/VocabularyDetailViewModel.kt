package net.aliveplex.witoong623.myjapanesevocabularies.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import net.aliveplex.witoong623.myjapanesevocabularies.databases.CurrentAppDb
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Tag
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Vocabulary
import net.aliveplex.witoong623.myjapanesevocabularies.databases.VocabularyTag
import net.aliveplex.witoong623.myjapanesevocabularies.utils.SingleThreadExecutor
import java.util.*

class VocabularyDetailViewModel : ViewModel() {
    private val mVocabulary: MutableLiveData<Vocabulary> = MutableLiveData()
    private val mAvailableTag: MutableLiveData<List<Tag>> = MutableLiveData()
    private val mVocabularyTag: MutableLiveData<List<Tag>> = MutableLiveData()

    val vocabulary: LiveData<Vocabulary>
        get() = mVocabulary

    val availableTag: LiveData<List<Tag>>
        get() = mAvailableTag

    val tagOfVocabulary: LiveData<List<Tag>>
        get() = mVocabularyTag

    fun getVocabularyDetail(id: Int) {
        SingleThreadExecutor.execute(Runnable {
            val dao = CurrentAppDb.Dao()
            val vocab = dao.getVocabularyById(id)
            mVocabulary.postValue(vocab)
            refreshAvailableTags(id)
            refreshVocabularyTag(id)
        })
    }

    private fun refreshAvailableTags(vocab_id: Int) {
        val dao = CurrentAppDb.Dao()
        val tags = dao.getAvailableTagList(vocab_id)
        mAvailableTag.postValue(tags)
    }

    private fun refreshVocabularyTag(vocab_id: Int) {
        val dao = CurrentAppDb.Dao()
        val vocabTags = dao.getTagsOfVocabulary(vocab_id)
        mVocabularyTag.postValue(vocabTags)
    }

    fun tagVocabulary(tag_name: String) {
        SingleThreadExecutor.execute(Runnable {
            val dao = CurrentAppDb.Dao()
            val tag = dao.getTagByTagName(tag_name)
            val vocabId = mVocabulary.value!!.id
            val vocabTag = VocabularyTag(vocabId, tag!!.id!!, Date())
            dao.insertVocabularyTag(vocabTag)
            tag.recent_use = Date()
            dao.updateTag(tag)
            refreshAvailableTags(vocabId)
            refreshVocabularyTag(vocabId)
        })
    }

    fun deleteVocabularyTag(tag: Tag) {
        SingleThreadExecutor.execute(Runnable {
            val dao = CurrentAppDb.Dao()
            val vocabId = mVocabulary.value!!.id
            val vocabTag = VocabularyTag(vocabId, tag.id!!, null)
            dao.deleteVocabularyTag(vocabTag)
            refreshAvailableTags(vocabId)
            refreshVocabularyTag(vocabId)
        })
    }
}
