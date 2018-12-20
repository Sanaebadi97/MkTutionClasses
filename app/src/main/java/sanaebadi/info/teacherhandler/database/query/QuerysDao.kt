package sanaebadi.info.teacherhandler.database.query

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import sanaebadi.info.teacherhandler.database.student.Student

@Dao
interface QuerysDao {

    /*insert single query info in table*/
    @Insert
    fun insertQuery(query: Querys)

    /*insert list query info in table*/
    @Insert
    fun insertQuery(query: List<Querys>)

    /*delete single query info in table*/
    @Delete
    fun deleteQuery(query: Querys)

    /*delete list query info in table*/
    @Delete
    fun deleteQuery(query: List<Querys>)

    /*query get all query info with live data*/
    @Query("SELECT * FROM querys")
    fun getAllQuerys(): LiveData<List<Querys>>


//    /*query delete item*/
//    @Querys("DELETE FROM student_info")
//    fun deleteStuInfo()

//    @Query("DELETE FROM querys WHERE query_id = :stuId")
//    fun deleteStuInfo(stuId: Int)
}
