package sanaebadi.info.teacherhandler.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityLoginTeacherBinding
import sanaebadi.info.teacherhandler.viewModel.TeacherNameFamilyViewModel

class TeacherLoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginTeacherBinding


    private lateinit var teacherId: String
    private lateinit var teacherPass: String
    private lateinit var teacherFirstName: String
    private lateinit var teacherLastName: String

    companion object {
        const val TAG: String = "TeacherLoginActivity"
    }

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var teacherNameFamilyViewModel: TeacherNameFamilyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_teacher)

        /*Fetch Room Database Data*/
        teacherNameFamilyViewModel = ViewModelProviders.of(this).get(TeacherNameFamilyViewModel::class.java)


        /*addTextChangedListener*/
        binding.edtTeacherId.addTextChangedListener(fillAllTextWatcher)
        binding.edtTeacherPassword.addTextChangedListener(fillAllTextWatcher)


        /*Get Intent From Register Activity*/
        if (intent.extras != null) {
            teacherFirstName = intent.getStringExtra("TEACHER_FIRST_NAME")
            teacherLastName = intent.getStringExtra("TEACHER_LAST_NAME")

            binding.txtNameLastNameTeacher.text = "$teacherFirstName $teacherLastName"
        } else
            Log.i("console", "Data is null")


        /*Firebase Auth*/
        firebaseAuth = FirebaseAuth.getInstance()


        teacherNameFamilyViewModel.allTeacherNameFamily.observe(this,
            Observer {
                //                batchTimeList.addAll(it)
//                batchTimeAdapter.notifyDataSetChanged()
//                Log.i("ItListSizeBatchFile", "ListSize : " + it.size)
            })

        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers

    }


    /*Method If All EditText Are fill*/
    private val fillAllTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            teacherId = binding.edtTeacherId.text.toString()
            teacherPass = binding.edtTeacherPassword.text.toString()

            binding.btnTeacherLogin.isEnabled = (!teacherId.isEmpty() && !teacherPass.isEmpty())

            if (binding.btnTeacherLogin.isEnabled) {
                binding.btnTeacherLogin.setBackgroundResource(R.drawable.launch_btn_shape)
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

            val dialog: ProgressDialog = ProgressDialog.show(
                this@TeacherLoginActivity, getString(R.string.please_wait),
                getString(R.string.processing), true
            )

            if (!TextUtils.isEmpty(teacherId) && !TextUtils.isEmpty(teacherPass)) {

                firebaseAuth.signInWithEmailAndPassword(teacherId, teacherPass).addOnCompleteListener {
                    dialog.dismiss()
                    if (it.isSuccessful) {
                        val snackbar: Snackbar = Snackbar.make(
                            binding.coordinator,
                            getString(R.string.register_succesed), Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()

                        /*Intent to Login Activity*/
                        val intent = Intent(applicationContext, TeacherHandelActivity::class.java)
                        startActivity(intent)

                    } else {
                        Log.e(StudentLoginActivity.TAG, it.exception.toString())
                        val snackbar: Snackbar = Snackbar.make(
                            binding.coordinator,
                            it.exception!!.message!!, Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                    }
                }
            }


        }

        /*Go To Register Activity*/
        fun onLinkRegisterClick(view: View) {
            val intent = Intent(applicationContext, TeacherRegisterActivity::class.java)
            startActivity(intent)
        }
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
