package hr.algebra.cryptotracker.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import hr.algebra.cryptotracker.enums.CustomResponse
import hr.algebra.cryptotracker.model.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private const val COMMENTS_NODE = "comments"

class CommentRepositoryImpl : CommentRepository {

    private val commentsNode = FirebaseDatabase.getInstance("https://cryptotracker-e417a-default-rtdb.europe-west1.firebasedatabase.app").reference.child(COMMENTS_NODE)

    override suspend fun getAllCommentsFor(currencyId: String): MutableList<Comment> {
        return withContext(Dispatchers.IO) {
            val currencyComments = mutableListOf<Comment>()
            val snapshot = commentsNode.orderByChild("currencyId").equalTo(currencyId).get().await()
            try {
                if(snapshot.exists()) {
                    for(commentSnapshot in snapshot.children) {
                        currencyComments.add(commentSnapshot.getValue(Comment::class.java)!!)
                    }
                }
                currencyComments
            } catch (e: Exception) {
                Log.e("GetAllCommitsForCurrency", "Error: ${e.message}", e)
                currencyComments
            }
        }
    }

    override suspend fun addComment(newComment: Comment): CustomResponse {
        return withContext(Dispatchers.IO) {
            try {
                val id = commentsNode.push().key
                newComment.id = id
                commentsNode.child(id!!).setValue(newComment)
                CustomResponse.SUCCESS
            } catch (e: Exception) {
                Log.e("GetAllCommitsForCurrency", "Error: ${e.message}", e)
                CustomResponse.ERROR
            }
        }
    }
}