package com.example.madbrainsirlixhomew5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.util.jar.Manifest

private const val TAG = "tag"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param: String? = null

    val singlePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        when {
            it -> {

            }
            !shouldShowRequestPermissionRationale() -> {
                Toast.makeText(activity, "Доступ к контактам запрещен", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(activity, "Доступ к контактам запрещен", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val contacts = registerForActivityResult(ActivityResultContracts.PickContact()) {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
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