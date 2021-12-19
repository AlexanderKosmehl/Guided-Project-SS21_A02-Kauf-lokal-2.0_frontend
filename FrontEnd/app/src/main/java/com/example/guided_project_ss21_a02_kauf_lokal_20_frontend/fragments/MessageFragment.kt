package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Message
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Vendor
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.utilities.TitleTexts
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.viewModel.MessageViewModel

class MessageFragment : Fragment() {

    private val args by navArgs<MessageFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_newsfeed_message, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = TitleTexts.MESSAGE

        addMessageVM(view)
        return view
    }

    // TODO: outsource
    private fun setMessage(view: View, message: Message) {
        val messageText: TextView = view.findViewById(R.id.message_tv)
        val messageImage: ImageView = view.findViewById(R.id.message_iv)
        val messageTitle: TextView = view.findViewById(R.id.message_title)
        val messageDate: TextView = view.findViewById(R.id.message_date)

        Log.i("MessageFragment", message.toString())

        messageText.text = message.message
        Glide.with(this).load(message.imageURL).into(messageImage)
        messageTitle.text = message.title
        messageDate.text = message.formatDate()
        addVendorVM(view, message.vendorId)
    }

    // TODO: outsource
    private fun setAuthorVM(view: View, vendor: Vendor) {
        val messageAuthorImage: ImageView = view.findViewById(R.id.message_author_image)
        val messageAuthorName: TextView = view.findViewById(R.id.message_author_name)
        messageAuthorName.text = vendor.name
        // TODO: Author image
        Glide.with(this).load(vendor.logo).into(messageAuthorImage)
    }

    private fun addMessageVM(view: View) {
        val model: MessageViewModel by viewModels()
        model.getMessage(args.event.refId).observe(this, {
            setMessage(view, it)
        })
    }

    private fun addVendorVM(view: View, vendorID: String) {
        val model: MessageViewModel by viewModels()
        model.getVendor().observe(this, {
            setAuthorVM(view, it)
        })
    }
}