package fingermelody.network.callback;


import android.content.Context;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import fingermelody.network.exception.ErrorType;
import fingermelody.network.exception.ServerException;
import fingermelody.util.ToastUtil;
import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    private Context mContext;

    public BaseSubscriber(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onStart() {
        super.onStart();
/*        if(!NetworkUtils.isConnected(mContext)){
            unsubscribe();
            onError(new ServerException(ErrorType.NETWORK_ERROR, "未连接网络"));
        }*/
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof ServerException) {
            ServerException httpException = (ServerException) e;
            switch (httpException.code) {
                case ErrorType.EMPTY_BEAN:
//                    LogUtils.e("网络请求未收到返回");
                    ToastUtil.showToastShort(httpException.getMessage());
                    break;
                case ErrorType.NETWORK_ERROR:
//                    HealthUtil.showNONetworkDialog(mContext);
                    ToastUtil.showToastShort(httpException.getMessage());
                    break;
                default:
                    ToastUtil.showToastShort(httpException.getMessage());
                    break;
            }
        } else if (e instanceof JSONException
                || e instanceof ParseException ||e instanceof JsonParseException) {
            ToastUtil.showToastShort("数据解析错误");
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException || e
                instanceof ConnectTimeoutException) {
//            HealthUtil.showNONetworkDialog(mContext);
            ToastUtil.showToastShort("访问超时,请稍后再试!");
        } else {
            ToastUtil.showToastShort("网络错误");
        }
    }

    @Override
    public void onCompleted() {

    }
}
