package sanaebadi.info.teacherhandler.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import sanaebadi.info.teacherhandler.database.studentNameFamily.StudentNameFamily
import sanaebadi.info.teacherhandler.database.studentNameFamily.StudentNameFamilyRepository

class StudentNameFamilyViewModel(application: Application) : AndroidViewModel(application) {
    private val studentNameFamilyRepository: StudentNameFamilyRepository =
        StudentNameFamilyRepository(application)

    val allStuNameFamily: LiveData<List<StudentNameFamily>> = studentNameFamilyRepository.getAllStuNameFamily()
    val stuNameFamily: LiveData<StudentNameFamily> = studentNameFamilyRepository.getStuNameFamily()


    /*insert Time */
    fun insertStuNameFamily(studentNameFamily: StudentNameFamily) {
        studentNameFamilyRepository.insertStudentNameFamily(studentNameFamily)
    }


//    /*delete time*/
//    fun deleteStuNameFamily() {
//        studentNameFamilyRepository.del()
//    }
}