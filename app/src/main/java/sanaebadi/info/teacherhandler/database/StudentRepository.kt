package sanaebadi.info.teacherhandler.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

/*Repository class should be created for server and get and put data ..*/
class StudentRepository(var application: Application) {
    private fun studentDb() = StudentRoomDatabase.getDatabase(application)
    private fun studentDao() = studentDb().studentDao()


    /*Insert and Delete should do in background not in main thread , so use AsyncTask*/
    companion object {

        /*Insert Fun in Background*/
        class InsertStuInfo(private val studentDao: StudentDao) : AsyncTask<Student, Void?, Void?>() {
            override fun doInBackground(vararg params: Student?): Void? {
                studentDao.insertStuInfo(params[0]!!)
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
        class DeleteStuInfo(private val studentDao: StudentDao) : AsyncTask<Int, Void?, Void?>() {
            override fun doInBackground(vararg params: Int?): Void? {
                studentDao.deleteStuInfo(params[0]!!)
                return null
            }

        }

    }

    /*insert student fun ... get main func from Student Dao interface*/
    fun insertStudentInfo(student: Student) {
        InsertStuInfo(studentDao()).execute(student)
    }


    /*delete student fun ... get main func from Student Dao interface*/
//    fun deleteStuInfo() {
//        DeleteStuInfo(studentDao()).execute()
//    }
    fun deleteStuInfo(id: Int) {
        DeleteStuInfo(studentDao()).execute(id)
    }


    /*get all student student fun ... get main func from Student Dao interface*/
    fun getAllStuInfo(): LiveData<List<Student>> {
        return studentDao().getAllStuInfo()
    }
}
