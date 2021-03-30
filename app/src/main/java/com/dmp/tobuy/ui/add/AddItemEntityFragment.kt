package com.dmp.tobuy.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.dmp.tobuy.R
import com.dmp.tobuy.database.entity.ItemEntity
import com.dmp.tobuy.databinding.FragmentAddItemEntityBinding
import com.dmp.tobuy.ui.BaseFragment
import java.util.*

class AddItemEntityFragment : BaseFragment() {

    private var _binding: FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: AddItemEntityFragmentArgs by navArgs()
    private val selectedItemEntity: ItemEntity? by lazy {
        sharedViewModel.itemEntitiesLiveData.value?.find {
            it.id == safeArgs.selectedItemEntityId
        }
    }
    private var isInEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemEntityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            saveItemEntityToDatabase()
        }

        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { complete ->
            if (complete) {

                if (isInEditMode) {
                    navigateUp()
                    return@observe
                }

                Toast.makeText(requireActivity(), "Item saved!", Toast.LENGTH_SHORT).show()
                binding.titleEditText.text = null
                binding.titleEditText.requestFocus()
                mainActivity.showKeyboard()

                binding.descriptionEditText.text = null
                binding.radioGroup.check(R.id.radioButtonLow)
            }
        }

        // Show keyboard and default select our Title EditText
        mainActivity.showKeyboard()
        binding.titleEditText.requestFocus()

        // Setup screen if we are in edit mode
        selectedItemEntity?.let { itemEntity ->
            isInEditMode = true

            binding.titleEditText.setText(itemEntity.title)
            binding.titleEditText.setSelection(itemEntity.title.length)
            binding.descriptionEditText.setText(itemEntity.description)
            when (itemEntity.priority) {
                1 -> binding.radioGroup.check(R.id.radioButtonLow)
                2 -> binding.radioGroup.check(R.id.radioButtonMedium)
                else -> binding.radioGroup.check(R.id.radioButtonHigh)
            }

            binding.saveButton.text = "Update"
            mainActivity.supportActionBar?.title = "Update item"
        }
    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.transactionCompleteLiveData.postValue(false)
    }

    private fun saveItemEntityToDatabase() {
        val itemTitle = binding.titleEditText.text.toString().trim()
        if (itemTitle.isEmpty()) {
            binding.titleTextField.error = "* Required field"
            return
        }

        binding.titleTextField.error = null

        var itemDescription: String? = binding.descriptionEditText.text.toString().trim()
        if (itemDescription?.isEmpty() == true) {
            itemDescription = null
        }
        val itemPriority = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }

        if (isInEditMode) {
            val itemEntity = selectedItemEntity!!.copy(
                title = itemTitle,
                description = itemDescription,
                priority = itemPriority
            )

            sharedViewModel.updateItem(itemEntity)
            return
        }

        val itemEntity = ItemEntity(
            id = UUID.randomUUID().toString(),
            title = itemTitle,
            description = itemDescription,
            priority = itemPriority,
            createdAt = System.currentTimeMillis(),
            categoryId = "" // todo update this later when we have categories in the app!
        )

        sharedViewModel.insertItem(itemEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}