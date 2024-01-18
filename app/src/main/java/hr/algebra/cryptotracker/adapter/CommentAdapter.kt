package hr.algebra.cryptotracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.cryptotracker.R
import hr.algebra.cryptotracker.model.Comment

class CommentAdapter(
    private val context: Context,
    private val comments: List<Comment>
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    class ViewHolder(commentView: View) : RecyclerView.ViewHolder(commentView) {
        private val tvUser = commentView.findViewById<TextView>(R.id.tvUser)
        private val tvComment = commentView.findViewById<TextView>(R.id.tvComment)

        fun bind(comment: Comment) {
            tvUser.text = comment.text
            tvComment.text = comment.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.comment_cardview, parent, false)
        )
    }

    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }
}