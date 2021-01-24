package com.onehitwonders.startpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.show_in.*
import kotlinx.coroutines.launch

class ShowOut : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_out)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        var latitude: Double
        var longitude: Double
        mMap = googleMap
        val dao = ShoppingDatabase.getInstance(this).shoppingDao
        lifecycleScope.launch {
            var ultimoOut = dao.searchOut()

            latitude = ultimoOut.first().latitude
            longitude = ultimoOut.first().longitude

            // Add a marker in Sydney and move the camera

            val sydney = LatLng(latitude, longitude)
            mMap.addMarker(MarkerOptions().position(sydney).title("Your last parking"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }

    }
}