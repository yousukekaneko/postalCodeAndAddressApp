package com.example.android.sample.postalcodeandaddressapp

import android.os.Bundle
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

        //クリック処理（簡易）
        searchButton.setOnClickListener {
            val inputAddress = textPostalAddress.text.toString()
            resultLabel.text = inputAddress

            //TODO 入力した値が正しいかどうかの処理

                //TODO 正しい場合、fun apiGetを呼ぶ


                val addressResponse = service.apiGet("searchByPostal", inputAddress)
                addressResponse.enqueue(object : retrofit2.Callback<List<AddressResponse>> {
                    override fun onFailure(call: Call<List<AddressResponse>>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(
                        call: Call<List<AddressResponse>>,
                        response: Response<List<AddressResponse>>
                    ) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })


                //TODO 誤っている場合、アラートを出す


        }
    }
}

