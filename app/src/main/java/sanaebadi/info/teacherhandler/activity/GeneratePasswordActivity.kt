package sanaebadi.info.teacherhandler.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityGeneratePasswordBinding

class GeneratePasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityGeneratePasswordBinding
    private lateinit var passwordInput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generate_password)

        /*TextWatcher EditText*/
        binding.edtGeneratePassword.addTextChangedListener(fillAllTextWatcher)

        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers

    }

    /*Method If All EditText Are fill*/
    private val fillAllTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {


        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            passwordInput = binding.edtGeneratePassword.text.toString()

            binding.btnGeneratePassword.isEnabled = (!passwordInput.isEmpty())

            if (binding.btnGeneratePassword.isEnabled) {
                binding.btnGeneratePassword.setBackgroundResource(R.drawable.launch_btn_shape)
            }


        }

        override fun afterTextChanged(s: Editable) {


        }

    }

    /*Click Listener On Views*/
    inner class MyHandlers {
        fun onBackClick(view: View) {
            finish()
        }

        /*Insert To Database*/
        fun onSendPassword(view: View) {

            /*define edit text and get input string from there*/

            // queryMessage = binding.edtQuery.text.toString()

            /*on click on btn add and insert to list database*/


            /*If Do not fill all EditTexts Yo can not Add*/

//                if (queryMessage.trim().isNotEmpty() && studentName.trim().isNotEmpty()
//                ) {

//                val queryMessage = binding.edtQuery.text.toString()
//                val queryStuentName = binding.edtName.text.toString()

//                    val snackbar = Snackbar
//                        .make(binding.coordinator, getString(R.string.submit_query), Snackbar.LENGTH_LONG).show()
//
//                    val intent = Intent(applicationContext, ShowStudentQueryActivity::class.java)
//                    intent.putExtra("QUERY_MESSAGE", queryMessage)
//                    intent.putExtra("QUERY_STU_NAME", queryStuentName)
//                    startActivity(intent)
//
//
//                } else {
//                    Snackbar.make(
//                        binding.coordinator,
//                        getString(R.string.add_warning_empty_edit_text),
//                        Snackbar.LENGTH_SHORT
//                    ).show()
//                }
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }

}