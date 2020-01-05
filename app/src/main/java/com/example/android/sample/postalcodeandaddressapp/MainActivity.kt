package com.example.android.sample.postalcodeandaddressapp

import android.os.Bundle
import android.util.Log
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
            val inputAddress = textPostalAddress.text.toString()
//            resultLabel.text = inputAddress

            //TODO 入力した値が正しいかどうかの処理

                //TODO 正しい場合、fun apiGetを呼ぶ


                val location = service.apiGet("searchByPostal", inputAddress)
                location.enqueue(object : retrofit2.Callback<PostalResponse> {

                    override fun onResponse(
                        call: Call<PostalResponse>,
                        response: Response<PostalResponse>
                    ) {
                        Log.d("通信結果", "成功!!!")
                        resultLabel.text = response.body().toString()
                    }
                    override fun onFailure(call: Call<PostalResponse>, t: Throwable) {
                        Log.d("通信結果", "失敗 $t")
                    }
                })
        }
    }
}

