package sk.rafig.mhdke.util

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class Animator {

    fun stopAnimation(view: View){
        val animation: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat("scaleX", 1f),
            PropertyValuesHolder.ofFloat("scaleY", 1f))
        animation.repeatCount = 0
    }

    fun pulseAnimation(view: View, timeMs: Long){
        val animation: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
            )
        animation.duration = timeMs
        animation.repeatCount = ObjectAnimator.INFINITE
        animation.repeatMode = ObjectAnimator.REVERSE
        animation.interpolator = FastOutSlowInInterpolator()
        animation.start()
    }

    fun jumpAnimation(view: View){
        val animation: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofInt("backgroundColor", Color.WHITE, Color.RED, Color.WHITE)
        )

        animation.duration = 1000
        animation.setEvaluator(ArgbEvaluator())
        animation.repeatMode = ObjectAnimator.REVERSE
        animation.repeatCount = ObjectAnimator.INFINITE
        animation.start()
    }

    fun emptyAnimation(view: View, speed: Long, toLeft: Float){
        val animation: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat("translationX", 350f),
            PropertyValuesHolder.ofFloat("rotation", 720f)
        )

        animation.duration = speed
        animation.repeatCount = ObjectAnimator.INFINITE

        animation.repeatMode = ObjectAnimator.RESTART
        animation.interpolator = LinearInterpolator()
        animation.start()
    }
}