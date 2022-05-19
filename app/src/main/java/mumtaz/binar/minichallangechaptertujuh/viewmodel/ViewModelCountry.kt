package mumtaz.binar.minichallangechaptertujuh.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mumtaz.binar.minichallangechaptertujuh.model.GetAllCountryResponseItem
import mumtaz.binar.minichallangechaptertujuh.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelCountry  : ViewModel() {

    val liveDataCountry = MutableLiveData<ArrayList<GetAllCountryResponseItem>?>()

    fun getCountry(){
        ApiClient.instance.getAllData()
            .enqueue(object : Callback<ArrayList<GetAllCountryResponseItem>> {
                override fun onResponse(
                    call: Call<ArrayList<GetAllCountryResponseItem>>,
                    response: Response<ArrayList<GetAllCountryResponseItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataCountry.postValue(response.body()!!)
                    }else{
                        liveDataCountry.postValue(null)
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<GetAllCountryResponseItem>>,
                    t: Throwable
                ) {
                    liveDataCountry.postValue(null)
                }

            })
    }

}