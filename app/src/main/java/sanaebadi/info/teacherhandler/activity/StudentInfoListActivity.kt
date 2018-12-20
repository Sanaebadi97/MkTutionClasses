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
import kotlinx.android.synthetic.main.activity_student_info_list.*
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.adapter.StudentInfoAdapter
import sanaebadi.info.teacherhandler.database.student.Student
import sanaebadi.info.teacherhandler.databinding.ActivityStudentInfoListBinding
import sanaebadi.info.teacherhandler.viewModel.StudentViewModel

class StudentInfoListActivity : BaseActivity() {
    private lateinit var rvStuInfo: RecyclerView
    private lateinit var stuAddAdapter: StudentInfoAdapter
    private lateinit var studentViewModel: StudentViewModel
    private var studentList = ArrayList<Student>()


    private lateinit var binding: ActivityStudentInfoListBinding

    private lateinit var stuName: String
    private lateinit var stuStartDate: String
    private lateinit var stuFeeDue: String
    private lateinit var stuFeeDeposit: String
    private lateinit var stuTestReport: String
    private lateinit var stuMobileNom: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_info_list)

        initRecyclerView()
        stuAddAdapter = StudentInfoAdapter(this, studentList)

        rvStuInfo.adapter = stuAddAdapter

        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        studentViewModel.allStudentInfo.observe(this, Observer {
            studentList.addAll(it)
            stuAddAdapter.notifyDataSetChanged()
            Log.i("ItListSize", "ListSize : " + it.size)
        })


        /*get Intent From StudentInfoActivity*/
        stuName = intent.getStringExtra("STU_NAME")
        stuStartDate = intent.getStringExtra("STU_START_DATE")
        stuFeeDue = intent.getStringExtra("STU_FEE_DUE")
        stuFeeDeposit = intent.getStringExtra("STU_FEE_DEPOSIT")
        stuTestReport = intent.getStringExtra("STU_TEST_REPORT")
        stuMobileNom = intent.getStringExtra("STU_MOBILE_NOM")

        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers
    }

    private fun initRecyclerView() {
        rvStuInfo = rv_add_user_info
        rvStuInfo.setHasFixedSize(true)
        rvStuInfo.layoutManager = LinearLayoutManager(
            this, RecyclerView.VERTICAL,
            false
        )
    }

    /*Context Menu Item Selected*/
    override fun onContextItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            121 -> {
                /*Delete Item From List*/
                stuAddAdapter.removeItem(item.groupId)
                studentViewModel.deleteStudent(item.groupId)
                showSnake(getString(R.string.item_deleted))
            }

            /*Share Item With Social Media*/
            122
            -> {

                val body: String = application.getString(R.string.student_name) + " : " + stuName + "\n" +
                        application.getString(R.string.starting_date) + " : " + stuStartDate + "\n" +
                        application.getString(R.string.fee_due) + " : " + stuFeeDue + "\n" +
                        application.getString(R.string.fee_deposit) + " : " + stuFeeDeposit + "\n" +
                        application.getString(R.string.test_report) + " : " + stuTestReport + "\n" +
                        application.getString(R.string.mobile_nom) + " : " + stuMobileNom

                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, body)
                startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))

            }
        }
        return super.onContextItemSelected(item)
    }


    /*Show SnakeBar For Action*/
    private fun showSnake(message: String) {
        Snackbar.make(binding.coordinator, message, Snackbar.LENGTH_SHORT).show()
    }

    //Handel  View Events Launch Activity
    inner class MyHandlers {
        fun onBackClick(view: View) {
            finish()
        }
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }


    override fun onStart() {
        super.onStart()
        stuAddAdapter.notifyDataSetChanged()
    }
}
