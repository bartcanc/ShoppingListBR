package com.example.shoppinglistbr.data

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shoppinglistbr.R
import com.example.shoppinglistbr.databinding.FragmentDisplayProductBinding


class DisplayProductFragment : Fragment() {
    val args: DisplayProductFragmentArgs by navArgs()
    private lateinit var binding: FragmentDisplayProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDisplayProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.product
        binding.displayTitle.text = product.title
        binding.displayCategory.text = product.category

        binding.displayQuantity.text = product.count.toString()

        binding.displayEdit.setOnClickListener {
// create an action to navigate to the AddTaskFragment with the displayed task
            val actionEditProduct =
                DisplayProductFragmentDirections.actionDisplayProductFragmentToAddProductFragment()
// set the task to edit and the edit flag to true in the action
            with(actionEditProduct) {
                productToEdit = product
                edit = true
            }
// use the navigate method to perform the navigation action created above
            findNavController().navigate(actionEditProduct)
        }
    }

}