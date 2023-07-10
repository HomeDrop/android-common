package com.homedrop.common.ktx

import java.util.regex.Pattern

val String.parsedOtp: String?
    get() {
        val pattern = Pattern.compile(".*([0-9]{6}).*")
        val matcher = pattern.matcher(this)
        return try {
            if (matcher.find()) {
                matcher.group(1)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }