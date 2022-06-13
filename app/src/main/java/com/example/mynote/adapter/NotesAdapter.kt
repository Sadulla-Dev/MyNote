package com.example.mynote.adapter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mynote.R
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.entities.Notes
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import kotlinx.android.synthetic.main.item_rv_note.view.*
import org.commonmark.node.SoftLineBreak


class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val markwon = Markwon.builder(view.context)
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TaskListPlugin.create(view.context))
            .usePlugin(object : AbstractMarkwonPlugin(){
                override fun configureVisitor(builder: MarkwonVisitor.Builder) {
                    super.configureVisitor(builder)
                    builder.on(
                        SoftLineBreak::class.java
                    ){visitor , _-> visitor.forceNewLine()}
                }
            })
            .build()
    }


    var listener:OnItemClickListener? = null
    var arrList = ArrayList<Notes>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_note,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNotesList: List<Notes>){
        arrList = arrNotesList as ArrayList<Notes>
    }

    fun setOnClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        val note = arrList[position]
        holder.itemView.tvTitle.text = arrList[position].title
//        holder.itemView.tvDesc.text = arrList[position].noteText
        holder.markwon.setMarkdown(holder.itemView.tvDesc,note.noteText.toString())
        holder.itemView.tvDateTime.text = arrList[position].dateTime

        if (arrList[position].color != null){
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
        }else{
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(R.color.ColorLightBlack.toString()))
        }

        if (arrList[position].imgPath != null){
            holder.itemView.imgNote.setImageBitmap(BitmapFactory.decodeFile(arrList[position].imgPath))
            holder.itemView.imgNote.visibility = View.VISIBLE
        }else{
            holder.itemView.imgNote.visibility = View.GONE
        }

        if (arrList[position].webLink != ""){
            holder.itemView.tvWebLink.text = arrList[position].webLink
            holder.itemView.tvWebLink.visibility = View.VISIBLE
        }else{
            holder.itemView.tvWebLink.visibility = View.GONE
        }

        holder.itemView.cardView.setOnClickListener {
            listener!!.onClicked(arrList[position].id!!)
        }

    }



    interface OnItemClickListener{
        fun onClicked(noteId:Int)
    }

}