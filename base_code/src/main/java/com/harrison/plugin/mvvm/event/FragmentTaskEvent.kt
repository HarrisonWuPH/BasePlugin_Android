package com.harrison.plugin.mvvm.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.harrison.plugin.mvvm.BaseActivityView
import com.harrison.plugin.mvvm.BaseFragmentView

/**
 * 配合BaseActivityView和BaseFragmentView完成栈顶事件响应，只有栈顶的页面才会响应事件
 */
class FragmentTaskEvent<T>:MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, {
            if(isFrontTask(owner)){
                observer.onChanged(it)
            }
        })
    }

    /**
     * 判断是否在栈顶
     */
    fun isFrontTask(owner: LifecycleOwner):Boolean{
        var baseActivity: BaseActivityView? = null
        if(owner is BaseActivityView){
            baseActivity = owner
        }
        if(owner is BaseFragmentView){
            baseActivity = owner.requireActivity() as BaseActivityView
        }
        if(baseActivity != null){
            if(owner is BaseActivityView && baseActivity.fragmentViewStack.size == 0){
                return true
            }else if(baseActivity.fragmentViewStack.last() == owner){
                return true
            }else{
                return false
            }
        }
        return true
    }
}