package com.koniukhov.memorygame.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.koniukhov.memorygame.R
import com.koniukhov.memorygame.adapters.CardAdapter
import com.koniukhov.memorygame.databinding.FragmentGameBinding
import com.koniukhov.memorygame.util.GridSpacingItemDecoration
import com.koniukhov.memorygame.viewmodels.GameViewModel

private const val MARGIN = 10

class GameFragment: Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() =  _binding!!
    private val gameViewModel: GameViewModel by activityViewModels()
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameViewModel.reset()
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        gameViewModel.initCards()
        setRecyclerSpanCount()
        addRecyclerItemDecoration()
        initAdapter()
        observeCards()

        return binding.root
    }

    private fun initAdapter() {
        adapter = CardAdapter() {
            if (gameViewModel.canClick){
                gameViewModel.selectCard(it, Handler(Looper.getMainLooper()))
            }
        }

        binding.cardRecycler.adapter = adapter
    }

    private fun addRecyclerItemDecoration() {
        binding.cardRecycler.addItemDecoration(
            GridSpacingItemDecoration(
                gameViewModel.numCol,
                MARGIN, true
            )
        )
    }

    private fun setRecyclerSpanCount() {
        val manager = binding.cardRecycler.layoutManager as GridLayoutManager
        manager.spanCount = gameViewModel.numCol
    }

    private fun observeCards() {
        gameViewModel.cards.observe(viewLifecycleOwner) { cardList ->

            adapter.submitList(cardList.map { it.copy() })

            if (gameViewModel.isGameOver()) {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.win_title))
            .setMessage(getString(R.string.win_text))
            .setNegativeButton(getString(R.string.dialog_negative_btn)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.dialog_positive_btn)) { dialog, _ ->
                dialog.dismiss()
                refreshFragment()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refreshFragment(){
        val navController = findNavController()
        val id = navController.currentDestination?.id
        navController.popBackStack(id!!,true)
        navController.navigate(id)
    }
}