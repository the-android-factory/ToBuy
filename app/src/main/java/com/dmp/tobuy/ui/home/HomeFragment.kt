package com.dmp.tobuy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmp.tobuy.R
import com.dmp.tobuy.database.entity.ItemEntity
import com.dmp.tobuy.databinding.FragmentHomeBinding
import com.dmp.tobuy.ui.BaseFragment

class HomeFragment: BaseFragment(), ItemEntityInterface {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            navigateViaNavGraph(R.id.action_homeFragment_to_addItemEntityFragment)
        }

        val controller = HomeEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.itemEntitiesLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            controller.itemEntityList = itemEntityList as ArrayList<ItemEntity>
        }
    }

    override fun onDeleteItemEntity(itemEntity: ItemEntity) {
        sharedViewModel.deleteItem(itemEntity)
    }

    override fun onBumpPriority(itemEntity: ItemEntity) {
        // todo implement me!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}