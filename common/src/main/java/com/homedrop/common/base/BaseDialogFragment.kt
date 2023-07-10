package com.homedrop.common.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.homedrop.common.R
import com.homedrop.common.ktx.dpToPx

abstract class BaseDialogFragment<B: ViewBinding, VM: ViewModel> : DialogFragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    protected lateinit var viewModel: VM

    protected open fun getViewModelClass(): Class<VM>? {
        return null
    }

    protected abstract fun getViewBinding(): B

    protected open val fullScreen = false
    protected open val cancellable = true
    protected open val margin = 16
    protected open val useSharedViewModel = false
    protected open fun setUpViews() {}
    protected open fun observeData() {}

    protected open fun receiveExtras() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (fullScreen) {
            setStyle(STYLE_NO_TITLE, R.style.DialogFullScreen)
        }
        init()
        receiveExtras()
    }

    override fun onStart() {
        super.onStart()
        if (fullScreen) {
            dialog?.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            dialog?.window?.setBackgroundDrawable(
                InsetDrawable(
                    ColorDrawable(Color.TRANSPARENT),
                    0
                )
            )
        } else {
            dialog?.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog?.window?.setBackgroundDrawable(
                InsetDrawable(
                    ColorDrawable(Color.TRANSPARENT),
                    dpToPx(margin)
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = cancellable

        setUpViews()
        observeData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun init() {
        if (getViewModelClass() == null) {
            return
        }
        viewModel = if (useSharedViewModel) {
            ViewModelProvider(requireActivity())[getViewModelClass()!!]
        } else {
            ViewModelProvider(this)[getViewModelClass()!!]
        }
    }

}