package sanaebadi.info.teacherhandler.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentNameFamilyDao {
    /*insert single student info in table*/
    @Insert
    fun insertStuNameFamily(studentNameFamily: StudentNameFamily)

    /*insert list student info in table*/
    @Insert
    fun insertStuNameFamily(studentNameFamily: List<StudentNameFamily>)

    /*delete single student info in table*/
    @Delete
    fun deleteStuNameFamily(studentNameFamily: StudentNameFamily)

    /*delete list student info in table*/
    @Delete
    fun deleteStuNameFamily(studentNameFamily: List<StudentNameFamily>)

    /*query get all student info with live data*/
    @Query("SELECT * FROM stu_name_last_name")
    fun getAllStuNameFamily(): LiveData<List<StudentNameFamily>>


//    /*query delete item*/
//    @Query("DELETE FROM student_info")
//    fun deleteStuInfo()

    @Query("DELETE FROM stu_name_last_name WHERE stu_id = :stuId")
    fun deleteStuNameFamily(stuId: Int)
}
