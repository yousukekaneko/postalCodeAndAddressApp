package com.example.android.sample.postalcodeandaddressapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //log interceptorの設定
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        //retrofitインスタンスを生成
        val retrofit = Retrofit.Builder()
            .baseUrl("http://geoapi.heartrails.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val service = retrofit.create(HeartRailsService::class.java)

        //クリック処理
        searchButton.setOnClickListener {
            var inputAddress = textPostalAddress.text.toString()

            //TODO 入力した値が正しいかどうかの処理
            if (inputAddress.length != 7) {
                val invalidDialog = AlertDialog.Builder(this@MainActivity).apply{
                    setTitle(getString(R.string.dialog_title))
                    setMessage(getString(R.string.dialog_message))
                    setPositiveButton(getString(R.string.answer_yes)) { _, _ ->
                    }
                    show()
                }
            }

            val location = service.apiGet("searchByPostal", inputAddress)
            location.enqueue(object : retrofit2.Callback<PostalResponse> {

                override fun onResponse(
                    call: Call<PostalResponse>,
                    response: Response<PostalResponse>
                ) {
                    Log.d("通信結果", "成功!!!")

                    //mapを使わないパターン
//                    var text = ""
//                    val eachAddress = response.body()?.response?.location
//                    if (eachAddress != null) {
//                        for (i in eachAddress) {
//                            text += "都道府県 : ${i.prefecture}\n区 : ${i.city}\n番地 : ${i.town}\n\n"
//                        }
//                    }
//                    resultLabel.text = text

                    //mapを使ったパターン
                    val eachAddress : List<Location>? = response.body()?.response?.location

                    resultLabel.text =
                        eachAddress?.map {
                            "都道府県 : ${it.prefecture}\n区 : ${it.city}\n番地 : ${it.town}\n\n"
                        }?.joinToString()

                }
                override fun onFailure(call: Call<PostalResponse>, t: Throwable) {
                    Log.d("通信結果", "失敗 $t")
                }
            })
        }
    }


}

