package com.dmp.tobuy.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dmp.tobuy.arch.ToBuyViewModel
import com.dmp.tobuy.database.AppDatabase

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    protected val appDatabase: AppDatabase
        get() = AppDatabase.getDatabase(requireActivity())

    protected val sharedViewModel: ToBuyViewModel by activityViewModels()

    // region Navigation helper methods
    protected fun navigateUp() {
        mainActivity.navController.navigateUp()
    }

    protected fun navigateViaNavGraph(actionId: Int) {
        mainActivity.navController.navigate(actionId)
    }
    // endregion Navigation helper methods
}