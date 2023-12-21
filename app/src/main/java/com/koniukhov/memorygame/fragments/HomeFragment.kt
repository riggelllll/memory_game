package com.koniukhov.memorygame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.koniukhov.memorygame.R
import com.koniukhov.memorygame.databinding.FragmentHomeBinding
import com.koniukhov.memorygame.viewmodels.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() =  _binding!!

    private val gameViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.card32.setOnClickListener{
            setRowCol(3, 2)
            navigateToGameFragment()
        }

        binding.card43.setOnClickListener{
            setRowCol(4, 3)
            navigateToGameFragment()
        }

        binding.card54.setOnClickListener{
            setRowCol(5, 4)
            navigateToGameFragment()
        }

        binding.card87.setOnClickListener{
            setRowCol(8, 7)
            navigateToGameFragment()
        }

        binding.card98.setOnClickListener{
            setRowCol(9, 8)
            navigateToGameFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToGameFragment(){
        findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
    }

    private fun setRowCol(numRow: Int, numCol: Int){
        gameViewModel.setNumRow(numRow)
        gameViewModel.setNumCol(numCol)
    }
}