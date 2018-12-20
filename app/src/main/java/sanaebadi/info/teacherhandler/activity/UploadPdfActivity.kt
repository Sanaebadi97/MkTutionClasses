package sanaebadi.info.teacherhandler.activity

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivityUploadPdfBinding
import sanaebadi.info.teacherhandler.handler.Constants
import sanaebadi.info.teacherhandler.model.Upload
import java.util.*


class UploadPdfActivity : BaseActivity() {


    //this is the pic pdf code used in file chooser
    companion object {
        const val PICK_PDF_CODE = 2342
        const val TAG = "UploadPdfActivity"
        const val REQUEST_ID_MULTIPLE_PERMISSIONS = 1


    }

    //the firebase objects for storage and database
    private var mStorageReference: StorageReference? = null
    private var mDatabaseReference: DatabaseReference? = null

    private var receiver: ConnectivityListenerReceiver? = null

    public lateinit var binding: ActivityUploadPdfBinding

    private lateinit var fileName: String

     var downloadUrl: Uri? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_pdf)


        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS)


        binding.edtFileName.addTextChangedListener(fillAllTextWatcher)
        /*Handlers*/
        val handlers = MyHandlers()
        binding.handler = handlers
    }


    //this function will get the pdf from the storage

    fun getPdf() {

        /*get Permission*/

        if (checkAndRequestPermissions()) {

            //creating an intent for file chooser
            val intent = Intent()
            intent.type = "application/pdf"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_pdf)), PICK_PDF_CODE)

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.data != null) {
            //if a file is selected
            if (data.data != null) {

                downloadUrl = data.data!!
                //uploading the file
                //uploadFile(data.data!!)
                uploadFile(downloadUrl!!)

            } else {
                Toast.makeText(this, getString(R.string.no_file_chosen), Toast.LENGTH_SHORT).show()
            }
        }
    }


    //this method is uploading the file
    @SuppressWarnings("visiableForTest")
    private fun uploadFile(data: Uri) {
        if (data != null) {
            binding.progressbar.visibility = View.VISIBLE

            /*get the storage reference*/
            val sRef: StorageReference =
                mStorageReference!!.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf")

            /*Add File To the reference*/
            sRef.putFile(this.downloadUrl!!)
                .addOnSuccessListener { taskSnapshot ->

                    binding.progressbar.visibility = View.GONE
                    binding.txtFileStatus.text = getString(R.string.upload_successfuly)

                    /*Save Pdf In Firebase Database*/
                    val upload =
                        Upload(binding.edtFileName.text.toString(), taskSnapshot.storage.downloadUrl.toString())
                    mDatabaseReference!!.child(mDatabaseReference!!.push().key!!).setValue(upload)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext,
                        exception.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    binding.txtFileStatus.text = progress.toInt().toString() + "% Uploading..."

                }
        }
    }

    /*Click Listener On Views*/
    inner class MyHandlers {
        fun onUploadClick(view: View) {
            getPdf()
        }

        fun onShowUploadFiles(view: View) {
            val intent = Intent(applicationContext, ViewUploadActivity::class.java)
            intent.data = downloadUrl
            startActivity(intent)
        }

        fun onBackClick(view: View) {
            finish()
        }

    }

    /*Method If All EditText Are fill*/
    private val fillAllTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            fileName = binding.edtFileName.text.toString()


            binding.btnUpload.isEnabled = (!fileName.isEmpty())

            if (binding.btnUpload.isEnabled) {
                binding.btnUpload.setBackgroundResource(R.drawable.launch_btn_shape)
            }
        }

        override fun afterTextChanged(s: Editable) {

        }
    }


    /*Handel Network Connection*/
    inner class ConnectivityListenerReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo: NetworkInfo?
            networkInfo = connectivityManager.activeNetworkInfo
            val isConnected = networkInfo != null && networkInfo.isConnected
            if (isConnected) {
                binding.txtUploadTitle.text = context.getString(R.string.upload_pdf_title)
            } else {
                binding.txtUploadTitle.text = context.getString(R.string.wait_network)


            }


        }

    }

    /*Set Anim*/
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }

    /*Fun Permission RunTime*/
    private fun checkAndRequestPermissions(): Boolean {

        val readPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ContextCompat.checkSelfPermission(
                this@UploadPdfActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } else {
            TODO("VERSION.SDK_INT < JELLY_BEAN")
        }
        val writePermission = ContextCompat.checkSelfPermission(
            this@UploadPdfActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val listPermissionNeeded = ArrayList<String>()

        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                listPermissionNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        }


        if (!listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this@UploadPdfActivity,
                listPermissionNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS
            )

            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        fun showDialogOk(message: String, okListener: DialogInterface.OnClickListener) {
            AlertDialog.Builder(this@UploadPdfActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show()
        }

        fun explain(msg: String) {
            val dialog = AlertDialog.Builder(this@UploadPdfActivity)
            dialog.setMessage(msg)
                .setPositiveButton("Yes") { _, _ ->
                    startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package sanaebadi.info.teacherhandler")
                        )
                    )
                }
                .setNegativeButton(
                    "Cancel"
                ) { _, _ -> finish() }

            dialog.show()
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

    }

}






