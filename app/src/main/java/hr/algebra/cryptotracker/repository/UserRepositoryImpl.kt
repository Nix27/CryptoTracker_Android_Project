package hr.algebra.cryptotracker.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import hr.algebra.cryptotracker.enums.CustomResponse
import hr.algebra.cryptotracker.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private const val USERS_NODE = "users"

class UserRepositoryImpl : UserRepository {

    private val usersNode = FirebaseDatabase.getInstance("https://cryptotracker-e417a-default-rtdb.europe-west1.firebasedatabase.app").reference.child(USERS_NODE)

    override suspend fun registerUser(newUser: User): CustomResponse {
        return withContext(Dispatchers.IO) {
            val snapshot = usersNode.orderByChild("username").equalTo(newUser.username).get().await()
            try {
                if (!snapshot.exists()) {
                    val id = usersNode.push().key
                    newUser.id = id
                    usersNode.child(id!!).setValue(newUser)
                    CustomResponse.SUCCESS
                } else {
                    CustomResponse.EXISTS
                }
            } catch (e: Exception) {
                Log.e("RegisterUser", "Error: ${e.message}", e)
                CustomResponse.ERROR
            }
        }
    }

    override suspend fun loginUser(user: User): CustomResponse {
        return withContext(Dispatchers.IO) {
            val snapshot = usersNode.orderByChild("username").equalTo(user.username).get().await()
            try {
                if (snapshot.exists()) {
                    val userFromDb = snapshot.children.first().getValue(User::class.java)

                    if (userFromDb != null && userFromDb.password == user.password) {
                        CustomResponse.SUCCESS
                    } else {
                        CustomResponse.WRONG
                    }
                } else {
                    CustomResponse.NOT_FOUND
                }
            } catch (e: Exception) {
                Log.e("LoginUser", "Error: ${e.message}", e)
                CustomResponse.ERROR
            }
        }
    }
}