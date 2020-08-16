package ir.iahrari.githubseeker.ui.util

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ExtendedFabBehavior: HideBottomViewOnScrollBehavior<ExtendedFloatingActionButton>{
    constructor(context: Context, attr: AttributeSet){}
    constructor(){}

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ExtendedFloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        Log.i("Extended fab behavior", dyConsumed.toString())
        if(dyConsumed > 0 && child.visibility == View.VISIBLE){
            child.shrink()
        } else if(dyConsumed <= 0 && child.visibility == View.VISIBLE) child.extend()
    }
}