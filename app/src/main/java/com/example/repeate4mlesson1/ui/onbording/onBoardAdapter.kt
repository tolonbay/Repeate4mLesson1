package com.example.repeate4mlesson1.ui.onbording

import android.icu.text.CaseMap
import androidx.constraintlayout.helper.widget.Carousel
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.repeate4mlesson1.R
import java.io.Serializable

class OnBoardAdapter(fm:FragmentManager,lifecycle: Lifecycle,
private val boardModels: List<BoardModel>): FragmentStateAdapter(fm,lifecycle) {


   override fun getItemCount()= boardModels.size


    override fun createFragment(position: Int): Fragment {
        val islast = boardModels.lastIndex ==position

        val bundle = bundleOf(
            BoardModel.ARG_KEY to boardModels[position],
            OnBoardPageFragment.IS_LAST_ARG to islast,
        )


        val fragment = OnBoardPageFragment()
        fragment.arguments = bundle
        return fragment
    }

}
data class BoardModel(
    val imageResId: Int,
    val title:String,
    val description:String
): java.io.Serializable{
    companion object{
        const val ARG_KEY="board"
    }
}