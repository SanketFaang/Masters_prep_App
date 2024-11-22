package com.jhainusa.testsxperts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class Chatbot : Fragment() {

    private lateinit var chatContainer: LinearLayout
    private lateinit var inputMessage: EditText
    private lateinit var sendButton: Button
    private lateinit var chatScrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_chatbot, container, false)

        // Initialize views
        chatContainer = rootView.findViewById(R.id.chatContainer)
        inputMessage = rootView.findViewById(R.id.inputMessage)
        sendButton = rootView.findViewById(R.id.sendButton)
        chatScrollView = rootView.findViewById(R.id.chatScrollView)

        // Handle send button click
        sendButton.setOnClickListener {
            val userMessage = inputMessage.text.toString()
            if (userMessage.isNotBlank()) {
                addMessage(userMessage, isUser = true)  // Display user message
                inputMessage.text.clear()  // Clear input field
                sendMessageToGemini(userMessage)  // Send message to Gemini API
            }
        }

        return rootView
    }

    // Function to add a message to the chat container
    private fun addMessage(message: String, isUser: Boolean) {
        val messageView = TextView(requireContext())
        messageView.text = message
        messageView.textSize = 16f
        messageView.setPadding(16, 8, 16, 8)
        messageView.setBackgroundResource(
            if (isUser) R.drawable.user_message_background else R.drawable.bot_message_background
        )

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(0, 8, 0, 8)
        params.gravity = if (isUser) View.FOCUS_RIGHT else View.FOCUS_LEFT

        messageView.layoutParams = params
        chatContainer.addView(messageView)

        // Scroll to the bottom after adding the message
        chatScrollView.post { chatScrollView.fullScroll(View.FOCUS_DOWN) }
    }

    // Function to send message to Gemini API
    private fun sendMessageToGemini(message: String) {
        val client = OkHttpClient()
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyBkHQYZ_9NeHDJQuXTRJo_ECBav3pUJre0" // Replace with your actual API key

        val json = JSONObject()

        // Structure the JSON payload correctly for Gemini API
        val contentsArray = JSONArray()
        val partsArray = JSONArray()

        partsArray.put(JSONObject().put("text", message))
        contentsArray.put(JSONObject().put("parts", partsArray))

        json.put("contents", contentsArray)

        // Create request body with proper media type
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = RequestBody.create(mediaType, json.toString())

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    addMessage("Failed to connect to the API. Please try again.", isUser = false)
                }
                Log.e("API Error", e.message ?: "Unknown error")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    requireActivity().runOnUiThread {
                        addMessage("Error: ${response.code}", isUser = false)
                    }
                    Log.e("API Error", "Response error: ${response.code}")
                    return
                }

                response.body?.use { responseBody ->
                    val responseString = responseBody.string()
                    Log.d("API Response", responseString) // Log the full response string for debugging

                    try {
                        val responseJson = JSONObject(responseString)
                        val candidates = responseJson.optJSONArray("candidates")

                        if (candidates != null && candidates.length() > 0) {
                            val content = candidates.optJSONObject(0)?.optJSONObject("content")
                            val parts = content?.optJSONArray("parts")
                            val botReply = parts?.optJSONObject(0)?.optString("text") ?: "No reply found."

                            Log.d("API Response", botReply ?: "No response")
                            requireActivity().runOnUiThread {
                                addMessage(botReply, isUser = false)  // Display bot reply
                            }
                        } else {
                            requireActivity().runOnUiThread {
                                addMessage("No candidates in the response.", isUser = false)
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("API Parsing Error", "Error parsing the response: ${e.message}")
                        requireActivity().runOnUiThread {
                            addMessage("Error parsing the response.", isUser = false)
                        }
                    }
                }
            }
        })
    }
}
