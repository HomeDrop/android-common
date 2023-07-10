package com.homedrop.common.base

import android.annotation.TargetApi
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.SparseArray
import androidx.activity.result.ActivityResult
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.homedrop.common.listener.PermissionCallback

abstract class BaseActivity<B : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    val activityLauncher: BetterActivityResult<Intent, ActivityResult> =
        BetterActivityResult.registerActivityForResult(this)
    private val permissionCallbackSparseArray = SparseArray<PermissionCallback>()

    protected lateinit var binding: B

    protected lateinit var viewModel: VM

    protected open fun getViewModelClass(): Class<VM>? {
        return null
    }

    protected abstract fun getViewBinding(): B

    protected open fun receiveExtras() {}

    /**
     * Only use to create/initialise views
     */
    protected open fun setUpViews() {}

    /**
     * Only use to register observers
     */
    protected open fun observeData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initViewModel()
        receiveExtras()
        setUpViews()
        observeData()
    }

    private fun initViewModel() {
        if (getViewModelClass() == null) {
            return
        }
        viewModel = ViewModelProvider(this)[getViewModelClass()!!]
    }

    fun hasPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            true
        } else {
            checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun requestPermission(
        permission: String,
        requestCode: Int,
        permissionCallback: PermissionCallback
    ) {
        permissionCallbackSparseArray.put(requestCode, permissionCallback)
        requestPermissions(arrayOf(permission), requestCode)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun canRequestPermission(permission: String): Boolean {
        return shouldShowRequestPermissionRationale(permission)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val callback = permissionCallbackSparseArray.get(requestCode, null) ?: return
        if (grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback.onGrant()
            } else {
                callback.onDeny()
            }
        } else {
            callback.onDeny()
        }
    }

}

fun Fragment.getActivityLauncher(): BetterActivityResult<Intent, ActivityResult> {
    return (activity as BaseActivity<*, *>).activityLauncher
}