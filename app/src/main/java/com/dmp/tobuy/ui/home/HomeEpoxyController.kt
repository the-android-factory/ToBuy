package com.dmp.tobuy.ui.home

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.R
import com.dmp.tobuy.SharedPrefUtil
import com.dmp.tobuy.addHeaderModel
import com.dmp.tobuy.arch.ToBuyViewModel
import com.dmp.tobuy.database.entity.ItemWithCategoryEntity
import com.dmp.tobuy.databinding.ModelEmptyStateBinding
import com.dmp.tobuy.databinding.ModelItemEntityBinding
import com.dmp.tobuy.ui.epoxy.LoadingEpoxyModel
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel

class HomeEpoxyController(
    private val itemEntityInterface: ItemEntityInterface
) : EpoxyController() {

    var viewState: ToBuyViewModel.HomeViewState = ToBuyViewModel.HomeViewState(isLoading = true)
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        if (viewState.isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if (viewState.dataList.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        viewState.dataList.forEach { dataItem ->
            if (dataItem.isHeader) {
                addHeaderModel(dataItem.data as String)
                return@forEach
            }

            val itemWithCategoryEntity = dataItem.data as ItemWithCategoryEntity
            ItemEntityEpoxyModel(itemWithCategoryEntity, itemEntityInterface)
                .id(itemWithCategoryEntity.itemEntity.id)
                .addTo(this)
        }
    }

    data class ItemEntityEpoxyModel(
        val itemEntity: ItemWithCategoryEntity,
        val itemEntityInterface: ItemEntityInterface
    ) : ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {

        override fun ModelItemEntityBinding.bind() {
            titleTextView.text = itemEntity.itemEntity.title

            if (itemEntity.itemEntity.description == null) {
                descriptionTextView.isGone = true
            } else {
                descriptionTextView.isVisible = true
                descriptionTextView.text = itemEntity.itemEntity.description
            }

            priorityTextView.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity.itemEntity)
            }

            val color = when (itemEntity.itemEntity.priority) {
                1 -> SharedPrefUtil.getLowPriorityColor()
                2 -> SharedPrefUtil.getMediumPriorityColor()
                3 -> SharedPrefUtil.getHighPriorityColor()
                else -> R.color.gray_700
            }

            priorityTextView.setBackgroundColor(color)

            root.setStrokeColor(ColorStateList.valueOf(color))
            root.setOnClickListener {
                itemEntityInterface.onItemSelected(itemEntity.itemEntity)
            }

            categoryNameTextView.text = itemEntity.categoryEntity?.name
        }
    }

    class EmptyStateEpoxyModel :
        ViewBindingKotlinModel<ModelEmptyStateBinding>(R.layout.model_empty_state) {
        override fun ModelEmptyStateBinding.bind() {
            // Nothing to do at the moment
        }
    }
}