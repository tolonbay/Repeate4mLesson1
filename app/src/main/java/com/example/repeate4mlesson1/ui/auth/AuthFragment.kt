package com.example.repeate4mlesson1.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.repeate4mlesson1.data.SessionEntity
import com.example.repeate4mlesson1.databinding.FragmentAuthBinding
import com.example.repeate4mlesson1.utilits.MainApplication
import com.example.repeate4mlesson1.utilits.Preferences
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AuthFragment: Fragment() {

    private var binding: FragmentAuthBinding? = null
    private var storedVerificationId: String? = null
    private var storedToken: PhoneAuthProvider.ForceResendingToken? = null
    private var phoneNumber:String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentAuthBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        initViews()
        initListeners()
    }

//    private fun initViews() {
//
//    }


    private fun initListeners() {
        binding?.btnAuth?.setOnClickListener {
            if (binding?.layoutVerificationCode?.visibility == View.VISIBLE
            ){ checkCode()

            }else {
                phoneNumber = binding?.etPhoneNumber?.text.toString()
                binding?.layoutPhoneNumber?.visibility = View.INVISIBLE
                binding?.layoutVerificationCode?.visibility = View.VISIBLE
                sendCode()
            }
        }
    }

    private fun sendCode() {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber.toString())        // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(/* callbacks = */ object :
                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onCodeAutoRetrievalTimeOut(p0: String) {
                    super.onCodeAutoRetrievalTimeOut(p0)

                    binding?.layoutVerificationCode?.visibility = View.GONE
                    binding?.layoutPhoneNumber?.visibility = View.VISIBLE
                }

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                    // This callback will be invoked in two situations:
                    // 1 - Instant verification. In some cases the phone number can be instantly
                    //     verified without needing to send or enter a verification code.
                    // 2 - Auto-retrieval. On some devices Google Play services can automatically
                    //     detect the incoming verification SMS and perform verification without
                    //     user action.
                    Log.d("Test", "onVerificationCompleted:$credential")
                    signIn()
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Log.w("Test", "onVerificationFailed", e)

                    if (e is FirebaseAuthInvalidCredentialsException) {
                        /* Invalid request */
                    } else if (e is FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded
                    }

                    Toast.makeText(
                        requireContext(),
                        e.localizedMessage ?: e.message,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    Log.d("Test", "onCodeSent:$verificationId")

                    // Save verification ID and resending token so we can use them later
                    storedVerificationId = verificationId
                    storedToken = token

                    Toast.makeText(
                        requireContext(),
                        "Код подтверждение отправлен на телефон",Toast.LENGTH_LONG).show()
                    }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun checkCode(){
         binding?.progressBar?.visibility = View.VISIBLE

        val credential = PhoneAuthProvider.getCredential(
            storedVerificationId!!, binding?.etVerificationCode?.text.toString()
        )
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener{task ->
                if (task.isSuccessful)signIn()
                else Toast.makeText(requireContext(), task.exception.toString(),Toast.LENGTH_LONG).show()

                binding?.progressBar?.visibility = View.GONE
            }
    }

    private fun signIn(){
        if (phoneNumber.isBlank()) throw RuntimeException("Phone number is empty!!")

        val entity = SessionEntity(phoneNumber)

        lifecycleScope.launch {
            MainApplication.appDatabase?.sessionDao?.save(entity)
        }

    }

}