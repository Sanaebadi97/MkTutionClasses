package sanaebadi.info.teacherhandler.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityQueryBinding

class QueryActivity : BaseActivity() {
    private lateinit var binding: ActivityQueryBinding
    private lateinit var queryInput: String
    private lateinit var nameInput: String

    private var isPressed = false
    private var isReached = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_query)


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
            if (binding.edtQuery.text.length == 15 && !isReached) {
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

        fun onQuerySubmit(view: View) {
            val snackbar = Snackbar
                .make(binding.coordinator, getString(R.string.submit_query), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
