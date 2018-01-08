package fingermelody.network.callback;

import android.content.Context;

import fingermelody.network.callback.BaseSubscriber;
import fingermelody.network.progress.ProgressCancelListener;
import fingermelody.network.progress.ProgressDialogHandler;


/**
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo
 * @date 2016/12/12  14:48
 */

public abstract class ProgressSubscriber<T> extends BaseSubscriber<T> implements ProgressCancelListener {

    private Context mContext;
    private ProgressDialogHandler mHandler;

    public ProgressSubscriber(Context context, boolean cancelable){
        super(context);
        this.mContext = context;
        mHandler = new ProgressDialogHandler(context,this,cancelable);
    }

    public void showProgressDialog(){
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    public void dismissProgressDialog(){
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mHandler = null;
        }
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }


    @Override
    public void onNext(T t) {
        dismissProgressDialog();
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
