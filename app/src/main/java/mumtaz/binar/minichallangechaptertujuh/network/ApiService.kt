package mumtaz.binar.minichallangechaptertujuh.network

import mumtaz.binar.minichallangechaptertujuh.model.GetAllCountryResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("all")
    fun getAllData(
    ) : Call<ArrayList<GetAllCountryResponseItem>>
}