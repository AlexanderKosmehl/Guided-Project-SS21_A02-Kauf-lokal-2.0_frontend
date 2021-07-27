package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Event
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.RequestSingleton
import com.google.gson.Gson

class MessageFragment(// TODO: Rename and change types of parameters
    //val event: Event
): Fragment() {

    private val args by navArgs<MessageFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_newsfeed_message, container, false)
            //val recyclerView = view.findViewById<ConstraintLayout>(R.id.detailView)

            setMessage(view)

            return view
        }

    fun setMessage(view: View) {
        val context = view.context
        val gson = Gson()

        val url = "http://10.0.2.2:8080/message/"

        val messageText: TextView = view.findViewById(R.id.message_tv)


        val request = JsonObjectRequest(
            Request.Method.GET, url+args.event.refId, null,
            { response ->
                // JSONArray does not support iterable which means this has to be a regular for loop
                val message = gson.fromJson(response.toString(), com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Message::class.java)

                messageText.text=message.message


            },
            { error ->
                // TODO Add meaningful error handling
                Toast.makeText(context, "No content found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        )
        RequestSingleton.getInstance(context).addToRequestQueue(request)
    }

}