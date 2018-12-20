package sanaebadi.info.teacherhandler.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import sanaebadi.info.teacherhandler.database.teacherNameFamily.TeacherNameFamily
import sanaebadi.info.teacherhandler.database.teacherNameFamily.TeacherNameFamilyRepository

class TeacherNameFamilyViewModel(application: Application) : AndroidViewModel(application) {
    private val teacherNameFamilyRepository: TeacherNameFamilyRepository =
        TeacherNameFamilyRepository(application)
    val allTeacherNameFamily: LiveData<List<TeacherNameFamily>> = teacherNameFamilyRepository.getAllTeacherNameFamily()


    /*insert Time */
    fun insertTeacherNameFamily(teacherNameFamily: TeacherNameFamily) {
        teacherNameFamilyRepository.insertTeacherNameFamily(teacherNameFamily)
    }


//    /*delete time*/
//    fun deleteStuNameFamily() {
//        studentNameFamilyRepository.del()
//    }

}