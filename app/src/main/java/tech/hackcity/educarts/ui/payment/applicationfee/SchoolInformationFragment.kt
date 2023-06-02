package tech.hackcity.educarts.ui.payment.applicationfee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FragmentSchoolInformationBinding
import tech.hackcity.educarts.ui.adapters.PhotosAdapter
import tech.hackcity.educarts.ui.payment.PaymentActivity
import tech.hackcity.educarts.uitls.Constants

/**
 *Created by Victor Loveday on 6/1/23
 */
class SchoolInformationFragment : Fragment(R.layout.fragment_school_information) {

    private lateinit var binding: FragmentSchoolInformationBinding

    private val args: SchoolInformationFragmentArgs by navArgs()
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSchoolInformationBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        (activity as PaymentActivity).supportActionBar?.title = args.school.name

        setupMapView(savedInstanceState)
        setupSchoolPhotosRecyclerView()

        binding.applyNowBtn.setOnClickListener {
            val action = SchoolInformationFragmentDirections.actionSchoolInformationFragmentToSchoolFormApplicationLinkFragment(
                args.school.name,
                "https://hackcity.tech/",
                resources.getString(R.string.application_fee),
                args.school.name
            )
            findNavController().navigate(action)
        }
    }

    private fun setupMapView(state: Bundle?) {
        mapView = binding.mapView
        mapView.onCreate(state)
    }

    private fun setupSchoolPhotosRecyclerView() {
        val photosAdapter = PhotosAdapter(requireContext())
        binding.galleryRV.apply {
            adapter = photosAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            photosAdapter.setData(Constants.dummyPhotoList)
        }
    }

    private fun displayLocation(latitude: Double, longitude: Double) {
        val location = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(location))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        mapView.getMapAsync { map ->
            googleMap = map
            displayLocation(40.4173, 82.9071)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}