package com.homedrop.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat

object CommonLib {

    fun hasLocationPermission(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationEnabled(context: Context): Boolean {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(manager)
    }

    fun getCountryCode(context: Context): String {
        return context.resources.configuration.locales.get(0)?.country ?: ""
    }

    fun getFirstInstallTime(context: Context): Long {
        return try {
            context.applicationContext
                .packageManager
                .getPackageInfo("me.flatmates.gauravbordoloi", 0)
                .firstInstallTime
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

}