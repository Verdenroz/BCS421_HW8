package edu.farmingdale.alrajab.bcs421.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.farmingdale.alrajab.bcs421.R

class DBAdapter(private val nameReading: ArrayList<User>) :
    RecyclerView.Adapter<DBAdapter.ViewHolder>() {
    //Implementation of the ViewHolder Class
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fnameTextView: TextView
        val lnameTextView: TextView
        val delete: Button
        val dbHelper: NameRepository

        init {
            fnameTextView = view.findViewById(R.id.firstname)
            lnameTextView = view.findViewById(R.id.lastname)
            delete = view.findViewById(R.id.delete_btn)
            dbHelper = NameRepository.getInstance(view.context)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create new view with UI of weather item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //set element from weather data [using position] to TextView
        holder.fnameTextView.text = nameReading[position].firstName
        holder.lnameTextView.text = nameReading[position].lastName
        //deletes element at position
        holder.delete.setOnClickListener {
            val deletedUser = nameReading.get(position)
            nameReading.removeAt(position)
            holder.dbHelper.deleteUser(deletedUser)
            this.notifyDataSetChanged()
        }

    }
    override fun getItemCount()= nameReading.size

}