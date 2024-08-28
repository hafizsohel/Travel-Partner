import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.travelpartner.databinding.FragmentPlacesBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.util.Locale

class PlacesFragment : Fragment() {
    private lateinit var binding: FragmentPlacesBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.locationSpinner.setOnTouchListener { _, _ ->
            fetchCurrentLocation()
            true
        }

        return binding.root
    }

    private fun fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        // Show progress bar while fetching the location
        binding.locationProgressBar.visibility = View.VISIBLE

        fusedLocationClient.lastLocation.addOnCompleteListener { task: Task<Location> ->
            binding.locationProgressBar.visibility = View.GONE
            if (task.isSuccessful && task.result != null) {
                val location = task.result
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                // Check if the addresses list is not null and has at least one address
                val cityName = addresses?.firstOrNull()?.locality ?: "Unknown Location"

                // Populate the spinner with the current location name
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf(cityName))
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.locationSpinner.adapter = adapter
            } else {
                Toast.makeText(requireContext(), "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}
