package net.aliveplex.witoong623.myjapanesevocabularies.databases

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory
import splitties.init.appCtx

@Database(entities = [Vocabulary::class, Tag::class, VocabularyTag::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}

object CurrentAppDb {
    private val appDb: AppDatabase = Room.databaseBuilder(appCtx,
            AppDatabase::class.java,
            "jtdicdb.db")
            .openHelperFactory(AssetSQLiteOpenHelperFactory())
            .build()
    fun Dao(): AppDao = appDb.appDao()
}