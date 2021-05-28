@file:Suppress("UNCHECKED_CAST")

package kotlinx.android.synthetic.main

import android.util.SparseArray
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.launch

object SyntheticHelper {
    private val viewsCachedWithActivityReference = mutableMapOf<ComponentActivity, SparseArray<View>>()
    private val viewsCachedWithFragmentReference = mutableMapOf<Fragment, SparseArray<View>>()

    fun <T: View> ComponentActivity.findViewByIdInCacheInActivity(id: Int): T {
        var idViewMap = viewsCachedWithActivityReference[this]
        if (idViewMap == null) {
            idViewMap = SparseArray()
            viewsCachedWithActivityReference[this] = idViewMap
            val activity = this
            lifecycle.coroutineScope.launch {
                lifecycle.whenStateAtLeast(Lifecycle.State.DESTROYED) {
                    viewsCachedWithActivityReference.remove(activity)
                }
            }
        }
        var view = idViewMap.get(id)
        return if (view == null) {
            view = findViewById<T>(id)
            idViewMap.put(id, view)
            view
        } else {
            view as T
        }
    }

    fun <T: View> Fragment.findViewByIdInCacheInFragment(id: Int): T {
        var idViewMap = viewsCachedWithFragmentReference[this]
        if (idViewMap == null) {
            idViewMap = SparseArray()
            viewsCachedWithFragmentReference[this] = idViewMap
            val fragment = this
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.whenStateAtLeast(Lifecycle.State.DESTROYED) {
                    viewsCachedWithFragmentReference.remove(fragment)
                }
            }
        }
        var view = idViewMap.get(id)
        return if (view == null) {
            view = requireView().findViewById<T>(id)
            idViewMap.put(id, view)
            view
        } else {
            view as T
        }
    }
}
