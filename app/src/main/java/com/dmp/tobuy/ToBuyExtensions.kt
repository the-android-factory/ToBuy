package com.dmp.tobuy

import com.airbnb.epoxy.EpoxyController
import com.dmp.tobuy.ui.epoxy.models.HeaderEpoxyModel

fun EpoxyController.addHeaderModel(headerText: String) {
    HeaderEpoxyModel(headerText).id(headerText).addTo(this)
}