package sanaebadi.info.teacherhandler.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityGeneratePasswordBinding


class GeneratePasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityGeneratePasswordBinding
    private lateinit var passwordInput: String


    private var receiver: ConnectivityListenerReceiver? = null

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

            /*Give Student Email Adders From Shared Pref*/

            val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val data = prefs.getString("STUDENT_EMAIL", "email") //no id: default value
            Log.i("GENERATEPASSWORD", "EMAIL : " + data)


            /*Send Password Than Teacher Generated To Student Email*/
            val subject = "Your Password"
            val message = binding.edtGeneratePassword.text.toString()

            val intent = Intent(Intent.ACTION_SEND)
            val addressees = arrayOf(data)
            intent.putExtra(Intent.EXTRA_EMAIL, addressees)
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Send Email using:"))

            Snackbar.make(
                binding.coordinator,
                getString(R.string.email_sended),
                Snackbar.LENGTH_SHORT
            ).show()

        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }


    /*Handel Network Connection*/
    inner class ConnectivityListenerReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo: NetworkInfo?
            networkInfo = connectivityManager.activeNetworkInfo
            val isConnected = networkInfo != null && networkInfo.isConnected
            if (isConnected) {
                binding.txtToolbar.text = context.getString(R.string.generate_password_title)
            } else {
                binding.txtToolbar.text = context.getString(R.string.wait_network)


            }


        }

    }

    override fun onStart() {
        super.onStart()
        receiver = ConnectivityListenerReceiver()
        registerReceiver(receiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))

    }

    override fun onStop() {
        unregisterReceiver(receiver)
        super.onStop()
    }


    override fun onResume() {
        super.onResume()
        binding.edtGeneratePassword.setText("")
    }
}