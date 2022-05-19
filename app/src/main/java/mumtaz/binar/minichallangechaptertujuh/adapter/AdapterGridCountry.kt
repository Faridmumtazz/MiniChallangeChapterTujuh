package mumtaz.binar.minichallangechaptertujuh.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.free.item_grid_country.view.*
import kotlinx.android.synthetic.main.item_country.view.*
import kotlinx.android.synthetic.main.item_grid_country.view.btn_favorite
import kotlinx.android.synthetic.main.item_grid_country.view.*
import kotlinx.android.synthetic.main.item_grid_country.view.img_countryy
import kotlinx.android.synthetic.main.item_grid_country.view.tv_capitall
import kotlinx.android.synthetic.main.item_grid_country.view.tv_countryy
import kotlinx.android.synthetic.main.item_grid_country.view.tv_cpopulationn
import kotlinx.android.synthetic.main.item_grid_country.view.tv_regionn
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import mumtaz.binar.minichallangechaptertujuh.R
import mumtaz.binar.minichallangechaptertujuh.model.GetAllCountryResponseItem
import mumtaz.binar.minichallangechaptertujuh.roomdb.Favorite
import mumtaz.binar.minichallangechaptertujuh.roomdb.FavoriteDao
import mumtaz.binar.minichallangechaptertujuh.roomdb.FavoriteDatabase
import mumtaz.binar.minichallangechaptertujuh.view.MainActivity

class AdapterGridCountry(private val listCountry: List<GetAllCountryResponseItem>) : RecyclerView.Adapter<AdapterGridCountry.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid_country, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listCountry[position]
        val img = data.flags.png

        holder.itemView.apply {
            tv_countryy.text = data.name
            tv_capitall.text = data.capital
            tv_cpopulationn.text = data.population.toString()
            Glide.with(holder.itemView).load(data.flags.png).into(img_countryy)
            tv_regionn.text = data.region
            val mDb = FavoriteDatabase.getInstance(this.context)
            GlobalScope.async {
                val res = mDb!!.favoriteDao().getFavorite()
                Log.d("iniRes", res.toString())
                (holder.itemView.context as MainActivity).runOnUiThread{
                    if (res.isNullOrEmpty()){

                        btn_favorite.setOnClickListener {
                            GlobalScope.async {
                                val result = mDb.favoriteDao().addFavorite(
                                    Favorite(
                                        null,
                                        data.name,
                                        data.capital,
                                        data.population,
                                        img,
                                        data.region
                                    )
                                )
                                (holder.itemView.context as MainActivity).runOnUiThread {
                                    if (result != 0.toLong()){
                                        btn_favorite.setBackgroundResource(R.drawable.ic_fav)
                                        Toast.makeText(
                                            it.context,
                                            "Added to Favorite",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }else{
                                        Toast.makeText(
                                            it.context,
                                            "Failed to Add",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    }else{
                        for (datas in res) {
                            if (datas.name == data.name) {
                                btn_favorite.setBackgroundResource(R.drawable.ic_fav)
                                btn_favorite.setOnClickListener {
                                    GlobalScope.async {
                                        val results = mDb.favoriteDao().deleteFavorite(datas)
                                        (holder.itemView.context as MainActivity).runOnUiThread {
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
                                    }
                                }
                                break
                            } else {
                                btn_favorite.setBackgroundResource(R.drawable.ic_fav)
                                btn_favorite.setOnClickListener {
                                    GlobalScope.async {
                                        val result = mDb.favoriteDao().addFavorite(
                                            Favorite(
                                                null,
                                                data.name,
                                                data.capital,
                                                data.population,
                                                img,
                                                data.region
                                            )
                                        )
                                        (holder.itemView.context as MainActivity).runOnUiThread {
                                            if (result != 0.toLong()) {
                                                btn_favorite.setBackgroundResource(R.drawable.ic_fav)
                                                Toast.makeText(
                                                    it.context,
                                                    "Added to Favorite",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    it.context,
                                                    "Failed to Add",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listCountry.size
    }
}