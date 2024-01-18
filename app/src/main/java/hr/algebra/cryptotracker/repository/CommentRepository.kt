package hr.algebra.cryptotracker.repository

import hr.algebra.cryptotracker.enums.CustomResponse
import hr.algebra.cryptotracker.model.Comment

interface CommentRepository {
    suspend fun getAllCommentsFor(currencyId: String): MutableList<Comment>
    suspend fun addComment(newComment: Comment): CustomResponse
}