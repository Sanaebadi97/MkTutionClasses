package sanaebadi.info.teacherhandler.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import sanaebadi.info.teacherhandler.database.BatchTimeRepository
import sanaebadi.info.teacherhandler.database.BatchTime

class BatchTimeViewModel(application: Application) : AndroidViewModel(application) {
    private val batchTimeRepository: BatchTimeRepository = BatchTimeRepository(application)
    val allTime: LiveData<List<BatchTime>> = batchTimeRepository.getAllTime()


    /*insert Time */
    fun insertTime(batchTime: BatchTime) {
        batchTimeRepository.insertBatchTime(batchTime)
    }


    /*delete time*/
    fun deleteTime() {
        batchTimeRepository.deleteTimeBatch()
    }
}