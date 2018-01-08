package fingermelody.network.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo
 * @date 2016/12/12  15:02
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog pd;

    private final WeakReference<Context> context;
    private boolean cancelable;
    private final WeakReference<ProgressCancelListener> mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.context = new WeakReference<Context>(context);
        this.mProgressCancelListener = new WeakReference<ProgressCancelListener>(mProgressCancelListener);
        this.cancelable = cancelable;
    }

    private void initProgressDialog(){
        if (pd == null) {
            pd = new ProgressDialog(context.get());
            pd.setMessage("loading..");
            pd.setCanceledOnTouchOutside(false);
            pd.setCancelable(cancelable);
            if (cancelable) {
                pd.setOnCancelListener(dialogInterface -> mProgressCancelListener.get().onCancelProgress());
            }
        }
        if (!pd.isShowing()) {
            pd.show();
        }
    }

    private void dismissProgressDialog(){
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
