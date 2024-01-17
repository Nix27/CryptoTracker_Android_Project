package hr.algebra.cryptotracker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.algebra.cryptotracker.enums.UserResponse
import hr.algebra.cryptotracker.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private const val USERS_NODE = "users"

class UserRepositoryImpl : UserRepository {

    private val usersNode = FirebaseDatabase.getInstance().reference.child(USERS_NODE)

    override suspend fun registerUser(newUser: User): UserResponse {
        return withContext(Dispatchers.IO) {
            val snapshot =
                usersNode.orderByChild("username").equalTo(newUser.username).get().await()
            try {
                if (!snapshot.exists()) {
                    val id = usersNode.push().key
                    newUser.id = id
                    usersNode.child(id!!).setValue(newUser)
                    UserResponse.SUCCESS
                } else {
                    UserResponse.EXISTS
                }
            } catch (error: Exception) {
                UserResponse.ERROR
            }
        }
    }

    override suspend fun loginUser(user: User): UserResponse {
        return withContext(Dispatchers.IO) {
            val snapshot = usersNode.orderByChild("username").equalTo(user.username).get().await()
            try {
                if (snapshot.exists()) {
                    val userFromDb = snapshot.children.first().getValue(User::class.java)

                    if (userFromDb != null && userFromDb.password == user.password) {
                        UserResponse.SUCCESS
                    } else {
                        UserResponse.NOT_FOUND
                    }
                } else {
                    UserResponse.NOT_FOUND
                }
            } catch (error: Exception) {
                UserResponse.ERROR
            }
        }
    }
}