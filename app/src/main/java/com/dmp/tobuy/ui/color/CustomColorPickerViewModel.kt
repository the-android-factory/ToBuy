package com.dmp.tobuy.ui.color

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmp.tobuy.SharedPrefUtil
import java.util.*
import kotlin.math.roundToInt

class CustomColorPickerViewModel : ViewModel() {

    data class ViewState(
        val red: Int = 0,
        val green: Int = 0,
        val blue: Int = 0,
        private val priorityName: String = ""
    ) {
        fun getFormattedTitle(): String {
            return "$priorityName ($red, $green, $blue)"
        }
    }

    private lateinit var priorityName: String

    private val _viewStateLiveData = MutableLiveData<ViewState>()
    val viewStateLiveData: LiveData<ViewState> = _viewStateLiveData

    fun setPriorityName(priorityName: String, colorCallback: (Int, Int, Int) -> Unit) {
        this.priorityName = priorityName

        val colorInt = when (priorityName.toLowerCase(Locale.US)) {
            "low" -> SharedPrefUtil.getLowPriorityColor()
            "medium" -> SharedPrefUtil.getMediumPriorityColor()
            "high" -> SharedPrefUtil.getHighPriorityColor()
            else -> 0
        }

        val color = Color.valueOf(colorInt)
        val redValue = (color.red() * 255).roundToInt() 
        val greenValue = (color.green() * 255).roundToInt() 
        val blueValue = (color.blue() * 255).roundToInt()
        
        colorCallback(redValue, greenValue, blueValue)
        
        _viewStateLiveData.postValue(ViewState(
            red = redValue,
            green = greenValue,
            blue = blueValue,
            priorityName = priorityName
        ))
    }

    fun onRedChange(red: Int) {
        val viewState = _viewStateLiveData.value ?: ViewState()
        _viewStateLiveData.postValue(viewState.copy(red = red))
    }

    fun onGreenChange(green: Int) {
        val viewState = _viewStateLiveData.value ?: ViewState()
        _viewStateLiveData.postValue(viewState.copy(green = green))
    }

    fun onBlueChange(blue: Int) {
        val viewState = _viewStateLiveData.value ?: ViewState()
        _viewStateLiveData.postValue(viewState.copy(blue = blue))
    }
}