package com.homedrop.common.base

import com.homedrop.common.ViewType

interface BaseViewType {

    @get:ViewType
    val viewType: Int

}