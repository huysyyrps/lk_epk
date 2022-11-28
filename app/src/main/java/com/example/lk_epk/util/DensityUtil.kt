package com.example.lk_epk.util

import android.content.Context
import android.util.TypedValue

object DensityUtil {
    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    fun dp2px(context: Context, dpFloat: Float): Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpFloat,context.resources.displayMetrics).toInt()
    }
    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    fun sp2px(context: Context,spFloat: Float): Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spFloat,context.resources.displayMetrics).toInt()
    }
    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    fun px2dp(context: Context,pxFloat: Float): Float{
        var scale : Float = context.resources.displayMetrics.density
        return (pxFloat/scale)
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @param pxVal
     * @return
     */
    fun px2sp(context: Context, pxFloat: Float):Float {
        return (pxFloat / context.resources.displayMetrics.scaledDensity)
    }
}