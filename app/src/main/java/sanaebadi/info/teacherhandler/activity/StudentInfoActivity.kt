package sanaebadi.info.teacherhandler.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.database.student.Student
import sanaebadi.info.teacherhandler.databinding.ActivityStudentInfoBinding
import sanaebadi.info.teacherhandler.viewModel.StudentViewModel


class StudentInfoActivity : BaseActivity() {

    private lateinit var studentViewModel: StudentViewModel
    private lateinit var binding: ActivityStudentInfoBinding
    private lateinit var stuName: String
    private lateinit var stuStartDate: String
    private lateinit var stuFeeDue: String
    private lateinit var stuFeeDeposit: String
    private lateinit var stuTestReport: String
    private lateinit var stuMobileNom: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_info)


        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)

        /*TextWatcher EditText*/
        binding.edtStudentName.addTextChangedListener(fillAllTextWatcher)
        binding.edtStartingDate.addTextChangedListener(fillAllTextWatcher)
        binding.edtFeeDue.addTextChangedListener(fillAllTextWatcher)
        binding.edtFeeDeposit.addTextChangedListener(fillAllTextWatcher)
        binding.edtTestReport.addTextChangedListener(fillAllTextWatcher)
        binding.edtMobileNumber.addTextChangedListener(fillAllTextWatcher)


        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers
    }

    /*Method If All EditText Are fill*/
    private val fillAllTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            stuName = binding.edtStudentName.text.toString()
            stuStartDate = binding.edtStartingDate.text.toString()
            stuFeeDue = binding.edtFeeDue.text.toString()
            stuFeeDeposit = binding.edtFeeDeposit.text.toString()
            stuTestReport = binding.edtTestReport.text.toString()
            stuMobileNom = binding.edtMobileNumber.text.toString()

            binding.btnShowStudentList.isEnabled = (!stuName.isEmpty() && !stuStartDate.isEmpty()
                    && !stuFeeDue.isEmpty() && !stuFeeDeposit.isEmpty() && !stuTestReport.isEmpty() && !stuMobileNom.isEmpty())

            if (binding.btnShowStudentList.isEnabled) {
                binding.btnShowStudentList.setBackgroundResource(R.drawable.launch_btn_shape)
            }
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    //Handel  View Events Launch Activity
    inner class MyHandlers {
        fun onStudentInfoAddedClick(view: View) {
            /*define edit text and get input string from there*/
            stuName = binding.edtStudentName.text.toString()
            stuStartDate = binding.edtStartingDate.text.toString()
            stuFeeDue = binding.edtFeeDue.text.toString()
            stuFeeDeposit = binding.edtFeeDeposit.text.toString()
            stuTestReport = binding.edtTestReport.text.toString()
            stuMobileNom = binding.edtMobileNumber.text.toString()

            /*on click on btn add and insert to list database*/
            val student = Student(
                stuName, stuStartDate, stuFeeDue, stuFeeDeposit, stuTestReport,
                stuMobileNom
            )

            /*If Do not fill all EditTexts Yo can not Add*/

            if (stuName.trim().isNotEmpty() && stuStartDate.trim().isNotEmpty() &&
                stuFeeDue.trim().isNotEmpty() &&
                stuFeeDeposit.trim().isNotEmpty() && stuTestReport.trim().isNotEmpty()
                && stuMobileNom.trim().isNotEmpty()
            ) {

                studentViewModel.insertStudent(student)
                Snackbar.make(binding.coordinator, getString(R.string.info_added_sanck), Snackbar.LENGTH_SHORT).show()


            } else {
                Snackbar.make(
                    binding.coordinator,
                    getString(R.string.add_warning_empty_edit_text),
                    Snackbar.LENGTH_SHORT
                ).show()
            }


        }

        /*on click on btn show list to go to list activity*/
        fun onShowStudentList(view: View) {

            val intent = Intent(applicationContext, StudentInfoListActivity::class.java)
            intent.putExtra("STU_NAME", stuName)
            intent.putExtra("STU_START_DATE", stuStartDate)
            intent.putExtra("STU_FEE_DUE", stuFeeDue)
            intent.putExtra("STU_FEE_DEPOSIT", stuFeeDeposit)
            intent.putExtra("STU_TEST_REPORT", stuTestReport)
            intent.putExtra("STU_MOBILE_NOM", stuMobileNom)
            startActivity(intent)


        }
    }

    override fun onResume() {
        super.onResume()

        /*Delete EditText When Go to NextPage*/
        binding.edtStudentName.setText("")
        binding.edtStartingDate.setText("")
        binding.edtFeeDue.setText("")
        binding.edtFeeDeposit.setText("")
        binding.edtTestReport.setText("")
        binding.edtMobileNumber.setText("")
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
