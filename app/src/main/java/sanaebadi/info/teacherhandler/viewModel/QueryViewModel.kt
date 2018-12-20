package sanaebadi.info.teacherhandler.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import sanaebadi.info.teacherhandler.database.query.Querys
import sanaebadi.info.teacherhandler.database.query.QuerysRepository

class QueryViewModel(application: Application) : AndroidViewModel(application) {
    private val queryRepository: QuerysRepository =
        QuerysRepository(application)
    val allQuery: LiveData<List<Querys>> = queryRepository.getAllQuery()

    /*insert Query info*/
    fun insertQuery(querys: Querys) {
        queryRepository.insertQuery(querys)
    }


//    /*delete query info*/
//    fun deleteQuery(id: Int) {
//        queryRepository.de(id)
//    }

}