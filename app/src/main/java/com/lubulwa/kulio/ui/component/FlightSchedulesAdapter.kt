package com.lubulwa.kulio.ui.component

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lubulwa.kulio.R
import com.lubulwa.kulio.model.Flight
import kotlinx.android.synthetic.main.flight_list_item.view.*

class FlightSchedulesAdapter (context: Context?, values: ArrayList<Flight>, itemListener: FlightSchedulesAdapter.ItemListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var mValues: ArrayList<Flight> = values
    private var mListener: ItemListener = itemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderFlight(inflater.inflate(R.layout.flight_list_item, parent, false))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewHolderFlight = viewHolder as ViewHolderFlight
        configureFlightVH(viewHolderFlight, position)
    }

    private fun configureFlightVH(viewHolderFlight: ViewHolderFlight, position: Int) {
        val flight = mValues.get(position)
        viewHolderFlight.setData(flight)
    }

    inner class ViewHolderFlight(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var tvOriginAirport: TextView
        var tvDestinationAirport: TextView
        var tvOriginAirportTime: TextView
        var tvDestinationAirportTime: TextView

        internal lateinit var item: Flight

        init {
            v.flight_list_item.setOnClickListener(this)

            tvOriginAirport = v.tv_origin_airport
            tvDestinationAirport = v.tv_destination_airport
            tvOriginAirportTime = v.tv_origin_airport_time
            tvDestinationAirportTime = v.tv_destination_airport_time
        }

        fun setData(item: Flight) {
            this.item = item

            tvOriginAirport.text = item.departure.airportCode
            tvDestinationAirport.text = item.arrival.airportCode
            tvOriginAirportTime.text = item.departure.scheduledTimeLocal.dateTime
            tvOriginAirportTime.text = item.arrival.scheduledTimeLocal.dateTime
        }


        override fun onClick(view: View) {
            mListener.onFlightItemClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    interface ItemListener {
        fun onFlightItemClicked(flightItem: Flight)
    }

}