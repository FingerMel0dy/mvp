package fingermelody.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import fingermelody.inface.BaseViewInface;

/**
 * Created by FingerMelody on 2018/1/2.
 */

public abstract class BaseViewActivity<V, P extends BasePresenter<V, M>, M extends BaseModel<P>> extends RxAppCompatActivity implements BaseViewInface<P> {
    /**
     * Presenter对象
     */
    private P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConnect();
        initView();
        initTool();
        initData();
        initEvent();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void initView() {

    }



    @Override
    public void bindView(Activity view, Integer id) {
        setContentView(id);
    }

    @Override
    public void initConnect() {
        mPresenter = createPresenter();
    }
}
