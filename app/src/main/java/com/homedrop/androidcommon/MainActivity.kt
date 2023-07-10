package com.homedrop.androidcommon

import com.homedrop.androidcommon.databinding.ActivityMainBinding
import com.homedrop.common.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, Nothing>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        super.setUpViews()
    }

}