package sanaebadi.info.teacherhandler.database.studentNameFamily

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudentNameFamily::class], version = 1)
abstract class StudentNameFamilyRoomDatabase : RoomDatabase() {

    abstract fun studentNameFamilyDao(): StudentNameFamilyDao

    companion object {
        private var sInstance: StudentNameFamilyRoomDatabase? = null

        fun getDatabase(context: Context): StudentNameFamilyRoomDatabase {
            if (sInstance == null) {
                synchronized(RoomDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(
                            context.applicationContext,
                            StudentNameFamilyRoomDatabase::class.java, "student_name_family_db"
                        ).build()
                    }
                }
            }

            return sInstance!!
        }
    }

}