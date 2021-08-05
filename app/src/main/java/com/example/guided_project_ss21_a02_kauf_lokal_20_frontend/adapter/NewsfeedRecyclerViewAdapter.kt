package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.marginBottom
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.R
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments.CouponListFragmentDirections
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.fragments.NewsfeedFragmentDirections
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Coupon
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.Event
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.model.EventTypes
import com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.service.RequestSingleton
import com.google.gson.Gson
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


/**
 * [RecyclerView.Adapter] that can display a [Event].
 */

// TODO: fix the warning later
@SuppressLint("SetTextI18n")
class NewsfeedRecyclerViewAdapter(
    private var events: List<Event>
) : RecyclerView.Adapter<NewsfeedRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_newsfeed_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        var textToSend = ""
        val timePassed = getTimePassed(event.created)

        //TODO: set all eventType
        //TODO: write better textToSend
        when (event.eventTypes) {
            EventTypes.COUPON -> {
                holder.eventType.text = "Coupon"
                holder.eventMessage.text = "A new Coupon was posted!"
                holder.eventIv.setImageResource(R.drawable.ic_tag)
                holder.eventTime.text = timePassed
                textToSend = "Hey, ein neuer Coupon wurde gesichtet: ${holder.eventMessage.text}"
            }
            EventTypes.MESSAGE -> {
                holder.eventType.text = "Message"
                holder.eventMessage.text = "A new Message was posted!"
                holder.eventIv.setImageResource(R.drawable.ic_baseline_article_24)
                holder.eventTime.text = timePassed
                textToSend = "Hey, schau mal was es bei KaufLokal neues gibt: ${holder.eventMessage.text}"
            }
            EventTypes.POLL -> {
                holder.eventType.text = "Poll"
                holder.eventMessage.text = "A new Poll was posted!"
                holder.eventIv.setImageResource(R.drawable.ic_baseline_poll_24)
                holder.eventTime.text = timePassed
                textToSend = "Hey, guck mal, bei KaufLokal gibt es wieder eine neue Umfrage: ${holder.eventMessage.text}"
            }
            EventTypes.UPDATE -> {
                holder.eventType.text = "Update"
                holder.eventMessage.text = "A new Update was posted!"
                holder.eventIv.setImageResource(R.drawable.ic_baseline_update_24)
                holder.eventTime.text = timePassed
                holder.itemView.isClickable=false

                // Visual Changes due to type Update
                holder.eventShareIv.visibility=GONE
                holder.eventIv.layoutParams.height=100
                holder.eventIv.layoutParams.width=100
                holder.eventMessage.textSize=10F
                //holder.eventCard.setCardBackgroundColor(Color.GRAY)
                holder.eventCard.setCardBackgroundColor(ContextCompat.getColor(holder.eventCard.context,R.color.card_color_unclickable))
                holder.eventCard.elevation=0F
            }
        }
        // Share Click Listener
        holder.eventShareIv.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textToSend)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            holder.eventIv.context.startActivity(shareIntent)
        }
    }

    override fun getItemCount(): Int = events.size



    // Automatically displays data changes
    fun setValues(events: List<Event>) {
        // sorts events by Date create
        this.events = events.sortedByDescending { it.created }
        this.notifyDataSetChanged()
    }

    fun getTimePassed(dateTime: Date): String {

        val dateTimeNow = LocalDateTime.now()
        //val dateTimeParsed = LocalDateTime.parse(dateTime)
        val dateTimeConv = LocalDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault())
        val diff = Duration.between(dateTimeConv, dateTimeNow).seconds



        return when {
             diff in 60..3599 -> (diff / 60).toString() + " min"
             diff in 3600..86399 -> (diff / 3600).toString() + " h"
             diff in 86400..604799 -> (diff / 86400).toString() + " d"
             diff in 604800..2629799 -> (diff / 604800).toString() + " w"
             diff in 2629800.. 31535999-> (diff / 2629800).toString() + " m"
             diff >= 31536000 -> (diff / 31536000).toString() + " y"

             else -> "$diff s"
         }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventShareIv: ImageView = view.findViewById(R.id.share_iv)
        var eventMessage: TextView = view.findViewById(R.id.event_message)
        var eventType: TextView = view.findViewById(R.id.event_type)
        var eventIv :ImageView = view.findViewById(R.id.event_iv)
        var eventTime : TextView = view.findViewById(R.id.event_time)
        var eventCard: CardView = view.findViewById(R.id.event_card)

        val paramsMessage = eventMessage.layoutParams as ConstraintLayout.LayoutParams
        val paramsType = eventType.layoutParams as ConstraintLayout.LayoutParams


        init {

            itemView.setOnClickListener {
                if (it.isClickable) {
                    var position: Int = bindingAdapterPosition
                    val event = events[position]
                    val context = itemView.context
                    val navController: NavController = view.findNavController()

                    //var nextFrag: Fragment? = null

                    //Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()


                    when (event.eventTypes) {
                        EventTypes.MESSAGE -> {

                            // Uses Safe Args with type safety
                            val action = NewsfeedFragmentDirections.actionNewsToNewsfeedMessage(event)
                            navController.navigate(action)
                        }
                        EventTypes.COUPON -> {

                            val gson = Gson()

                            val url = "http://10.0.2.2:8080/coupon/"

                            val request = JsonObjectRequest(
                                Request.Method.GET, url+event.refId, null,
                                { response ->
                                    val coupon = gson.fromJson(response.toString(), Coupon::class.java)
                                    val action = NewsfeedFragmentDirections.actionNewsToCouponDetailFragment(coupon)
                                    it.findNavController().navigate(action)

                                },
                                { error ->
                                    Log.e("Response", error.message ?: "Keine Fehlermeldung vorhanden")
                                }
                            )
                            RequestSingleton.getInstance(context).addToRequestQueue(request)
                        }
                        EventTypes.POLL -> {
                            val action = NewsfeedFragmentDirections.actionNewsToPoll(event)
                            navController.navigate(action)
                        }
                        //EventTypes.UPDATE -> TODO()
                    }

                }
            }
        }


    }

}