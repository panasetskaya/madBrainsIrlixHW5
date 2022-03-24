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
import android.provider.ContactsContract
import androidx.core.database.getStringOrNull
import androidx.lifecycle.MutableLiveData

private const val TAG = "tag"

class ContactsFragment : Fragment() {
    private var param: String? = null
    private lateinit var adapter: MyRecyclerViewAdapter
    private var contactsList = mutableListOf<Contact>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonGetContacts: Button
    private val contactsLiveData = MutableLiveData<List<Contact>>()

    val singlePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        when {
            it -> {
                parseContacts()
            }
            else -> {
                showSorryDialog()
            }
        }
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
        adapter = MyRecyclerViewAdapter()
        recyclerView.adapter = adapter
        contactsLiveData.observe(requireActivity()) {
            adapter.list = it.toMutableList()
            adapter.notifyDataSetChanged()
        }

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

    private fun showSorryDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Сорри")
        alertDialog.setMessage("Нет доступа к контактам")
        alertDialog.setPositiveButton("Ок") { dialog, i ->
            dialog.cancel()
        }
        alertDialog.show()
    }

    private fun showRationaleDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Дай доступ")
        alertDialog.setMessage("Ну очень нужно")
        alertDialog.setPositiveButton("Дать") { dialog, i ->
            dialog.cancel()
            singlePermission.launch(Manifest.permission.READ_CONTACTS)
        }
        alertDialog.setNegativeButton("Не давать") {dialog, i ->
            dialog.cancel()
            showSorryDialog()
        }
        alertDialog.show()
    }

    private fun parseContacts() {
        val resolver = requireContext().contentResolver
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        cursor?.let {
            val nameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            if (cursor.count>0) {
                while (cursor.moveToNext()) {
                    val name = cursor.getStringOrNull(nameColumnIndex)
                    contactsList.add(Contact(name))
                }
                updateList()
                buttonGetContacts.visibility = View.GONE
            }
        }
    }

    private fun updateList() {
        contactsLiveData.postValue(contactsList.toList())
        adapter.notifyDataSetChanged()
    }
}