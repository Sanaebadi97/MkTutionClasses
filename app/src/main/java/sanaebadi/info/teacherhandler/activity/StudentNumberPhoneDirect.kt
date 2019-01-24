package sanaebadi.info.teacherhandler.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityStudentNumberPhoneDirectBinding


class StudentNumberPhoneDirect : BaseActivity() {
    private lateinit var binding: ActivityStudentNumberPhoneDirectBinding
    private lateinit var stuPhoneNum: String

    companion object {
        const val SEND_SMS_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_number_phone_direct)


        /*TextWatcher EditText*/

        binding.edtStuPhoneNum.addTextChangedListener(fillAllTextWatcher)


        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers


    }


    /*Method If All EditText Are fill*/
    private
    val fillAllTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            stuPhoneNum = binding.edtStuPhoneNum.text.toString()

            binding.btnStudentSendPhoneNum.isEnabled = (!stuPhoneNum.isEmpty())

            if (binding.btnStudentSendPhoneNum.isEnabled) {
                binding.btnStudentSendPhoneNum.setBackgroundResource(R.drawable.launch_btn_shape)
            }
        }

        override fun afterTextChanged(s: Editable) {

        }
    }


    //Handel  View Events Launch Activity
    inner class MyHandlers {
        fun onBackClick(view: View) {
            finish()
        }

        /*Enter Phone Number To Teacher*/
        fun onSendPhoneNom(view: View) {

            val inputPhoneNom = binding.edtStuPhoneNum.text.toString()

            val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val editor = prefs.edit()
            editor.putString("PHONE_NOM", inputPhoneNom) //InputString: from the EditText
            editor.apply()

            Log.i("CONSOLE", "PHONE NUMBER $inputPhoneNom")

            Snackbar.make(
                binding.coordinator,
                getString(R.string.message_sent),
                Snackbar.LENGTH_SHORT
            ).show()



        }


        fun onLinkToLogin(view:View){
            val intent = Intent(applicationContext, StudentLoginActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }

}
