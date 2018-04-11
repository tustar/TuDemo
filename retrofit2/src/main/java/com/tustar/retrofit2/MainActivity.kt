package com.tustar.retrofit2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tustar.common.util.Logger
import com.tustar.retrofit2.model.Repo
import com.tustar.retrofit2.net.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit2_main)

        // Create a call instance for looking up Retrofit Repos.
        val call = GitHubService.gitHub.repos("tustar")

        // Fetch and print a list of the Repos to the library.
        call.enqueue(getRepos())
    }

    private fun getRepos(): Callback<List<Repo>> {
        return object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                Logger.d("$response")
                val repos = response?.body()
                repos?.forEach { Logger.d("$it") }
            }

            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                Logger.d("Request failure, ${t?.message}")
            }
        }
    }
}
