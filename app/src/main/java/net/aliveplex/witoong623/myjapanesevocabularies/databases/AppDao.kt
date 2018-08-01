package net.aliveplex.witoong623.myjapanesevocabularies.databases

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface AppDao {
    @Query("SELECT * FROM jtdic WHERE kanji like :keyword")
    fun getMeaningByKanji(keyword: String): List<Vocabulary>

    @Query("SELECT * FROM jtdic WHERE yomikata like :keyword")
    fun getMeaningByKana(keyword: String): List<Vocabulary>

    @Query("SELECT * FROM jtdic WHERE id = :id")
    fun getVocabularyById(id: Int): Vocabulary

    @Query("SELECT * FROM tags ORDER BY tag_recent_use DESC")
    fun getTagListObservable(): LiveData<List<Tag>>

    @Query("SELECT * FROM tags WHERE id not in (SELECT tag_id FROM vocabularytags WHERE vocab_id = :vocab_id) ORDER BY tag_recent_use DESC")
    fun getAvailableTagList(vocab_id: Int): List<Tag>

    @Query("SELECT * FROM tags WHERE tag_name = :name")
    fun getTagByTagName(name: String): Tag?

    @Query("SELECT * FROM tags WHERE id in (SELECT tag_id FROM vocabularytags WHERE vocab_id = :id)")
    fun getTagsOfVocabulary(id: Int): List<Tag>

    @Insert
    fun insertTag(tag: Tag)

    @Insert
    fun insertVocabularyTag(vocabTag: VocabularyTag)

    @Update
    fun updateTag(tag: Tag)

    @Delete
    fun deleteTag(tag: Tag)

    @Delete
    fun deleteVocabularyTag(vocabTag: VocabularyTag)
}
