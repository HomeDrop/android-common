package com.homedrop.common.base

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.homedrop.common.ITEM_NONE
import java.util.Locale

abstract class BaseRecyclerAdapter<T : BaseViewType> :
    RecyclerView.Adapter<DataBoundViewHolder<ViewDataBinding>>() {

    companion object {
        private const val TAG = "BaseRecyclerAdapter"
    }

    var dataList = ArrayList<T>()
        private set
    private val supportedViewBinderResolverMap = SparseArray<ViewDataBinder<ViewDataBinding, T>>()

    protected abstract fun getSupportedViewDataBinder(): List<ViewDataBinder<ViewDataBinding, T>>

    protected fun initViewDataBinders() {
        getSupportedViewDataBinder().forEach { viewDataBinder ->
            supportedViewBinderResolverMap.put(viewDataBinder.viewType, viewDataBinder)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBoundViewHolder<ViewDataBinding> {
        val viewDataBinder = supportedViewBinderResolverMap.get(viewType)
        if (viewDataBinder == null) {
            val message =
                String.format(Locale.US, "No view binder found for viewType: %d", viewType)
            throw IllegalArgumentException(message)
        }
        val binder = viewDataBinder.createBinder(parent)
        binder.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return DataBoundViewHolder(binder)
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ViewDataBinding>, position: Int) {
        val viewDataBinder = supportedViewBinderResolverMap.get(getItemViewType(position))
        if (viewDataBinder == null) {
            Log.e(TAG, "Please add the supported view binder to view binders list in adapter")
            return
        }
        viewDataBinder.bindData(holder.binding, dataList[position], position)
        holder.binding.executePendingBindings()
    }

    override fun onBindViewHolder(
        holder: DataBoundViewHolder<ViewDataBinding>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val viewDataBinder = supportedViewBinderResolverMap.get(getItemViewType(position))
            if (viewDataBinder == null) {
                Log.e(TAG, "Please add the supported view binder to view binders list in adapter")
                return
            }
            val bundle = payloads[0] as? Bundle
            viewDataBinder.bindData(holder.binding, bundle, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= 0 && position < dataList.size) {
            return dataList[position].viewType
        }
        return ITEM_NONE
    }

    override fun getItemCount() = dataList.size

    operator fun get(position: Int): BaseViewType {
        return dataList[position]
    }

    operator fun <T> get(position: Int): T? {
        return dataList[position] as? T
    }

    fun setItems(items: List<T>) {
        if (items is ArrayList<T>) {
            dataList = items
        } else {
            dataList = ArrayList()
            dataList.addAll(items)
        }
        notifyDataSetChanged()
    }

    fun addItem(item: T, position: Int) {
        dataList.add(position, item)
        notifyItemInserted(position)
    }

    fun addItem(item: T) {
        dataList.add(item)
        notifyItemInserted(dataList.size - 1)
    }

    fun updateItem(item: T, position: Int) {
        dataList[position] = item
        notifyItemChanged(position)
    }

    fun removeItem(position: Int, updateAllTrailing: Boolean = false) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
        if (updateAllTrailing) {
            notifyItemRangeChanged(position, dataList.size - position)
        }
    }

    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

}