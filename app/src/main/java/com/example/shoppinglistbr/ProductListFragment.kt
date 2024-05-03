package com.example.shoppinglistbr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistbr.data.Product
import com.example.shoppinglistbr.data.Products
import com.example.shoppinglistbr.databinding.ActivityMainBinding
import com.example.shoppinglistbr.databinding.ProductListBinding
import com.google.android.material.snackbar.Snackbar

class ProductListFragment : Fragment(), ShoppingListListener {
    // connect the fragment_task_list.xml with TaskListFragment class
    private lateinit var binding: ProductListBinding
    private val listaFiltr: MutableList<Product> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductListBinding.inflate(inflater, container, false)
        listaFiltr.clear()
        for(i in Products.list){
            if(i.category == binding.categorySelect.selectedItem.toString()){
                listaFiltr.add(i)
            }
        }

        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = if(binding.categorySelect.selectedItem.toString() != "Wszystkie"){
                MyProductRecyclerViewAdapter(
                    listaFiltr,
                    this@ProductListFragment
                )
            } else{
                MyProductRecyclerViewAdapter(
                    Products.list,
                    this@ProductListFragment
                )
            }
             // adapter is responsible for displaying the data
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// Set the action for the FAB
        binding.addButton.setOnClickListener {
// Navigate to the AddTaskFragment with action id
            findNavController().navigate(R.id.action_productListFragment_to_addProductFragment)
        }
        binding.refreshButton.setOnClickListener {
            listaFiltr.clear()
            for(i in Products.list){
                if(i.category == binding.categorySelect.selectedItem.toString()){
                    listaFiltr.add(i)
                }
            }

            with(binding.list) {
                layoutManager = LinearLayoutManager(context)
                adapter = if(binding.categorySelect.selectedItem.toString() != "Wszystkie"){
                    MyProductRecyclerViewAdapter(
                        listaFiltr,
                        this@ProductListFragment
                    )
                } else{
                    MyProductRecyclerViewAdapter(
                        Products.list,
                        this@ProductListFragment
                    )
                }
            }
        }
    }

    override fun onProductClick(productPosition: Int) {
// create an action to navigate to the DisplayTaskFragment
//with the selected task at taskPosition
        val actionProductListFragmentToDisplayProductFragment =
            ProductListFragmentDirections.actionProductListFragmentToDisplayProductFragment(
                Products.list[productPosition])
// use the navigate method to perform the navigation action created above
// we do not use the id of the action in this case
        findNavController().navigate(actionProductListFragmentToDisplayProductFragment)
    }

    override fun onProductLongClick(productPosition: Int) {
        showDeleteDialog(productPosition)
    }

    // show a dialog window to delete a task at given position
    private fun showDeleteDialog(productPosition: Int) {
// get the task to delete from the list
        val productToDelete = Products.list[productPosition]
// Define a dialog window with the AlertDialog.Builder class
        val dialogBuilder = android.app.AlertDialog.Builder(requireContext())
// set the title for the dialog
        dialogBuilder.setTitle("Delete Product?")
// set the message for the dialog
        dialogBuilder.setMessage(productToDelete.title)
// set the positive button for the dialog
            .setPositiveButton("Confirm") { _, _ ->
                deleteDialogPositiveClick(productPosition)
            }
// set the negative button for the dialog
            .setNegativeButton("Cancel") { dialog, _ ->
// if the user cancels the deletion, dismiss the dialog
                dialog.dismiss()
                deleteDialogNegativeClick(productPosition)
            }
// create the dialog
        val alert = dialogBuilder.create()
// show the dialog
        alert.show()
    }

    private fun deleteDialogPositiveClick(productPosition: Int) {
// remove the task from the list
            for(i in listaFiltr){
                if(i.id == Products.list[productPosition].id){
                    listaFiltr.remove(i)
                    break
                }
            }
        Products.list.removeAt(productPosition)

// show a snack-bar message to confirm the deletion
        Snackbar.make(binding.root, "Product deleted", Snackbar.LENGTH_SHORT).show()
// notify the adapter that the data has changed
        binding.list.adapter?.notifyItemRemoved(productPosition)
        listaFiltr.clear()
        for(i in Products.list){
            if(i.category == binding.categorySelect.selectedItem.toString()){
                listaFiltr.add(i)
            }
        }
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = if(binding.categorySelect.selectedItem.toString() != "Wszystkie"){
                MyProductRecyclerViewAdapter(
                    listaFiltr,
                    this@ProductListFragment
                )
            } else{
                MyProductRecyclerViewAdapter(
                    Products.list,
                    this@ProductListFragment
                )
            }
            // adapter is responsible for displaying the data
        }
    }
    private fun deleteDialogNegativeClick(productPosition: Int) {
// show a snack-bar message to confirm the cancellation.
// The action specified for the snack-bar allows to add "REDO" button to
// show the dialog again
        Snackbar.make(binding.root, "Deletion cancelled", Snackbar.LENGTH_SHORT)
            .setAction("Redo") {
// if the user wants to redo the deletion, call the showDeleteDialog method again
                showDeleteDialog(productPosition)
            }.show()
    }
}