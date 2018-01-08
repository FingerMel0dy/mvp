package fingermelody.base;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import fingermelody.inface.BaseModelInface;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by FingerMelody on 2018/1/3.
 */

public class BaseModel<P> implements BaseModelInface<P> {
    private Reference<P> mPresenterRef;

    public BaseModel(P presenter) {
        bindPresenter(presenter);
    }

    public BaseModel() {
    }

    /**
     * T  presenter 绑定
     */
    @Override
    public void bindPresenter(P presenter) {
        mPresenterRef = new WeakReference<P>(presenter);
    }


    /**
     * 获取 调度者
     *
     * @return
     */
    @Override
    public P getPresenter() {
        return mPresenterRef.get();
    }


}


