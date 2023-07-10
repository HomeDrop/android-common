package com.homedrop.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!
    private var hasInitializedRootView = false

    protected lateinit var viewModel: VM

    protected open fun getViewModelClass(): Class<VM>? {
        return null
    }

    protected abstract fun getViewBinding(): B

    protected open val useSharedViewModel = false

    protected open val keepBindingRetained = false

    protected open fun receiveExtras() {}

    /**
     * Only use to create/initialise views
     */
    protected open fun setUpViews() {}

    /**
     * Only use to register observers
     */
    protected open fun observeData() {}

    /**
     * Only use to initialise Toolbar
     */
    protected open fun setUpMenu() {}

    /**
     * @return true if fragment consumed the back press else false to let activity handle it
     */
    protected open fun onBackPressed(): Boolean {
        return false
    }

    /**
     * This function can be called in case to clearing any view or listener or observer in fragment
     */
    protected open fun doCleanup() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        receiveExtras()
        initBackPress()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = getViewBinding()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMenu()
        if (!hasInitializedRootView && keepBindingRetained) {
            hasInitializedRootView = true
            setUpViews()
        } else if (!keepBindingRetained) {
            setUpViews()
        }
        observeData()
    }

    override fun onDestroyView() {
        doCleanup()
        if (!keepBindingRetained) {
            _binding = null
        }
        super.onDestroyView()
    }

    private fun initBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (!onBackPressed()) {
                isEnabled = false
                activity?.onBackPressed()
            }
        }
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

    protected fun activity(): BaseActivity<*,*> {
        return requireActivity() as BaseActivity<*, *>
    }

    protected fun isBindingInitialized(): Boolean {
        return _binding != null
    }

}