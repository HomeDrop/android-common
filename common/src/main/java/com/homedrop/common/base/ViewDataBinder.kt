package com.homedrop.common.base

import com.homedrop.common.ViewType
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

abstract class ViewDataBinder<V : ViewDataBinding, T : BaseViewType> {

    @get:ViewType
    abstract val viewType: Int

    abstract fun createBinder(parent: ViewGroup): V

    abstract fun bindData(binding: V, data: T, position: Int)

    fun bindData(binding: V, data: Bundle?, position: Int) {}
}