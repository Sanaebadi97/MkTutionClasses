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
import com.google.android.material.snackbar.Snackbar
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.adapter.BatchTimeStudentAdapter
import sanaebadi.info.teacherhandler.database.batchTime.BatchTime
import sanaebadi.info.teacherhandler.databinding.ActivityBatchTimeStudentBinding
import sanaebadi.info.teacherhandler.viewModel.BatchTimeViewModel

class StudentBatchTimeActivity : BaseActivity() {

    private lateinit var binding: ActivityBatchTimeStudentBinding
    private var batchTimeList = ArrayList<BatchTime>()

    private lateinit var batchTimeViewModel: BatchTimeViewModel
    lateinit var batchTimeAdapter: BatchTimeStudentAdapter

    private lateinit var timeTitle: String
    private lateinit var firstTime: String
    private lateinit var secondTime: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_batch_time_student)

        //RecyclerView config
        binding.rvBatchTime.setHasFixedSize(true)
        binding.rvBatchTime.setItemViewCacheSize(20)
        binding.rvBatchTime.clearOnScrollListeners()
        binding.rvBatchTime.layoutManager = LinearLayoutManager(
            applicationContext,
            RecyclerView.VERTICAL, false
        )


        batchTimeAdapter = BatchTimeStudentAdapter(applicationContext, batchTimeList)
        binding.rvBatchTime.adapter = batchTimeAdapter

        batchTimeViewModel = ViewModelProviders.of(this).get(BatchTimeViewModel::class.java)
        batchTimeViewModel.allTime.observe(this,
            Observer {
                batchTimeList.addAll(it)
                batchTimeAdapter.notifyDataSetChanged()
                Log.i("ItListSizeBatchFile", "ListSize : " + it.size)
            })


        /*get Intent From TeacherBatchTimeActivity*/
        if (intent.extras != null) {
            timeTitle = intent.getStringExtra("TIME_TITLE")
            firstTime = intent.getStringExtra("FIRST_TIME")
            secondTime = intent.getStringExtra("SECOND_TIME")
        } else
            Log.i("console", "Data is null")


        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers


    }

    //Handel  View Events Launch Activity
    inner class MyHandlers {
        fun onBackClick(view: View) {
            finish()
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {

            121 -> {
                /*Delete Item From List*/
                batchTimeViewModel.deleteTime(batchTimeList[item.groupId])
                batchTimeAdapter.removeItem(item.groupId)

                showSnake(getString(R.string.delete_item))
            }

            /*Share Item With Social Media*/
            122
            -> {

                val body: String = application.getString(R.string.time_title) + " : " + timeTitle + "\n" +
                        application.getString(R.string.first_time) + " : " + firstTime + "\n" +
                        application.getString(R.string.second_time) + " : " + secondTime + "\n"

                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, body)
                startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))

            }

        }

        return super.onContextItemSelected(item)
    }

    private fun showSnake(message: String) {
        Snackbar.make(binding.coordinator, message, Snackbar.LENGTH_SHORT).show()
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }


}


