package sanaebadi.info.teacherhandler.database.teacherNameFamily

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TeacherNameFamilyDao {
    /*insert single Teacher info in table*/
    @Insert
    fun insertTeacherNameFamily(teacherNameFamily: TeacherNameFamily)

    /*insert list teacher info in table*/
    @Insert
    fun insertTeacherNameFamily(teacherNameFamily: List<TeacherNameFamily>)

    /*delete single student info in table*/
    @Delete
    fun deleteTeacherNameFamily(teacherNameFamily: TeacherNameFamily)

    /*delete list student info in table*/
    @Delete
    fun deleteTeacherNameFamily(teacherNameFamily: List<TeacherNameFamily>)

    /*query get all student info with live data*/
    @Query("SELECT * FROM teacher_name_last_name")
    fun getAllTeacherNameFamily(): LiveData<List<TeacherNameFamily>>


//    /*query delete item*/
//    @Querys("DELETE FROM student_info")
//    fun deleteStuInfo()

//    @Querys("DELETE FROM teacher_name_last_name WHERE teacher_id = :stuId")
//    fun deleteStuNameFamily(stuId: Int)

}