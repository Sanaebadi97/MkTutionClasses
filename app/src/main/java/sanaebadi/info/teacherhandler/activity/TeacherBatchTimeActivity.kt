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
import sanaebadi.info.teacherhandler.database.BatchTime
import sanaebadi.info.teacherhandler.databinding.ActivityTeacherBatchTimeBinding
import sanaebadi.info.teacherhandler.viewModel.BatchTimeViewModel

class TeacherBatchTimeActivity : BaseActivity() {

    lateinit var binding: ActivityTeacherBatchTimeBinding

    private lateinit var batchTimeViewModel: BatchTimeViewModel

    private lateinit var timeTitle: String
    private lateinit var timeOne: String
    private lateinit var timeTwo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_batch_time)


        batchTimeViewModel = ViewModelProviders.of(this).get(BatchTimeViewModel::class.java)

        /*TextWatcher EditText*/
        binding.edtTimeTitle.addTextChangedListener(fillAllTextWatcher)
        binding.edtTimeOne.addTextChangedListener(fillAllTextWatcher)
        binding.edtTimeTwo.addTextChangedListener(fillAllTextWatcher)


        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers

    }


    /*Method If All EditText Are fill*/
    private val fillAllTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            timeTitle = binding.edtTimeTitle.text.toString()
            timeOne = binding.edtTimeOne.text.toString()
            timeTwo = binding.edtTimeTwo.text.toString()


            binding.btnShowStudentBatchTime.isEnabled = (!timeTitle.isEmpty() && !timeOne.isEmpty()
                    && !timeTwo.isEmpty())

            if (binding.btnShowStudentBatchTime.isEnabled) {
                binding.btnShowStudentBatchTime.setBackgroundResource(R.drawable.launch_btn_shape)
            }
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    //Handel  View Events Launch Activity
    inner class MyHandlers {

        /*Added Time To database*/
        fun onAddBatchTime(view: View) {

            /*define edit text and get input string from there*/
            timeTitle = binding.edtTimeTitle.text.toString()
            timeOne = binding.edtTimeOne.text.toString()
            timeTwo = binding.edtTimeTwo.text.toString()

            /*on click on btn add and insert to list database*/

            val batchTime = BatchTime(timeTitle, timeOne, timeTwo)

            /*If Do not fill all EditTexts Yo can not Add*/

            if (timeOne.trim().isNotEmpty() && timeTwo.trim().isNotEmpty() &&
                timeTitle.trim().isNotEmpty()
            ) {

                batchTimeViewModel.insertTime(batchTime)
                Snackbar.make(binding.coordinator, getString(R.string.time_added_sanck), Snackbar.LENGTH_SHORT).show()


            } else {
                Snackbar.make(
                    binding.coordinator,
                    getString(R.string.add_warning_empty_edit_text),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }

        /*on click on btn show list to go to list activity*/
        fun onShowBatchTime(view: View) {


            timeTitle = binding.edtTimeTitle.text.toString()
            timeOne = binding.edtTimeOne.text.toString()
            timeTwo = binding.edtTimeTwo.text.toString()

            val intent = Intent(applicationContext, StudentBatchTimeActivity::class.java)
            intent.putExtra("TIME_TITLE", timeTitle)
            intent.putExtra("FIRST_TIME", timeOne)
            intent.putExtra("SECOND_TIME", timeTwo)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        /*Delete EditText When Go to NextPage*/
        binding.edtTimeTitle.setText("")
        binding.edtTimeOne.setText("")
        binding.edtTimeTwo.setText("")
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }


}
