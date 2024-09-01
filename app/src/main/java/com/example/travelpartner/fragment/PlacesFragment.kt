/*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.travelpartner.databinding.FragmentPlacesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlacesFragment : Fragment() {
    private lateinit var binding: FragmentPlacesBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().reference

        setupSpinners()

        binding.btnSearch.setOnClickListener {
           // fetchAddress()
        }

        return binding.root
    }

    private fun setupSpinners() {
        // Fetch and set up divisions
        database.child("aLocations").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val divisions = snapshot.children.mapNotNull {
                    it.child("divisionName").getValue(String::class.java)
                }
                setupDivisionSpinner(divisions)
            }

            override fun onCancelled(error: DatabaseError) {
                showError("Error fetching divisions")
            }
        })
    }

    private fun setupDivisionSpinner(divisions: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, divisions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.divisionSpinner.adapter = adapter

        binding.divisionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDivision = parent?.getItemAtPosition(position) as String
                fetchDistricts(selectedDivision)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where no item is selected, if necessary
            }
        }
    }

    private fun fetchDistricts(divisionName: String) {
        database.child("aLocations").orderByChild("divisionName").equalTo(divisionName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val districts = snapshot.children.flatMap { divisionSnapshot ->
                        divisionSnapshot.child("districts").children.mapNotNull {
                            it.child("name").getValue(String::class.java)
                        }
                    }
                    setupDistrictSpinner(districts)
                }

                override fun onCancelled(error: DatabaseError) {
                    showError("Error fetching districts")
                }
            })
    }

    private fun setupDistrictSpinner(districts: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, districts)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.districtSpinner.adapter = adapter

        binding.districtSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDistrict = parent?.getItemAtPosition(position) as String
                fetchUpazilas(selectedDistrict)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where no item is selected, if necessary
            }
        }
    }

    private fun fetchUpazilas(districtName: String) {
        database.child("aLocations").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val upazilas = snapshot.children.flatMap { divisionSnapshot ->
                    divisionSnapshot.child("districts").children.flatMap { districtSnapshot ->
                        if (districtSnapshot.child("name").getValue(String::class.java) == districtName) {
                            districtSnapshot.child("upazilas").children.mapNotNull {
                                it.child("name").getValue(String::class.java)
                            }
                        } else {
                            emptyList()
                        }
                    }
                }
                setupUpazilaSpinner(upazilas)
            }

            override fun onCancelled(error: DatabaseError) {
                showError("Error fetching upazilas")
            }
        })
    }

    private fun setupUpazilaSpinner(upazilas: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, upazilas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.upazilaSpinner.adapter = adapter

        binding.upazilaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedUpazila = parent?.getItemAtPosition(position) as String
               // fetchLocations(selectedUpazila)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where no item is selected, if necessary
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}
*/

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.travelpartner.databinding.FragmentPlacesBinding
import com.google.firebase.database.*

class PlacesFragment : Fragment() {
    private lateinit var binding: FragmentPlacesBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().reference

        setupDivisionSpinner()

        binding.btnSearch.setOnClickListener {
            // Handle search action
        }

        return binding.root
    }

    private fun setupDivisionSpinner() {
        database.child("aLocations").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val divisions = snapshot.children.mapNotNull {
                    it.child("divisionName").getValue(String::class.java)
                }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, divisions)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.divisionSpinner.adapter = adapter

                binding.divisionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val selectedDivision = parent?.getItemAtPosition(position) as String
                        fetchDistricts(selectedDivision)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showError("Error fetching divisions")
            }
        })
    }

    private fun fetchDistricts(divisionName: String) {
        database.child("aLocations").orderByChild("divisionName").equalTo(divisionName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val districts = snapshot.children.flatMap { divisionSnapshot ->
                        divisionSnapshot.child("districts").children.mapNotNull {
                            it.child("name").getValue(String::class.java)
                        }
                    }
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, districts)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.districtSpinner.adapter = adapter

                    binding.districtSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            val selectedDistrict = parent?.getItemAtPosition(position) as String
                            fetchUpazilas(selectedDistrict)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    showError("Error fetching districts")
                }
            })
    }

    private fun fetchUpazilas(districtName: String) {
        database.child("aLocations").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val upazilas = snapshot.children.flatMap { divisionSnapshot ->
                    divisionSnapshot.child("districts").children.flatMap { districtSnapshot ->
                        if (districtSnapshot.child("name").getValue(String::class.java) == districtName) {
                            districtSnapshot.child("upazilas").children.mapNotNull {
                                it.child("name").getValue(String::class.java)
                            }
                        } else {
                            emptyList()
                        }
                    }
                }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, upazilas)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.upazilaSpinner.adapter = adapter

                binding.upazilaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val selectedUpazila = parent?.getItemAtPosition(position) as String
                        // Fetch locations based on selected upazila
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showError("Error fetching upazilas")
            }
        })
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
