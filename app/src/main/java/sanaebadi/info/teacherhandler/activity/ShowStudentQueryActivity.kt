package sanaebadi.info.teacherhandler.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.adapter.QueryAdapter
import sanaebadi.info.teacherhandler.database.query.Querys
import sanaebadi.info.teacherhandler.databinding.ActivityShowStudentQueryBinding
import sanaebadi.info.teacherhandler.viewModel.QueryViewModel

class ShowStudentQueryActivity : BaseActivity() {
    private lateinit var binding: ActivityShowStudentQueryBinding

    private lateinit var queryViewModel: QueryViewModel

    private lateinit var queryAdapter: QueryAdapter
    private var queryList = ArrayList<Querys>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_student_query)


        //RecyclerView config
        binding.rvStudentsQuery.setHasFixedSize(true)
        binding.rvStudentsQuery.setItemViewCacheSize(20)
        binding.rvStudentsQuery.clearOnScrollListeners()
        binding.rvStudentsQuery.layoutManager = LinearLayoutManager(
            applicationContext,
            RecyclerView.VERTICAL, false
        )


        /*Set Adapter*/
        queryAdapter = QueryAdapter(applicationContext, queryList)
        binding.rvStudentsQuery.adapter = queryAdapter

        queryViewModel = ViewModelProviders.of(this).get(QueryViewModel::class.java)
        queryViewModel.allQuery.observe(this,
            Observer {
                queryList.addAll(it)
                queryAdapter.notifyDataSetChanged()
                Log.i("ItListSizeBatchFile", "ListSize : " + it.size)
            })


        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers
    }

    /*Click Listener On Views*/
    inner class MyHandlers {
        fun onBackClick(view: View) {
            finish()
        }


    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
