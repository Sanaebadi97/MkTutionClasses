package sanaebadi.info.teacherhandler.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import sanaebadi.info.teacherhandler.database.Student
import sanaebadi.info.teacherhandler.database.StudentRepository

/*in this class We Connect  between ViewModel and Repository and We do not use 3th database class*/
class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private val studentRepository: StudentRepository = StudentRepository(application)
    val allStudentInfo: LiveData<List<Student>> = studentRepository.getAllStuInfo()

    /*insert student info*/
    fun insertStudent(student: Student) {
        studentRepository.insertStudentInfo(student)
    }


    /*delete student info*/
    fun deleteStudent(id:Int) {
        studentRepository.deleteStuInfo(id)
    }

}
