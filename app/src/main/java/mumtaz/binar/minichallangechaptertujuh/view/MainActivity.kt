package mumtaz.binar.minichallangechaptertujuh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.rv_country
import kotlinx.android.synthetic.main.activity_main.switch_rv
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mumtaz.binar.minichallangechaptertujuh.R
import mumtaz.binar.minichallangechaptertujuh.adapter.AdapterCountry
import mumtaz.binar.minichallangechaptertujuh.adapter.AdapterGridCountry
import mumtaz.binar.minichallangechaptertujuh.datastore.DataStoreManager
import mumtaz.binar.minichallangechaptertujuh.viewmodel.ViewModelCountry

class MainActivity : AppCompatActivity() {

    private val viewModel : ViewModelCountry by viewModels()
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataStoreManager = DataStoreManager(this)

        fetchData()

        btn_fav.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
            finish()
        }
    }

    fun fetchData() {
        dataStoreManager.boolean.asLiveData().observe(this){
            switch_rv.isChecked = it
            viewModel.getCountry()
            if (it){
                viewModel.liveDataCountry.observe(this){ data ->
                    rv_country.layoutManager = LinearLayoutManager(this)
                    rv_country.adapter = AdapterCountry(data!!)
                }
            }else{
                viewModel.liveDataCountry.observe(this){ data ->
                    rv_country.layoutManager = GridLayoutManager(this, 2)
                    rv_country.adapter = AdapterGridCountry(data!!)
                }
            }

            switch_rv.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    GlobalScope.launch {
                        dataStoreManager.saveData(true)
                    }
                } else {
                    GlobalScope.launch {
                        dataStoreManager.saveData(false)
                    }
                }
            }
        }
    }
}