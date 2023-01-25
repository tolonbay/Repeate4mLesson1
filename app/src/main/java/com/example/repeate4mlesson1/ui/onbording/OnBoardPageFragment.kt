package com.example.repeate4mlesson1.ui.onbording

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.repeate4mlesson1.R
import com.example.repeate4mlesson1.databinding.FragmentOnboardBinding
import com.example.repeate4mlesson1.databinding.FragmentOnboardPageBinding

class OnBoardPageFragment: Fragment() {
    companion object{
        const val IS_LAST_ARG = "is_last"
        const val IS_NEXT_ARG = "is_next"
    }
    private var binding: FragmentOnboardPageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardPageBinding.inflate(
            LayoutInflater.from(context), container, false
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(BoardModel.ARG_KEY, BoardModel::class.java)
        } else{
            arguments?.getSerializable(BoardModel.ARG_KEY) as BoardModel
        }
        val isLast  = arguments?.getBoolean(IS_LAST_ARG)
        val isNext = arguments?.getBoolean(IS_NEXT_ARG)

        if (data != null){
            binding?.ivBoardPicture?.setImageResource(
                data.imageResId
            )
            binding?.tvTitle2?.text = data.title
            binding?.tvDesc2?.text =data.description

            if (isLast == true){
                binding?.apply {
                binding?.btnSkip?.visibility = View.GONE
                binding?.btnNext?.visibility = View.GONE
                binding?.btnStart?.visibility = View.VISIBLE
           }
          }

        }


    }
    private fun initListeners(){
        binding?.btnSkip?.setOnClickListener{
            (parentFragment as OnBoardListener).onSkipClicked()

            }
        binding?.btnNext?.setOnClickListener {
            (parentFragment as OnBoardListener).onNextClicked()
        }
        binding?.btnStart?.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardFragment_to_navigation_home)
        }


        }

    interface OnBoardListener{
        fun onSkipClicked()
        fun onNextClicked()
        fun onStartClicked()
    }
}
