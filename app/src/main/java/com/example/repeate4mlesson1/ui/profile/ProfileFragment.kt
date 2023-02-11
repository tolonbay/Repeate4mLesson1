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
import com.bumptech.glide.Glide
import com.example.repeate4mlesson1.R
import com.example.repeate4mlesson1.databinding.FragmentProfileBinding
import com.example.repeate4mlesson1.utilits.Preferences
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null){
            Preferences(requireContext()).savePrefImage(uri)
        }
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
        initListener()
        quitProfile()


    }

    private fun quitProfile() {

        binding.btnExit.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
    }

    private fun initViews() {
        val prefs = Preferences(requireContext())


        prefs.getPrefImage()?.let {
            Glide.with(requireContext())
                .load(it)
                .circleCrop().into(binding.image!!)

        }
        prefs.getPrefTitle()?.let {
            binding.etText?.setText(it)
        }
        }
    private fun initListener(){
        binding.image.setOnClickListener {
            imagePicker.launch("image/*")
        }

        binding.btnSave.setOnClickListener{
            val name = binding?.etText?.text.toString()
            Preferences(requireContext()).setPrefTitle(name)
        }
    }


        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
    }
