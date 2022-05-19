package mumtaz.binar.minichallangechaptertujuh.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_country.view.*
import kotlinx.android.synthetic.main.item_grid_country.view.btn_favorite
import kotlinx.android.synthetic.main.item_grid_country.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import mumtaz.binar.minichallangechaptertujuh.R
import mumtaz.binar.minichallangechaptertujuh.roomdb.Favorite
import mumtaz.binar.minichallangechaptertujuh.roomdb.FavoriteDatabase
import mumtaz.binar.minichallangechaptertujuh.view.FavoriteActivity

class FavoriteAdapter(private val listCountry: List<Favorite>): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listCountry[position]

        holder.itemView.apply {
            val txtCapital = "Capital : ${data.capital}"
            val txtPopulation = "Population : ${data.population}"
            val txtRegion = "Region : ${data.region}"
            tv_countryy.text = data.name
            tv_capitall.text = txtCapital
            tv_cpopulationn.text = txtPopulation
            Glide.with(holder.itemView).load(data.flag).into(img_countryy)
            tv_regionn.text = txtRegion
            val mDb = FavoriteDatabase.getInstance(this.context)
            btn_favorite.setBackgroundResource(R.drawable.ic_fav)
            btn_favorite.setOnClickListener {
                GlobalScope.async {
                    val results = mDb!!.favoriteDao().deleteFavorite(data)
                    (holder.itemView.context as FavoriteActivity).runOnUiThread {
                        if (results != 0) {
                            btn_favorite.setBackgroundResource(R.drawable.ic_fav)
                            Toast.makeText(
                                it.context,
                                "Removed from Favorite",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                it.context,
                                "Failed to Remove",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    (holder.itemView.context as FavoriteActivity).fetchData()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listCountry.size
    }
}