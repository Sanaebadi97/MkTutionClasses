package sanaebadi.info.teacherhandler.database.query

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Querys::class], version = 1)
abstract class QuerysRoomDatabase : RoomDatabase() {

    abstract fun queryDao(): QuerysDao

    companion object {
        private var sInstance: QuerysRoomDatabase? = null

        fun getDatabase(context: Context): QuerysRoomDatabase {
            if (sInstance == null) {
                synchronized(RoomDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(
                            context.applicationContext,
                            QuerysRoomDatabase::class.java, "querys_db"
                        ).build()
                    }
                }
            }

            return sInstance!!
        }
    }
}