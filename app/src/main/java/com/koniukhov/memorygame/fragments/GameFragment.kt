package com.koniukhov.memorygame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.koniukhov.memorygame.R
import com.koniukhov.memorygame.data.GridSize
import com.koniukhov.memorygame.databinding.FragmentGameBinding
import com.koniukhov.memorygame.viewmodels.GameViewModel

class GameFragment: Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() =  _binding!!
    private val gameViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createGrid()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createGrid(){

        when(gameViewModel.numRow){
            GridSize.GRID_3_2.row -> {
                inflateViews(R.layout.block_3_2)
            }
            GridSize.GRID_4_3.row ->{
                inflateViews(R.layout.block_4_3)
            }
            GridSize.GRID_5_4.row ->{
                inflateViews(R.layout.block_5_4)
            }
            GridSize.GRID_8_7.row ->{
                inflateViews(R.layout.block_8_7)
            }
            GridSize.GRID_9_8.row ->{
                inflateViews(R.layout.block_9_8)
            }
        }
    }

    private fun inflateViews(resource: Int){
        for (r in 1..gameViewModel.numRow){
            val linearLayout = LinearLayout(requireContext())
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            for (c in 1..gameViewModel.numCol){
                val block: RelativeLayout = layoutInflater.inflate(resource, null) as RelativeLayout
                block.findViewById<ImageView>(R.id.image).setImageResource(R.drawable.block1)
                linearLayout.addView(block)
            }
            binding.gameRoot.addView(linearLayout)
        }
    }
}