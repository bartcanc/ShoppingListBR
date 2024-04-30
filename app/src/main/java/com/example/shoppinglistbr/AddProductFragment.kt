package com.example.shoppinglistbr

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shoppinglistbr.data.Product
import com.example.shoppinglistbr.data.Products
import com.example.shoppinglistbr.databinding.FragmentAddProductBinding

class AddProductFragment : Fragment() {
    val args: AddProductFragmentArgs by navArgs()
    private lateinit var binding: FragmentAddProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Initialize the binding variable
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
// Set the title and description EditText fields with the task to edit
// (only if it's not null)
        binding.titleInput.setText(args.productToEdit?.title)
        binding.typeInput.setText(args.productToEdit?.category)
        binding.countInput.setText(args.productToEdit?.count.toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener { saveProduct() }
    }


    private fun saveProduct() {
        var title: String = binding.titleInput.text.toString()
        var category: String = binding.typeInput.text.toString()
        val count: String = binding.countInput.text.toString()

        if(title.isEmpty())
            title = getString(R.string.default_task_title) + "${Products.list.size + 1}"
        if(category.isEmpty())
            category = getString(R.string.no_category_msg)

        val productItem = Product(
            {title + category}.hashCode().toString(),
            title,
            category,
            Integer.parseInt(count)
        )
// Add the new task to the list of tasks
        if(!args.edit){
            Products.addProduct(productItem)
        }
        else{
            Products.updateProduct(oldProduct = args.productToEdit, newProduct = productItem)
        }

// Hide the software keyboard with InputMethodManager
        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken,0)

        findNavController().popBackStack(R.id.productListFragment, false)
    }
}