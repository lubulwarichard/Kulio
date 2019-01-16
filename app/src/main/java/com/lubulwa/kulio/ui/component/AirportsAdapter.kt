package dance.krantz.com.danceapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lubulwa.kulio.R
import com.lubulwa.kulio.model.Airport
import kotlinx.android.synthetic.main.airport_list_item.view.*


class AirportsAdapter(context: Context?, values: ArrayList<Airport>, itemListener: ItemListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mValues: ArrayList<Airport> = values
    private var mListener: ItemListener = itemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderAirport(inflater.inflate(R.layout.airport_list_item, parent, false))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewHolderAirport = viewHolder as ViewHolderAirport
        configureAirportVH(viewHolderAirport, position)
    }

    private fun configureAirportVH(viewHolderAirport: ViewHolderAirport, position: Int) {
        val airPort = mValues[position]
        viewHolderAirport.setData(airPort)
    }

    inner class ViewHolderAirport(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var airportName: TextView
        var airportCity: TextView
        internal lateinit var item: Airport

        init {
            v.airport_list_item.setOnClickListener(this)

            airportName = v.tv_airport_name
            airportCity = v.tv_airport_city
        }

        fun setData(item: Airport) {
            this.item = item

            airportName.text = item.names.name.airportName
            airportCity.text = item.cityCode
        }


        override fun onClick(view: View) {
            mListener.onAirportItemClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    interface ItemListener {
        fun onAirportItemClicked(airportItem: Airport)
    }

}
