package hr.algebra.cryptotracker.enums

enum class UserResponse(description: String) {
    SUCCESS("Success"),
    EXISTS("User already exists!"),
    NOT_FOUND("User not found"),
    ERROR("Unable to register")
}