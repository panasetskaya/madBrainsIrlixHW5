package com.example.madbrainsirlixhomew5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager

private const val TAG = "tag"

class ContactsFragment : Fragment() {
    private var param: String? = null
    private lateinit var adapter: MyRecyclerViewAdapter
    private var contactsList = mutableListOf<Contact>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonGetContacts: Button

    val singlePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        when {
            it -> {
                parseContacts()
            }
            else -> {
                showRequestPermissionDialog()
            }
        }
    }


    private fun requestContactPermissions() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED -> {
                parseContacts()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                showRationaleDialog()
            }
            else -> {
                singlePermission.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    private fun showRequestPermissionDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Сорри")
        alertDialog.setMessage("Нет доступа к контактам")
        alertDialog.setPositiveButton("Ок") { dialog, i ->
            dialog.cancel()
        }
    }

    private fun showRationaleDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Дай доступ")
        alertDialog.setMessage("Ну очень нужно")
        alertDialog.setPositiveButton("Дать") { dialog, i ->
            dialog.cancel()
            singlePermission.launch(Manifest.permission.READ_CONTACTS)
        }
        alertDialog.setNegativeButton("Не дать") {dialog, i ->
            dialog.cancel()
        }
        alertDialog.show()
    }

    private fun parseContacts() {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewContacts)
        adapter = MyRecyclerViewAdapter(contactsList)
        recyclerView.adapter = adapter
        buttonGetContacts = view.findViewById(R.id.buttonPermission)
        buttonGetContacts.setOnClickListener {
            requestContactPermissions()
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            ContactsFragment().apply {
                arguments = Bundle().apply {
                    putString(TAG, param)
                }
            }
    }
}