package sanaebadi.info.teacherhandler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pdf_view_row.view.*
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.model.Upload


class ViewUploadAdapter(
    private val context: Context,
    private val uploadList: ArrayList<Upload>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ViewUploadAdapter.ViewUploadViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewUploadViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pdf_view_row, parent, false)
        return ViewUploadViewHolder(view)

    }

    override fun getItemCount() = uploadList.size

    override fun onBindViewHolder(holder: ViewUploadViewHolder, position: Int) {
        val upload = uploadList[position]
        holder.txtPdfName.text = upload.name

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }

    class ViewUploadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPdfName = view.txt_pdf_name!!
        var viewBackground = view.view_background
        var viewForeground = view.view_foreground
    }

    fun removeItem(position: Int) {
        uploadList.removeAt(position)
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun restoreItem(item: Upload, position: Int) {
        uploadList.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }

}