package com.dmp.tobuy.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    protected val mainActivity: MainActivity = activity as MainActivity
}