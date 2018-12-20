package sanaebadi.info.teacherhandler.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import sanaebadi.info.teacherhandler.database.StudentNameFamily
import sanaebadi.info.teacherhandler.database.StudentNameFamilyRepository

class StudentNameFamilyViewModel(application: Application) : AndroidViewModel(application) {
    private val studentNameFamilyRepository: StudentNameFamilyRepository = StudentNameFamilyRepository(application)
    val allStuNameFamily: LiveData<List<StudentNameFamily>> = studentNameFamilyRepository.getAllStuNameFamily()


    /*insert Time */
    fun insertStuNameFamily(studentNameFamily: StudentNameFamily) {
        studentNameFamilyRepository.insertStudentNameFamily(studentNameFamily)
    }


//    /*delete time*/
//    fun deleteStuNameFamily() {
//        studentNameFamilyRepository.del()
//    }
}