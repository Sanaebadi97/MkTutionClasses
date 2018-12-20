package sanaebadi.info.teacherhandler.database.query

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class QuerysRepository(var application: Application) {
    private fun queryDb() =
        QuerysRoomDatabase.getDatabase(application)

    private fun queryDao() = queryDb().queryDao()


    /*Insert and Delete should do in background not in main thread , so use AsyncTask*/
    companion object {

        /*Insert Fun in Background*/
        class InsertQuesry(private val querysDao: QuerysDao) : AsyncTask<Querys, Void?, Void?>() {
            override fun doInBackground(vararg params: Querys?): Void? {
                querysDao.insertQuery(params[0]!!)
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
//        class DeleteStuInfo(private val studentDao: StudentDao) : AsyncTask<Int, Void?, Void?>() {
//            override fun doInBackground(vararg params: Int?): Void? {
//                studentDao.deleteStuInfo(params[0]!!)
//                return null
//            }
//
//        }

    }

    /*insert query fun ... get main func from Query Dao interface*/
    fun insertQuery(querys: Querys) {
        InsertQuesry(queryDao()).execute(querys)
    }


    /*delete student fun ... get main func from Student Dao interface*/
//    fun deleteStuInfo() {
//        DeleteStuInfo(studentDao()).execute()
//    }
//    fun deleteStuInfo(id: Int) {
//        DeleteStuInfo(studentDao()).execute(id)
//    }


    /*get all query student fun ... get main func from Query Dao interface*/
    fun getAllQuery(): LiveData<List<Querys>> {
        return queryDao().getAllQuerys()
    }
}