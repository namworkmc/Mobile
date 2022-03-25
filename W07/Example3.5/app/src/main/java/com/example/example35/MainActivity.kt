package com.example.example35

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialog = FireMissilesDialogFragment()
        dialog.show(supportFragmentManager, "ndnam")
    }

    class FireMissilesDialogFragment : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val selectedItems = ArrayList<Int>() // Where we track the selected items
                //the items to be selected by default (null for none)
                val initSelectedItems = booleanArrayOf(true, false, false)
                val builder = AlertDialog.Builder(it)
                builder.setTitle("Dialog with checkboxes")
                    // Specify the list array, and the listener receiving the callbacks...
                    .setMultiChoiceItems(
                        arrayOf("Milk", "Candy", "Chocolate"), initSelectedItems
                    ) {

                            _, which, isChecked ->

                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(which)
                        } else if (selectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(Integer.valueOf(which))
                        }
                    }
                    // Set the action buttons
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        Log.d(
                            "nlhdung",
                            "User clicked OK, so save the selectedItems..."
                        )
                    }
                    .setNegativeButton(
                        "Cancel"
                    ) { _, _ ->
                        Log.d("nlhdung", "User clicked Cancel...")
                    }
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }
}