package sanaebadi.info.teacherhandler.database.teacherNameFamily

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

/*Repository class should be created for server and get and put data ..*/
class TeacherNameFamilyRepository(var application: Application) {
    private fun teacherNameFamilyDb() =
        TeacherNameFamilyRoomDatabase.getDatabase(
            application
        )
    private fun teacherNameFamilyDao() = teacherNameFamilyDb().teacherNameFamilyDao()


    /*Insert and Delete should do in background not in main thread , so use AsyncTask*/
    companion object {

        /*Insert Fun in Background*/
        class InsertTeacherNameFamily(private val teacherNameFamilyDao: TeacherNameFamilyDao) :
            AsyncTask<TeacherNameFamily, Void?, Void?>() {
            override fun doInBackground(vararg params: TeacherNameFamily?): Void? {
                teacherNameFamilyDao.insertTeacherNameFamily(params[0]!!)
                return null
            }

        }

    }

    /*insert Teacher fun ... get main func from Teacher Dao interface*/
    fun insertTeacherNameFamily(teacherNameFamily: TeacherNameFamily) {
        InsertTeacherNameFamily(
            teacherNameFamilyDao()
        )
            .execute(teacherNameFamily)
    }

    /*get all student student fun ... get main func from Student Dao interface*/
    fun getAllTeacherNameFamily(): LiveData<List<TeacherNameFamily>> {
        return teacherNameFamilyDao().getAllTeacherNameFamily()
    }
}



