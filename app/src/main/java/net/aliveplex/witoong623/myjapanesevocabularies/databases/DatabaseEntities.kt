package net.aliveplex.witoong623.myjapanesevocabularies.databases

import android.arch.persistence.room.*
import java.util.*

@Entity(tableName = "jtdic")
data class Vocabulary(@PrimaryKey val id: Int,
                      @ColumnInfo(name = "kanji") val vocab: String,
                      @ColumnInfo(name = "yomikata") val reading: String,
                      @ColumnInfo(name = "thai") val meaning: String,
                      val type: String)

@Entity(tableName = "tags")
@TypeConverters(Converters::class)
data class Tag(@ColumnInfo(name = "tag_name") val tag_name: String,
               @ColumnInfo(name = "tag_description") val description: String?,
               @ColumnInfo(name = "tag_recent_use") var recent_use: Date?,
               @PrimaryKey(autoGenerate = true) val id: Int? = null)

@Entity(tableName = "vocabularytags",
        primaryKeys = ["vocab_id", "tag_id"],
        foreignKeys = [ForeignKey(entity = Vocabulary::class,
                                  parentColumns = ["id"],
                                  childColumns = ["vocab_id"],
                                  onDelete = ForeignKey.CASCADE),
                       ForeignKey(entity = Tag::class,
                                  parentColumns = ["id"],
                                  childColumns = ["tag_id"],
                                  onDelete = ForeignKey.CASCADE)])
@TypeConverters(Converters::class)
data class VocabularyTag(val vocab_id: Int,
                         val tag_id: Int,
                         val added_date: Date?)