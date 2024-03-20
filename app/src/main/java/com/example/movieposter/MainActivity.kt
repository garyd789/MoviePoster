package com.example.movieposter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.movieposter.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import coil.load

private const val TAG = "MainActivity"
private const val API_KEY = "f676e66b"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var userInput = ""
    private var movieTitle = ""
    private var movieYear = ""
    private var movieImage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()


        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()



        val omdbApi: OMDBApi = retrofit.create<OMDBApi>(OMDBApi::class.java)

        binding.button.setOnClickListener() {
            lifecycleScope.launch {
                try {
                    val response = omdbApi.fetchPoster(API_KEY,userInput)
                    binding.Title.setText(response.title)
                    binding.Year.setText(response.year)
                    binding.movieImage.load(response.image)

                    Log.d(TAG, response.title)
                } catch (ex: Exception) {
                    Log.e(TAG, "Failed to fetch: $ex")
                }
            }
        }

        binding.userInput.doOnTextChanged() { text,_,_,_ ->
            userInput = text.toString()

        }



    }









}