package project.robby.userappnative.utils

sealed class DataHandler<out T> {
    data object Init : DataHandler<Nothing>()
    data object Loading : DataHandler<Nothing>()
    data class Success<T>(val data: T) : DataHandler<T>()
    data class Error(val message: String) : DataHandler<Nothing>()
}