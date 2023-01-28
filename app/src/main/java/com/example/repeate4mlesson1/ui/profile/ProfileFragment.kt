package com.example.repeate4mlesson1.ui.profile

import android.app.Activity.MODE_PRIVATE
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.repeate4mlesson1.R
import com.example.repeate4mlesson1.databinding.FragmentProfileBinding
import com.example.repeate4mlesson1.utilits.Preferences

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        binding?.image?.setImageURI(uri)
    }

    private var uri: Uri? = null

    companion object {
        const val MEMORY_IMAGE = "image/*"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        binding.image.setOnClickListener {
            imagePicker.launch("image/*")

        }
    }

    private fun initViews() {
        val preferences = Preferences(requireContext())

        //Если данные из SharedPreferences не null тогда их применим
        if (preferences.getPrefTitle() != null) binding.etText.setText(
            preferences.getPrefTitle()
        )
        if (preferences.getPrefImage() != "") {
            binding.image.setImageURI(Uri.parse(preferences.getPrefImage()))
        }
    }



        override fun onDestroyView() {
            super.onDestroyView()
            val preferences = Preferences(requireContext())
            preferences.setPrefTitle(binding.etText.text.toString())
            preferences.setPrefImage(uri.toString())


        }

        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
    }
