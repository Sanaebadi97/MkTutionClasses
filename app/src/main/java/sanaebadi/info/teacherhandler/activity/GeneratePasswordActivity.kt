package sanaebadi.info.teacherhandler.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.telephony.SmsManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
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
            /*Store Password */
//            val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//            val editor = prefs.edit()
//            editor.putString("STUDENT_PASSWORD", passwordInput) //InputString: from the EditText
//            Log.i("PASSWORDSTUDENT", "Password: $passwordInput")
//            editor.apply()

        }

    }

    /*Click Listener On Views*/
    inner class MyHandlers {
        fun onBackClick(view: View) {
            finish()
        }

        /*Insert To Database*/
        fun onSendPassword(view: View) {
            val inputPassword = binding.edtGeneratePassword.text.toString()

            /*Get Phone Number From Student Enter Phone Number*/

            val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val data = prefs.getString("PHONE_NOM", "no id") //no id: default value


            /*Check Permission*/
            if (checkPermission(Manifest.permission.SEND_SMS)) {

                /*SMS MANAGER*/
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(data, null, inputPassword, null, null)
                Snackbar.make(
                    binding.coordinator,
                    getString(R.string.message_sent),
                    Snackbar.LENGTH_SHORT
                ).show()

            } else {

                ActivityCompat.requestPermissions(
                    this@GeneratePasswordActivity,
                    arrayOf(Manifest.permission.SEND_SMS), StudentNumberPhoneDirect.SEND_SMS_PERMISSION_REQUEST_CODE
                )
            }

        }


    }

    fun checkPermission(permission: String): Boolean {
        val check: Int = ContextCompat.checkSelfPermission(applicationContext, permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }


    override fun onResume() {
        super.onResume()
        // binding.edtGeneratePassword.setText("")
    }
}