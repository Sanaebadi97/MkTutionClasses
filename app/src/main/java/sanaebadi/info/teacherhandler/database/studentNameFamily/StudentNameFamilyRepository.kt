package sanaebadi.info.teacherhandler.database.studentNameFamily

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

/*Repository class should be created for server and get and put data ..*/
class StudentNameFamilyRepository(var application: Application) {
    private fun studentNameFamilyDb() =
        StudentNameFamilyRoomDatabase.getDatabase(
            application
        )
    private fun studentNameFamilyDao() = studentNameFamilyDb().studentNameFamilyDao()

    /*Insert and Delete should do in background not in main thread , so use AsyncTask*/
    companion object {

        /*Insert Fun in Background*/
        class InsertStuNameFamily(private val studentNameFamilyDao: StudentNameFamilyDao) :
            AsyncTask<StudentNameFamily, Void?, Void?>() {
            override fun doInBackground(vararg params: StudentNameFamily?): Void? {
                studentNameFamilyDao.insertStuNameFamily(params[0]!!)
                return null
            }

        }

        /*Delete Fun in Background*/

        //        class DeleteStuInfo(private val studentDao: StudentDao) : AsyncTask<Student, Void?, Void?>() {
//            override fun doInBackground(vararg params: Student?): Void? {
//                studentDao.deleteStuInfo()
//                return null
//            }
//
//        }
        class DeleteStuNameFamily(private val studentNameFamilyDao: StudentNameFamilyDao) :
            AsyncTask<Int, Void?, Void?>() {
            override fun doInBackground(vararg params: Int?): Void? {
                studentNameFamilyDao.deleteStuNameFamily(params[0]!!)
                return null
            }

        }

    }

    /*insert student fun ... get main func from Student Dao interface*/
    fun insertStudentNameFamily(studentNameFamily: StudentNameFamily) {
        InsertStuNameFamily(
            studentNameFamilyDao()
        ).execute(studentNameFamily)
    }

    /*delete student fun ... get main func from Student Dao interface*/
//    fun deleteStuInfo() {
//        DeleteStuInfo(studentDao()).execute()
//    }


//    fun deleteStuInfo(id: Int) {
//        StudentNameFamilyRepository.Companion.DeleteStuNameFamily(studentNameFamilyDao()).execute(id)
//    }


    /*get all student student fun ... get main func from Student Dao interface*/
    fun getAllStuNameFamily(): LiveData<List<StudentNameFamily>> {
        return studentNameFamilyDao().getAllStuNameFamily()
    }
}