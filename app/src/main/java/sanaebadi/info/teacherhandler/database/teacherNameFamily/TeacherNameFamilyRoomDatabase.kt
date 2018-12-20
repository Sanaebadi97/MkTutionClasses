package sanaebadi.info.teacherhandler.database.teacherNameFamily

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TeacherNameFamily::class], version = 1)
abstract class TeacherNameFamilyRoomDatabase : RoomDatabase() {

    abstract fun teacherNameFamilyDao(): TeacherNameFamilyDao


    companion object {
        private var sInstance: TeacherNameFamilyRoomDatabase? = null

        fun getDatabase(context: Context): TeacherNameFamilyRoomDatabase {
            if (sInstance == null) {
                synchronized(RoomDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(
                            context.applicationContext,
                            TeacherNameFamilyRoomDatabase::class.java, "teacher_name_family_db"
                        ).build()
                    }
                }
            }

            return sInstance!!
        }
    }
}
