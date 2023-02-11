package com.example.repeate4mlesson1.ui.onbording

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.repeate4mlesson1.R
import com.example.repeate4mlesson1.databinding.FragmentOnboardBinding
import com.example.repeate4mlesson1.utilits.Preferences

class OnBoardFragment : Fragment(), OnBoardPageFragment.OnBoardListener {

    private var binding:FragmentOnboardBinding? = null
    private var boardAdapter: OnBoardAdapter? = null
    private val boardModels = listOf(
        BoardModel(
            imageResId = R.drawable.ic_onboarding1,
            title = "To-do list!",
            description = "Here you can write down something important or make a schedule for tomorrow:)"
        ),
        BoardModel(
            imageResId = R.drawable.ic_onboarding2,
            title = "Share your crazy idea ^_^",
            description = "You can easily share with your report, list or schedule and it's convenient"
        ),
        BoardModel(
            imageResId = R.drawable.ic_onboarding3,
            title = "Flexibility",
            description = "Your note with you at home, at work, even at the resort",
        )

    )



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardBinding.inflate(
            LayoutInflater.from(context),container,false
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
    private fun initViews(){


        boardAdapter = OnBoardAdapter(
            childFragmentManager, lifecycle,boardModels)

        binding?.viewpager?.adapter = boardAdapter
    }

    override fun onSkipClicked() {
        binding?.viewpager?.setCurrentItem(boardModels.lastIndex,true)
    }

    override fun onNextClicked() {
        binding?.apply {
         viewpager.setCurrentItem(++viewpager.currentItem,true)
        }
    }


    override fun onStartClicked() {
         findNavController().navigate(R.id.navigation_home)
         Preferences(requireContext()).apply {
             setHaveSeenOnBoarding()
         }
        }

    override fun getViewPager(): ViewPager2 {
        return binding?.viewpager!!

    }
    }



