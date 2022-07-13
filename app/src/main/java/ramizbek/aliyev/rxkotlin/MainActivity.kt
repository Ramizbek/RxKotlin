package ramizbek.aliyev.rxkotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ramizbek.aliyev.rxkotlin.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        createButtonObservable().subscribe() {
//            binding.tvInfo.text = it
//        }
        createButtonObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
//            .map {
//                it.plus(it) //map keladigan ma'lumotni o'zgartirib beradi
//            }
//            .filter{
//                it.length == 5 //filter shart berish uchun
//            }
            .debounce(5L, TimeUnit.SECONDS) //5 sekundan keyin ishla! Faqat bacground uchun viewlar uchun emas xato beradi viewlar uchun handlar yoki boshqasidan ishlatish kerak
            .subscribe{
                Log.d(TAG, "onCreate: $it")
            }

    }

    //        Observable - obektlarni eshitib turadigan xususiyat

    private fun createButtonObservable(): Observable<String> {
        return Observable.create { emitter -> //emitter RxJava ichida bor xabarlarni yuborib turadigan classi
            //editText texti o'zgarganda
            binding.edtName.addTextChangedListener {
                emitter.onNext(binding.edtName.text.toString())
            }
            //button bosilganda
            emitter.setCancellable(null) //emitter ishini bajarib bo'lgandan keyin cancel bo'lishi
        }
    }
}