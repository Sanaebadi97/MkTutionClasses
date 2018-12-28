package sanaebadi.info.teacherhandler.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityLoginStudentBinding
import sanaebadi.info.teacherhandler.viewModel.StudentNameFamilyViewModel


class StudentLoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginStudentBinding

    private lateinit var stuEmail: String
    private lateinit var stuPass: String

    private lateinit var stuFirstName: String
    private lateinit var stuLastName: String


    private lateinit var studentNameFamilyViewModel: StudentNameFamilyViewModel

    companion object {
        const val TAG: String = "StudentLoginActivity"
    }

    private lateinit var firebaseAuth: FirebaseAuth


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_student)

        /*Get Intent From Register Activity*/
        if (intent.extras != null) {
            stuFirstName = intent.getStringExtra("STU_FIRST_NAME")
            stuLastName = intent.getStringExtra("STU_LAST_NAME")

            // binding.txtNameLastName.text = "$stuFirstName $stuLastName"
        } else
            Log.i("console", "Data is null")


        /*TextWatcher EditText*/

        binding.edtStudentPassword.addTextChangedListener(fillAllTextWatcher)


        /*Fetch Room Database Data*/
        studentNameFamilyViewModel = ViewModelProviders.of(this).get(StudentNameFamilyViewModel::class.java)
        studentNameFamilyViewModel.stuNameFamily.observe(this,
            Observer {
                try {
                    if (!it.stu_first_name!!.isEmpty() && !it.stu_last_name!!.isEmpty()) {
                        // binding.txtNameLastName.text = it.stu_first_name + " " + it.stu_last_name
                    } else {
                        Log.e(TAG, "Data Is Null Student Name and LastName")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, e.message)
                }
            })

        /*Firebase Auth*/
        firebaseAuth = FirebaseAuth.getInstance()


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

            stuPass = binding.edtStudentPassword.text.toString()

            binding.btnStudentLogin.isEnabled = (!stuPass.isEmpty())

            if (binding.btnStudentLogin.isEnabled) {
                binding.btnStudentLogin.setBackgroundResource(R.drawable.launch_btn_shape)
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

        /*Login With Firebase Auth*/
        fun onLoginBtnClick(view: View) {

//            val dialog: ProgressDialog = ProgressDialog.show(
//                this@StudentLoginActivity, getString(R.string.please_wait),
//                getString(R.string.processing), true
//            )
//            if (!TextUtils.isEmpty(stuEmail) && !TextUtils.isEmpty(stuPass)) {
//
//
//                /*Give Student Email Adders From Shared Pref*/
//                val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//                val data = prefs.getString("STUDENT_PASSWORD", "password") //no id: default value
//                Log.i("GENERATEEMAIL", "PASSWORD : " + data)
//
//
//                firebaseAuth.signInWithEmailAndPassword(stuEmail, data!!).addOnCompleteListener {
//                    dialog.dismiss()
//                    if (it.isSuccessful) {
//                        val snackbar: Snackbar = Snackbar.make(
//                            binding.coordinator,
//                            getString(R.string.register_succesed), Snackbar.LENGTH_SHORT
//                        )
//                        snackbar.show()
//
//                        /*Intent to Login Activity*/
//                        val intent = Intent(applicationContext, BatchesStudentActivity::class.java)
//                        startActivity(intent)
//                        finish()
//
//                    } else {
//                        Log.e(TAG, it.exception.toString())
//                        val snackbar: Snackbar = Snackbar.make(
//                            binding.coordinator,
//                            it.exception!!.message!!, Snackbar.LENGTH_SHORT
//                        )
//                        snackbar.show()
//                    }
//                }
//
//            }
//        }

//        /*Go To Register Activity*/
//        fun onLinkRegisterClick(view: View) {
//            val intent = Intent(applicationContext, StudentRegisterActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }

    override fun onResume() {
        super.onResume()

        /*Delete EditText When Go to NextPage*/
        binding.edtStudentPassword.setText("")

    }
}
