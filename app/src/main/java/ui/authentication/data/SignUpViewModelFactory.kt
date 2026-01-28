package ui.authentication.data
//
//import android.content.Context
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//
//class SignUpViewModelFactory(
//    private val context: Context
//) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
//            val repository = AuthRepository(
//                context = context.applicationContext,
//                api = RetrofitProvider.authApi
//            )
//            return SignUpViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
