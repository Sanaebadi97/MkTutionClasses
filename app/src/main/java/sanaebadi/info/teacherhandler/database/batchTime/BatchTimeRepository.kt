package sanaebadi.info.teacherhandler.database.batchTime

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class BatchTimeRepository(val application: Application) {
    private fun timeBatchDb() =
        BatchTimeRoomDatabase.getDatabase(application)

    private fun timeBatchDao() = timeBatchDb().batchTimeDao()

    /*Insert and Delete should do in background not in main thread , so use AsyncTask*/
    companion object {

        /*Insert Fun in Background*/
        class InsertTime(private val batchTimeDao: BatchTimeDao) : AsyncTask<BatchTime, Void?, Void?>() {
            override fun doInBackground(vararg params: BatchTime?): Void? {
                batchTimeDao.insertTime(params[0]!!)
                return null
            }

        }

        /*Delete Fun in Background*/

        class DeleteBatchTime(private val timeBatchDao: BatchTimeDao) : AsyncTask<BatchTime, Void?, Void?>() {
            override fun doInBackground(vararg params: BatchTime?): Void? {
                timeBatchDao.deleteBatchTime(params[0]!!)
                return null
            }

        }


    }

    /*insert time fun ... get main func from Time batch Dao interface*/
    fun insertBatchTime(batchTime: BatchTime) {
        InsertTime(timeBatchDao()).execute(batchTime)
    }


    /*delete time fun ... get main func from Time batch Dao interface*/
    fun deleteTimeBatch(batchTime: BatchTime) {
        DeleteBatchTime(timeBatchDao()).execute(batchTime)
    }


    /*get all Time student fun ... get main func from Time Dao interface*/
    fun getAllTime(): LiveData<List<BatchTime>> {
        return timeBatchDao().getAllBatchTime()
    }
}

