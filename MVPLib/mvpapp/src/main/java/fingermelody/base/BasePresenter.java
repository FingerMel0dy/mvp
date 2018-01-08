package fingermelody.base;

import android.util.Log;
import android.util.SparseArray;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import fingermelody.inface.BaseModelInface;
import fingermelody.inface.BasePresenterInface;

/**
 * Created by FingerMelody on 2018/1/2.
 */

public abstract class BasePresenter<V, M> implements BasePresenterInface<V, M> {
    /**
     * View接口类型的弱引用 (为了避免内存泄漏)
     */
    private Reference<V> mViewRef;


    private M model;

    public BasePresenter(V view) {
        bindView(view);

    }


    /**
     * 建立关联
     *
     * @param view
     */
    @Override
    public void bindView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    /**
     * 建立 Model 和Presenter （调度者）的关联
     */
    @Override
    public void bindMedel(M model) {
        this.model = model;
    }

    /**
     * 获取View
     *
     * @return
     */
    @Override
    public V getView() {
        return mViewRef.get();
    }

    /**
     * 获取 调度者
     *
     * @return
     */
    @Override
    public M getModel() {
        return model;
    }

    /**
     * view 是否绑定上
     *
     * @return
     */
    @Override
    public boolean isViewBinded() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 解除关联
     */
    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
            model = null;
        }
    }
}
