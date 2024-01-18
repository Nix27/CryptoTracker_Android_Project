package hr.algebra.cryptotracker.enums

enum class CustomResponse(description: String) {
    SUCCESS("Success"),
    EXISTS("Already exists!"),
    WRONG("Wrong data!"),
    NOT_FOUND("Not found"),
    ERROR("Unable to execute request")
}