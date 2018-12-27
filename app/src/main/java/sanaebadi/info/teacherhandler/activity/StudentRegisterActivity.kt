package sanaebadi.info.teacherhandler.activity

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
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
import sanaebadi.info.teacherhandler.database.studentNameFamily.StudentNameFamily
import sanaebadi.info.teacherhandler.databinding.ActivityStudentRegisterBinding
import sanaebadi.info.teacherhandler.viewModel.StudentNameFamilyViewModel

class StudentRegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityStudentRegisterBinding

    private lateinit var stuEmail: String
    private lateinit var stuPass: String
    private lateinit var stuFirstName: String
    private lateinit var stuLastName: String

    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        const val TAG: String = "StudentRegisterActivity"
    }

    private lateinit var studentNameFamilyViewModel: StudentNameFamilyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_register)


        /*Fetch Room Database Data*/
        studentNameFamilyViewModel = ViewModelProviders.of(this).get(StudentNameFamilyViewModel::class.java)

        /*TextWatcher EditText*/
        binding.edtStudentEmailRegister.addTextChangedListener(fillAllTextWatcher)
        binding.edtStudentPasswordRegister.addTextChangedListener(fillAllTextWatcher)
        binding.edtStuFirstName.addTextChangedListener(fillAllTextWatcher)
        binding.edtStuLastName.addTextChangedListener(fillAllTextWatcher)


        /*Firebase Auth*/
        firebaseAuth = FirebaseAuth.getInstance()


        studentNameFamilyViewModel.allStuNameFamily.observe(this,
            Observer {
                //                batchTimeList.addAll(it)
//                batchTimeAdapter.notifyDataSetChanged()
//                Log.i("ItListSizeBatchFile", "ListSize : " + it.size)
            })


        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers

        stuEmail = binding.edtStudentEmailRegister.text.toString()


    }


    /*Method If All EditText Are fill*/
    private val fillAllTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            stuEmail = binding.edtStudentEmailRegister.text.toString()
            stuPass = binding.edtStudentPasswordRegister.text.toString()
            stuFirstName = binding.edtStuFirstName.text.toString()
            stuLastName = binding.edtStuLastName.text.toString()

            binding.btnStudentRegister.isEnabled = (!stuEmail.isEmpty() && !stuPass.isEmpty()
                    && !stuFirstName.isEmpty() && !stuLastName.isEmpty())

            if (binding.btnStudentRegister.isEnabled) {
                binding.btnStudentRegister.setBackgroundResource(R.drawable.launch_btn_shape)
            }



        }

        override fun afterTextChanged(s: Editable) {

            /*Store Email Address*/
            val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val editor = prefs.edit()
            editor.putString("STUDENT_EMAIL", stuEmail) //InputString: from the EditText
            Log.i(TAG, stuEmail)
            editor.apply()
        }


    }

    //Handel  View Events Launch Activity
    inner class MyHandlers {


        /*Register With Firebase Auth*/
        fun onRegisterBtnClick(view: View) {

            /*Define Edit Text Input*/
            stuFirstName = binding.edtStuFirstName.text.toString()
            stuLastName = binding.edtStuLastName.text.toString()

            val dialog: ProgressDialog = ProgressDialog.show(
                this@StudentRegisterActivity, getString(R.string.please_wait),
                getString(R.string.processing), true
            )

            if (!TextUtils.isEmpty(stuEmail) && !TextUtils.isEmpty(stuPass)) {

                firebaseAuth.createUserWithEmailAndPassword(stuEmail, stuPass).addOnCompleteListener {
                    dialog.dismiss()
                    if (it.isSuccessful) {
                        val snackbar: Snackbar = Snackbar.make(
                            binding.coordinator,
                            getString(R.string.register_succesed), Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()


                        /*Insert TO database*/
                        val studentNameFamily =
                            StudentNameFamily(
                                stuFirstName,
                                stuLastName
                            )

                        if (stuFirstName.trim().isNotEmpty() && stuLastName.trim().isNotEmpty()) {

                            /*Insert Name and Family To Database*/
                            studentNameFamilyViewModel.insertStuNameFamily(studentNameFamily)


                        } else {
                            Snackbar.make(
                                binding.coordinator,
                                getString(R.string.add_warning_empty_edit_text),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }


                        /*Intent to Login Activity*/
                        val intent = Intent(applicationContext, StudentLoginActivity::class.java)

                        /*Get Edit Text Input To Bundle Them To Next Page*/
                        val studentFirstName: String = binding.edtStuFirstName.text.toString()
                        val studentLastName: String = binding.edtStuLastName.text.toString()

                        intent.putExtra("STU_FIRST_NAME", studentFirstName)
                        intent.putExtra("STU_LAST_NAME", studentLastName)
                        Log.i(TAG, "$studentFirstName -> $studentLastName")
                        startActivity(intent)
                        finish()

                    } else {
                        Log.e(TAG, it.exception.toString())
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
        fun onLinkLoginClick(view: View) {
            val intent = Intent(applicationContext, StudentLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        fun onBackClick(view: View) {
            finish()
        }
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }

    override fun onResume() {
        super.onResume()

        /*Delete EditText When Go to NextPage*/
        binding.edtStuFirstName.setText("")
        binding.edtStuLastName.setText("")
        binding.edtStudentEmailRegister.setText("")
        binding.edtStudentPasswordRegister.setText("")

    }
}
