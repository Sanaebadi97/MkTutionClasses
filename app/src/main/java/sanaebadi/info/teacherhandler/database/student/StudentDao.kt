package sanaebadi.info.teacherhandler.database.student

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import sanaebadi.info.teacherhandler.database.student.Student

@Dao
interface StudentDao {
    /*insert single student info in table*/
    @Insert
    fun insertStuInfo(student: Student)

    /*insert list student info in table*/
    @Insert
    fun insertStuInfo(student: List<Student>)

    /*delete single student info in table*/
    @Delete
    fun deleteStuInfo(student: Student)

    /*delete list student info in table*/
    @Delete
    fun deleteStuInfo(student: List<Student>)

    /*query get all student info with live data*/
    @Query("SELECT * FROM student_info")
    fun getAllStuInfo(): LiveData<List<Student>>


//    /*query delete item*/
//    @Querys("DELETE FROM student_info")
//    fun deleteStuInfo()

    @Query("DELETE FROM student_info WHERE stu_id = :stuId")
    fun deleteStuInfo(stuId: Int)
}
