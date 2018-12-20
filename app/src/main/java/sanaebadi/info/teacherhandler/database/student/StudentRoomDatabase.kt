package sanaebadi.info.teacherhandler.database.student

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentRoomDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        private var sInstance: StudentRoomDatabase? = null

        fun getDatabase(context: Context): StudentRoomDatabase {
            if (sInstance == null) {
                synchronized(RoomDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(
                            context.applicationContext,
                            StudentRoomDatabase::class.java, "student_db"
                        ).build()
                    }
                }
            }

            return sInstance!!
        }
    }
}
