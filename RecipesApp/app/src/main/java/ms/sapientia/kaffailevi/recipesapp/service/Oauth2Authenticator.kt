package ms.sapientia.kaffailevi.recipesapp.service

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


object OAuth2Authenticator {
    private const val TOKEN_URL = "https://oauth2-server.com/token" // Your OAuth2 server token URL
    private const val CLIENT_ID = "162589133748-qjgufs6rv44fcrt4q8dstre6v1elo4cs.apps.googleusercontent.com"
    private const val CLIENT_SECRET = "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"

    fun getAccessToken() {
        val client = OkHttpClient()

        // Prepare the body of the POST request using FormBody.Builder
        val body: RequestBody = FormBody.Builder()
            .add("grant_type", "client_credentials")
            .add("client_id", CLIENT_ID)
            .add("client_secret", CLIENT_SECRET)
            .build()

        // Create the request object
        val request = Request.Builder()
            .url(TOKEN_URL)
            .post(body) // Attach the body to the POST request
            .build()

        // Make the request asynchronously
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // Handle failure
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Parse the response to get the access token
                    val responseBody = response.body?.string()
                    responseBody?.let {
                        try {
                            val jsonResponse = JSONObject(it)
                            val accessToken = jsonResponse.getString("access_token")
                            // Store the token securely
                            storeToken(accessToken)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                } else {
                    // Handle failure (e.g., invalid credentials)
                }
            }
        })
    }

    fun storeToken(token: String) {
        Log.d("OAuth2Authenticator", "Stored token: $token")
    }
}