package fingermelody.inface;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by FingerMelody on 2018/1/3.
 */

public interface BaseViewInface<P> {
    P createPresenter();

    void initConnect();

    void initTool();

    void initView();

    void initData();

    void initEvent();

    void bindView(Activity view, Integer id);
}
