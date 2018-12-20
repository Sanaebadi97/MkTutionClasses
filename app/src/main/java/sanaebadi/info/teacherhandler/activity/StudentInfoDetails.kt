package sanaebadi.info.teacherhandler.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityStudentInfoDetailsBinding

class StudentInfoDetails : BaseActivity() {

    private lateinit var binding: ActivityStudentInfoDetailsBinding

    private lateinit var stuName: String
    private lateinit var stuStartDate: String
    private lateinit var stuFeeDue: String
    private lateinit var stuFeeDeposit: String
    private lateinit var stuTestReport: String
    private lateinit var stuMobileNom: String


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_info_details)


        /*get Intent From StudentInfoActivity*/
        stuName = intent.getStringExtra("STU_NAME")
        stuStartDate = intent.getStringExtra("STU_START_DATE")
        stuFeeDue = intent.getStringExtra("STU_FEE_DUE")
        stuFeeDeposit = intent.getStringExtra("STU_FEE_DEPOSIT")
        stuTestReport = intent.getStringExtra("STU_TEST_REPORT")
        stuMobileNom = intent.getStringExtra("STU_MOBILE_NOM")


        /*Set Data On TextView*/
        binding.txtStuName.text = stuName
        binding.txtStuStartDate.text = getString(R.string.starting_date) + " : " + stuStartDate
        binding.txtStuFeeDue.text = getString(R.string.fee_due) + " : " + stuFeeDue
        binding.txtStuFeeDeposit.text = getString(R.string.fee_deposit) + " : " + stuFeeDeposit
        binding.txtStuTestReport.text = getString(R.string.test_report) + " : " + stuTestReport
        binding.txtStuPhoneNom.text = getString(R.string.mobile_nom) + " : " + stuMobileNom

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }
}
