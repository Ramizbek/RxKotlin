package ramizbek.aliyev.rxkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import ramizbek.aliyev.rxkotlin.adapters.UserAdapter
import ramizbek.aliyev.rxkotlin.database.AppDatabase
import ramizbek.aliyev.rxkotlin.database.User
import ramizbek.aliyev.rxkotlin.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private val TAG = "MainActivity2"

    private lateinit var binding: ActivityMain2Binding

    lateinit var userAdapter: UserAdapter
    lateinit var appDatabase: AppDatabase

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDatabase.getInstance(this)

        userAdapter = UserAdapter(object : UserAdapter.OnItemCLickListener{
            override fun onItemClick(user: User) {

            }

        })

        binding.rv.adapter = userAdapter

        appDatabase.userDao().getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<List<User>>{

                override fun accept(t: List<User>?) {
                    userAdapter.submitList(t)
                }
            })
        binding.btnSave.setOnClickListener {
            val user = User()
            user.userName = binding.edtName.text.toString()
            user.password = binding.edtUsername.text.toString()
            appDatabase.userDao().addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{t->
                    Log.d(TAG, "$t")
                }
        }
    }
}