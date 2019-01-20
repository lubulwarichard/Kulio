package com.lubulwa.kulio.ui.component

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lubulwa.kulio.R
import com.lubulwa.kulio.helpers.local.KulioUtlis
import com.lubulwa.kulio.model.Flight
import com.lubulwa.kulio.model.Schedule
import kotlinx.android.synthetic.main.flight_list_item.view.*
import java.time.Duration

class FlightSchedulesAdapter (context: Context?, values: ArrayList<Schedule>, itemListener: FlightSchedulesAdapter.ItemListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var mValues: ArrayList<Schedule> = values
    private var mListener: ItemListener = itemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderSchedule(inflater.inflate(R.layout.flight_list_item, parent, false))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewHolderSchedule = viewHolder as ViewHolderSchedule
        configureScheduleVH(viewHolderSchedule, position)
    }

    private fun configureScheduleVH(viewHolderFlight: ViewHolderSchedule, position: Int) {
        val schedule = mValues.get(position)
        viewHolderFlight.setData(schedule)
    }

    inner class ViewHolderSchedule(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var tvOriginAirportTime: TextView
        var tvOriginAirport: TextView

        var tvDestinationAirportTime: TextView
        var tvDestinationAirport: TextView

        var tvFlightDuration: TextView
        var tvFlightDate: TextView
        var tvFlightStops: TextView
        var tvFlightStopsLabel: TextView

        internal lateinit var item: Schedule

        init {
            v.flight_list_item.setOnClickListener(this)

            tvOriginAirport = v.tv_origin_airport
            tvOriginAirportTime = v.tv_origin_airport_time

            tvDestinationAirport = v.tv_destination_airport
            tvDestinationAirportTime = v.tv_destination_airport_time

            tvFlightDuration = v.tv_flight_duration
            tvFlightDate = v.tv_flight_date
            tvFlightStops = v.tv_flight_stops
            tvFlightStopsLabel = v.tv_flight_stops_label
        }

        fun setData(item: Schedule) {
            this.item = item

            val lastIndex = item.flights.size - 1

            tvOriginAirport.text = item.flights[0].departure.airportCode
            tvOriginAirportTime.text = KulioUtlis.getTimeFromDateString(item.flights[0].departure.scheduledTimeLocal.dateTime)

            tvDestinationAirport.text = item.flights[lastIndex].arrival.airportCode
            tvDestinationAirportTime.text = KulioUtlis.getTimeFromDateString(item.flights[lastIndex].arrival.scheduledTimeLocal.dateTime)

            tvFlightDuration.text = KulioUtlis.getFlightDurationFromString(item.totalJourney.duration)
            tvFlightStops.text = "$lastIndex"
            tvFlightDate.text = KulioUtlis.getDateFromString(item.flights[0].departure.scheduledTimeLocal.dateTime)

            //Build flight stops
            tvFlightStopsLabel.text = null
            tvFlightStopsLabel.append("stops(")
            for (flight in item.flights) {
                if (flight != item.flights[lastIndex]){
                    tvFlightStopsLabel.append(flight.arrival.airportCode)
                    if (flight != item.flights[lastIndex -1]) {
                        tvFlightStopsLabel.append("-")
                    }
                }
            }
            tvFlightStopsLabel.append(")")
        }


        override fun onClick(view: View) {
            mListener.onScheduleItemClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    interface ItemListener {
        fun onScheduleItemClicked(scheduleItem: Schedule)
    }

}