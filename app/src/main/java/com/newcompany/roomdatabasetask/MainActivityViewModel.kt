package com.newcompany.roomdatabasetask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.newcompany.roomdatabasetask.api.UserApi
import com.newcompany.roomdatabasetask.db.AppDatabase
import com.newcompany.roomdatabasetask.db.User
import com.newcompany.roomdatabasetask.db.UserDAO
import com.newcompany.roomdatabasetask.model.NewsResponse
import com.newcompany.roomdatabasetask.repository.UserRepository
import com.newcompany.roomdatabasetask.utils.Event
import com.newcompany.roomdatabasetask.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


class MainActivityViewModel(application: Application):AndroidViewModel(application) {
    //downloading
    private val _downloading: MutableLiveData<Boolean> = MutableLiveData()
    val downloading: LiveData<Boolean> = _downloading

    val quantityEntries: List<String> = getStaticSpinnerData()
    private  var isUpdateOrAdd= false
    private lateinit var updateUser:User
     val inputUserName = MutableLiveData<String>()
     val inputMobile = MutableLiveData<String>()
     var inputBook = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    private val statusMessage = MutableLiveData<Event<String>>()
     val message:LiveData<Event<String>> get()=statusMessage


    @Inject
    lateinit var apiService: UserApi
    fun getStaticSpinnerData():List<String>
    {

       return listOf("Select Book...", "Book A", "Book B", "Book C")
    }
    @Inject
    lateinit var appDatabase: AppDatabase

    @Inject
    lateinit var userDAO: UserDAO

    @Inject
    lateinit var userRepository: UserRepository

    lateinit var allUsers: MutableLiveData<List<User>>

    init {
        (application as MyApp).getAppComponent().inject(this)
       allUsers= MutableLiveData()
        inputBook.value="Select Book..."
        saveOrUpdateButtonText.value="Save"

    }

    fun onSaveButtonClick()
    {
        if (inputUserName.value == null || inputUserName.value == "") {
            statusMessage.value = Event("Please Enter Name.")
        } else if (inputMobile.value == null || inputMobile.value == "") {
            statusMessage.value = Event("Please Enter Mobile Number.")
        } else if (inputBook.value==null || inputBook.value=="Select Book...") {
            statusMessage.value = Event("Please Select Book.")
        } else {
            val name=inputUserName.value!!
            val mobile=inputMobile.value!!
            val book=inputBook.value!!
            if(!isUpdateOrAdd)
            saveUser(User(0, name, mobile, book))
            else
            {
                updateUser.user_name=name
                updateUser.user_mobile=mobile
                updateUser.user_book=book
                updateUser(updateUser)
            }

        }
    }


      fun saveUser(user: User)
     =viewModelScope.launch {
         val newRowId =userRepository.insertUser(user, appDatabase)
          if (newRowId > -1) {
              statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
              inputUserName.value=""
              inputMobile.value=""
              inputBook.value="Select Book..."
          } else {
              statusMessage.value = Event("Error Occurred")
          }

     }

    fun initUserBeforeUpdate(user: User)
    {
        updateUser = user
        inputUserName.value=updateUser.user_name
        inputMobile.value=updateUser.user_mobile
        inputBook.value=updateUser.user_book
        saveOrUpdateButtonText.value="Update"
        isUpdateOrAdd= true


    }

    fun updateUser(user: User)
    = viewModelScope.launch {
        val noOfRowsDeleted=userRepository.updateUser(user,appDatabase)
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Updated Successfully")
            inputUserName.value=""
            inputMobile.value=""
            inputBook.value="Select Book..."
            isUpdateOrAdd=false
            saveOrUpdateButtonText.value="Save"
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun deleteUser(user: User)
    {

        viewModelScope.launch {

            val noOfRowsDeleted = userRepository.deleteUser(user, appDatabase)
            if (noOfRowsDeleted > 0) {
                statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
            } else {
                statusMessage.value = Event("Error Occurred")
            }
        }

    }

     fun getUsers()
    = userRepository.getAllUsers(appDatabase)

    fun deleteAllUsers()
    {
        viewModelScope.launch {
            val noOfRowsDeleted = userRepository.deleteAllUser(appDatabase)
            if(noOfRowsDeleted>0)
            {
                statusMessage.value= Event("$noOfRowsDeleted Rows Deleted  Successfully.")
            }

        }
    }

    fun setDownloading(downloading: Boolean) {
        _downloading.value = downloading
    }

}