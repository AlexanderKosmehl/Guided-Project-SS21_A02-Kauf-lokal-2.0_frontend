package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Event
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
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

        val messageText: TextView = view.findViewById(R.id.message_tv)
        val messageImage: ImageView = view.findViewById(R.id.message_iv)
        val messageTitle: TextView = view.findViewById(R.id.message_title)
        val messageAuthorImage: ImageView = view.findViewById(R.id.message_author_image)
        val messageAuthorName: TextView = view.findViewById(R.id.message_author_name)
        val messageDate: TextView = view.findViewById(R.id.message_date)

        val url = "http://10.0.2.2:8080/message/"

        val request = JsonObjectRequest(
            Request.Method.GET, url+args.event.refId, null,
            { response ->
                // JSONArray does not support iterable which means this has to be a regular for loop
                val message = gson.fromJson(response.toString(), com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Message::class.java)

                messageText.text = message.message
                Glide.with(this).load(message.imageURL).into(messageImage)
                messageTitle.text = message.title
                messageDate.text = message.formatDate()

                addAuthor(message.vendorId, view)

            },
            { error ->
                // TODO Add meaningful error handling
                Toast.makeText(context, "No content found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
            }
        )
        RequestSingleton.getInstance(context).addToRequestQueue(request)
    }

    fun addAuthor(vendorId:String, view: View) {
        val url = "http://10.0.2.2:8080/vendor/"
        val gson = Gson()
        val context = view.context
        val messageAuthorName: TextView = view.findViewById(R.id.message_author_name)
        val messageAuthorImage: ImageView = view.findViewById(R.id.message_author_image)

        val request = JsonObjectRequest(
            Request.Method.GET, url + vendorId, null,
            { response ->

                val vendor = gson.fromJson(response.toString(), Vendor::class.java)
                messageAuthorName.text = vendor.name

                // TODO: Author image
                Glide.with(this).load(vendor.logo).into(messageAuthorImage)


            },
            { error ->
                Toast.makeText(context, "No Vendor found", Toast.LENGTH_SHORT).show()
                Log.e("Response", error.message ?: "Kein Vendor vorhanden")
            }
        )
        RequestSingleton.getInstance(context).addToRequestQueue(request)



    }

}