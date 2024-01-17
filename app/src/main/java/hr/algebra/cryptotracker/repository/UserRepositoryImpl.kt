package hr.algebra.cryptotracker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.algebra.cryptotracker.model.User

private const val USERS_NODE = "users"

class UserRepositoryImpl : UserRepository {

    private val usersNode = FirebaseDatabase.getInstance().reference.child(USERS_NODE)

    override fun registerUser(username: String, password: String): String {
        var result = ""

        usersNode.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists()) {
                    val id = usersNode.push().key
                    val user = User(id, username, password)
                    usersNode.child(id!!).setValue(user)
                    result = "success"
                } else {
                    result = "User already exists!"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                result = "Database error: ${error.message}"
            }
        })

        return result
    }

    override fun loginUser(username: String, password: String): String {
        var result = ""

        usersNode.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)

                        if(user != null && user.password == password) {
                            result = "success"
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                result = "Database error: ${error.message}"
            }

        })

        return result
    }
}