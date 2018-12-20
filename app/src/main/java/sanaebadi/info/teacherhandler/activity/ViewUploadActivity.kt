package sanaebadi.info.teacherhandler.activity

import RecyclerItemTouchHelper
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.adapter.ViewUploadAdapter
import sanaebadi.info.teacherhandler.databinding.ActivityViewUploadBinding
import sanaebadi.info.teacherhandler.handler.Constants
import sanaebadi.info.teacherhandler.model.Upload


class ViewUploadActivity : BaseActivity(), ViewUploadAdapter.OnItemClickListener,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {


    companion object {
        const val TAG: String = "ViewUploadActivity"
    }

    private lateinit var binding: ActivityViewUploadBinding

    var downloadUrl: Uri? = null

    //database reference to get uploads data
    var mDatabaseReference: DatabaseReference? = null
    //    //list to store uploads data
    var uploadList: ArrayList<Upload>? = null

    lateinit var adapter: ViewUploadAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_upload)

        uploadList = ArrayList()


        binding.imgBack.setOnClickListener {
            finish()
        }

        /*Get Intent From Upload Activity*/

        if (intent.extras != null) {
            if (downloadUrl != null) {
                downloadUrl = intent.data
            }
        } else
            Log.e("console", "Data is null")


        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS)

        //retrieving upload data from firebase database

        binding.progressbar.visibility = View.VISIBLE

        mDatabaseReference!!.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (postSnapshot in dataSnapshot.children) {
                        val upload = postSnapshot.getValue(Upload::class.java)
                        if (upload != null) {
                            uploadList!!.add(upload)

                            Log.i(TAG, "Upload List : " + uploadList!!.size)
                        }
                    }


                    //RecyclerView config
                    binding.rvShowPdfUploaded.setHasFixedSize(true)
                    binding.rvShowPdfUploaded.itemAnimator = DefaultItemAnimator()
                    binding.rvShowPdfUploaded.addItemDecoration(
                        DividerItemDecoration(
                            applicationContext,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    binding.rvShowPdfUploaded.setItemViewCacheSize(20)
                    binding.rvShowPdfUploaded.clearOnScrollListeners()
                    binding.rvShowPdfUploaded.layoutManager =
                            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                    adapter = ViewUploadAdapter(
                        applicationContext,
                        uploadList!!,
                        this@ViewUploadActivity
                    )


                    binding.rvShowPdfUploaded.adapter = adapter

                    binding.progressbar.visibility = View.GONE


                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })


        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param

        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvShowPdfUploaded)
    }


    /*Interface To Click ON item To Open Url*/
    override fun onItemClick(position: Int) {
        //Opening the upload file in browser using the upload url

        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(downloadUrl.toString())
            startActivity(intent)

        } catch (e: Exception) {
            Log.i(TAG, "Download Url : " + downloadUrl)
            Log.e(TAG, e.message)
        }
    }


    /*Set Anim*/
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out)
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is ViewUploadAdapter.ViewUploadViewHolder) {
            // get the removed item name to display it in snack bar
            val name = uploadList!![viewHolder.adapterPosition].name

            // backup of removed item for undo purpose
            val deletedItem = uploadList!![viewHolder.adapterPosition]
            val deletedIndex = viewHolder.adapterPosition

            // remove the item from recycler view
            adapter.removeItem(viewHolder.adapterPosition)
            Log.i(TAG, "RemoveSIZE " + uploadList!!.size)

            // showing snack bar with Undo option
            val snackbar = Snackbar
                .make(binding.coordinator, "$name removed from !", Snackbar.LENGTH_LONG)
            snackbar.setAction("UNDO") {
                // undo is selected, restore the deleted item
                adapter.restoreItem(deletedItem, deletedIndex)
            }
            snackbar.setActionTextColor(
                ContextCompat.getColor(applicationContext, R.color.colorAccent))
            snackbar.show()
        }
    }

    override fun onResume() {
        super.onResume()

    }

}


