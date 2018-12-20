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
import sanaebadi.info.teacherhandler.database.query.Querys
import sanaebadi.info.teacherhandler.databinding.ActivityQueryBinding
import sanaebadi.info.teacherhandler.viewModel.QueryViewModel

class QueryActivity : BaseActivity() {
    private lateinit var binding: ActivityQueryBinding
    private lateinit var queryInput: String
    private lateinit var nameInput: String

    private var isPressed = false
    private var isReached = false

    private lateinit var queryMessage: String
    private lateinit var studentName: String
    private lateinit var queryViewModel: QueryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_query)


        queryViewModel = ViewModelProviders.of(this).get(QueryViewModel::class.java)

        /*TextWatcher EditText*/
        binding.edtQuery.addTextChangedListener(fillAllTextWatcher)
        binding.edtName.addTextChangedListener(fillAllTextWatcher)


        /*Enable Error*/
        binding.btnSubmitQuery.setOnClickListener {
            isPressed = true

            val inputQuery = binding.textInputQuery.editText!!.text.toString()

            if (inputQuery.length < 10) {
                binding.textInputQuery.error = getString(R.string.error_editText)
                binding.textInputQuery.isErrorEnabled = true
            } else {
                binding.textInputQuery.isErrorEnabled = false
            }


        }


        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers


    }


    /*Method If All EditText Are fill*/
    private val fillAllTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            if (isPressed) {
                if (s.length < 10) {
                    binding.textInputQuery.error = getString(R.string.error_editText)
                    binding.textInputQuery.isErrorEnabled = true
                } else {
                    binding.textInputQuery.isErrorEnabled = false
                }
            }

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            queryInput = binding.edtQuery.text.toString()
            nameInput = binding.edtName.text.toString()

            binding.btnSubmitQuery.isEnabled = (!queryInput.isEmpty() && !nameInput.isEmpty())

            if (binding.btnSubmitQuery.isEnabled) {
                binding.btnSubmitQuery.setBackgroundResource(R.drawable.launch_btn_shape)
            }
        }

        override fun afterTextChanged(s: Editable) {
            // if edittext has 10chars & this is not called yet, add new line
            if (binding.edtQuery.text.length == 20 && !isReached) {
                binding.edtQuery.append("\n")
                isReached = true
            }
            // if edittext has less than 10chars & boolean has changed, reset
            if (binding.edtQuery.text.length < 10 && isReached) isReached = false
        }
    }


    /*Click Listener On Views*/
    inner class MyHandlers {
        fun onBackClick(view: View) {
            finish()
        }

        /*Insert To Database*/
        fun onQuerySubmit(view: View) {

            /*define edit text and get input string from there*/

            queryMessage = binding.edtQuery.text.toString()
            studentName = binding.edtName.text.toString()

            /*on click on btn add and insert to list database*/

            val querys = Querys(queryMessage, studentName)

            /*If Do not fill all EditTexts Yo can not Add*/

            if (queryMessage.trim().isNotEmpty() && studentName.trim().isNotEmpty()
            ) {
                queryViewModel.insertQuery(querys)

                val queryMessage = binding.edtQuery.text.toString()
                val queryStuentName = binding.edtName.text.toString()

                val snackbar = Snackbar
                    .make(binding.coordinator, getString(R.string.submit_query), Snackbar.LENGTH_LONG).show()

                val intent = Intent(applicationContext, ShowStudentQueryActivity::class.java)
                intent.putExtra("QUERY_MESSAGE", queryMessage)
                intent.putExtra("QUERY_STU_NAME", queryStuentName)
                startActivity(intent)


            } else {
                Snackbar.make(
                    binding.coordinator,
                    getString(R.string.add_warning_empty_edit_text),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
