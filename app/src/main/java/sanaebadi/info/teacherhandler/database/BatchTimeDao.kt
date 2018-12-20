package sanaebadi.info.teacherhandler.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BatchTimeDao {
    /*insert single Time info in table*/
    @Insert
    fun insertTime(batchTime: BatchTime)

    /*insert list time info in table*/
    @Insert
    fun insertTimeList(batchTime: List<BatchTime>)

    /*delete single time info in table*/
    @Delete
    fun deleteTime(batchTime: BatchTime)

    /*delete list time info in table*/
    @Delete
    fun deleteBatchTimelist(batchTime: List<BatchTime>)

    /*query get all time info with live data*/
    @Query("SELECT * FROM batch_time")
    fun getAllBatchTime(): LiveData<List<BatchTime>>


    /*query delete item*/
    @Query("DELETE FROM batch_time")
    fun deleteBatchTime()
}