package com.homedrop.common

import androidx.annotation.IntDef

const val ITEM_NONE = 0
const val ITEM_INVENTORY_PRODUCT = 1
const val ITEM_ORDER_PRODUCT = 2
const val ITEM_TEXT = 4
const val ITEM_ORDER = 5
const val ITEM_CUSTOMER = 6
const val ITEM_SALE = 7
const val ITEM_SUPPLIER = 8
const val ITEM_SALE_PRODUCT = 9
const val ITEM_DRAFT_ORDERS = 10
const val ITEM_SOLD_PRODUCT = 11
const val ITEM_MINI_ORDER_PRODUCT = 12

@IntDef(
    ITEM_NONE,
    ITEM_ORDER_PRODUCT,
    ITEM_TEXT,
    ITEM_ORDER,
    ITEM_INVENTORY_PRODUCT,
    ITEM_CUSTOMER,
    ITEM_SALE,
    ITEM_SUPPLIER,
    ITEM_SALE_PRODUCT,
    ITEM_DRAFT_ORDERS,
    ITEM_SOLD_PRODUCT,
    ITEM_MINI_ORDER_PRODUCT
)
@Retention(AnnotationRetention.SOURCE)
annotation class ViewType