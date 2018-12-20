package sanaebadi.info.teacherhandler.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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

    private lateinit var queryMessage: String
    private lateinit var queryStuName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_student_query)


        /*get Intent From TeacherBatchTimeActivity*/
        if (intent.extras != null) {
            queryMessage = intent.getStringExtra("QUERY_MESSAGE")
            queryStuName = intent.getStringExtra("QUERY_STU_NAME")
        } else
            Log.i("console", "Data is null")


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

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
//            121 -> {
//                /*Delete Item From List*/
//                queryViewModel.deleteTime()
//                batchTimeAdapter.removeItem(item.groupId)
//                showSnake("item Deleted !")
//            }

            /*Share Item With Social Media*/
//            122
//            -> {
//
//                val body: String = application.getString(R.string.time_title) + " : " + queryMessage + "\n" +
//                        application.getString(R.string.first_time) + " : " + queryStuName + "\n"
//
//
//                val shareIntent = Intent()
//                shareIntent.action = Intent.ACTION_SEND
//                shareIntent.type = "text/plain"
//                shareIntent.putExtra(Intent.EXTRA_TEXT, body)
//                startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
//
//            }

        }
        return super.onContextItemSelected(item)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
