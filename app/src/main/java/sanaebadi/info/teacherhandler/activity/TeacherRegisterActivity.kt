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
import sanaebadi.info.teacherhandler.database.teacherNameFamily.TeacherNameFamily
import sanaebadi.info.teacherhandler.databinding.ActivityTeacherRegisterBinding
import sanaebadi.info.teacherhandler.viewModel.TeacherNameFamilyViewModel

class TeacherRegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityTeacherRegisterBinding

    private lateinit var teacherId: String
    private lateinit var teacherPass: String
    private lateinit var teacherFirstName: String
    private lateinit var teacherLastName: String

    companion object {
        const val TAG: String = "TeacherRegisterActivity"
    }

    private lateinit var firebaseAuth: FirebaseAuth


    private lateinit var teacherNameFamilyViewModel: TeacherNameFamilyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_register)

        /*Fetch Room Database Data*/
        teacherNameFamilyViewModel = ViewModelProviders.of(this).get(TeacherNameFamilyViewModel::class.java)


        /*addTextChangedListener*/
        binding.edtTeacherIdRegister.addTextChangedListener(fillAllTextWatcher)
        binding.edtTeacherPasswordRegister.addTextChangedListener(fillAllTextWatcher)
        binding.edtTeacherFirstName.addTextChangedListener(fillAllTextWatcher)
        binding.edtTeacherLastName.addTextChangedListener(fillAllTextWatcher)


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

            teacherId = binding.edtTeacherIdRegister.text.toString()
            teacherPass = binding.edtTeacherPasswordRegister.text.toString()
            teacherFirstName = binding.edtTeacherFirstName.text.toString()
            teacherLastName = binding.edtTeacherLastName.text.toString()

            binding.btnTeacherRegister.isEnabled =
                    (!teacherId.isEmpty() && !teacherPass.isEmpty() && !teacherFirstName.isEmpty() &&
                            !teacherLastName.isEmpty())

            if (binding.btnTeacherRegister.isEnabled) {
                binding.btnTeacherRegister.setBackgroundResource(R.drawable.launch_btn_shape)
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
        fun onRegisterBtnClick(view: View) {

            /*Define Edit Text Input*/
            teacherFirstName = binding.edtTeacherFirstName.text.toString()
            teacherLastName = binding.edtTeacherLastName.text.toString()

            val dialog: ProgressDialog = ProgressDialog.show(
                this@TeacherRegisterActivity, getString(R.string.please_wait),
                getString(R.string.processing), true
            )

            if (!TextUtils.isEmpty(teacherId) && !TextUtils.isEmpty(teacherPass)) {
                firebaseAuth.createUserWithEmailAndPassword(teacherId, teacherPass).addOnCompleteListener {
                    dialog.dismiss()
                    if (it.isSuccessful) {
                        val snackbar: Snackbar = Snackbar.make(
                            binding.coordinator,
                            getString(R.string.register_succesed), Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()


                        /*Insert TO database*/
                        val teacherNameFamily =
                            TeacherNameFamily(
                                teacherFirstName,
                                teacherLastName
                            )

                        if (teacherFirstName.trim().isNotEmpty() && teacherLastName.trim().isNotEmpty()) {
                            teacherNameFamilyViewModel.insertTeacherNameFamily(teacherNameFamily)


                        } else {
                            Snackbar.make(
                                binding.coordinator,
                                getString(R.string.add_warning_empty_edit_text),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }


                        /*Intent to Login Activity*/
                        val intent = Intent(applicationContext, TeacherLoginActivity::class.java)

                        /*Get Edit Text Input To Bundle Them To Next Page*/
                        val teacherFirstName: String = binding.edtTeacherFirstName.text.toString()
                        val teacherLastName: String = binding.edtTeacherLastName.text.toString()

                        intent.putExtra("TEACHER_FIRST_NAME", teacherFirstName)
                        intent.putExtra("TEACHER_LAST_NAME", teacherLastName)

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
        fun onLinkLoginClick(view: View) {
            val intent = Intent(applicationContext, TeacherLoginActivity::class.java)
            startActivity(intent)
        }
    }
}
