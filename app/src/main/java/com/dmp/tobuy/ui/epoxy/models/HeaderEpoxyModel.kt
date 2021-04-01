package com.dmp.tobuy.ui.epoxy.models

import com.dmp.tobuy.R
import com.dmp.tobuy.databinding.ModelHeaderItemBinding
import com.dmp.tobuy.ui.epoxy.ViewBindingKotlinModel

data class HeaderEpoxyModel(
    val headerText: String
): ViewBindingKotlinModel<ModelHeaderItemBinding>(R.layout.model_header_item) {

    override fun ModelHeaderItemBinding.bind() {
        textView.text = headerText
    }
}