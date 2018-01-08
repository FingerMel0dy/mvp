package fingermelody.network.exception;


import com.google.gson.JsonObject;

import fingermelody.base.BaseBean;
import rx.Observable;
import rx.functions.Func1;

public class ErrorTransformer<T> implements Observable.Transformer<T, T> {

    private static ErrorTransformer errorTransformer = null;

    @Override
    public Observable<T> call(Observable<T> responseObservable) {

        return responseObservable.map(new Func1<T, T>() {
            @Override
            public T call(T httpResult) {
                if (httpResult == null) {
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");
                } else if (httpResult instanceof BaseBean) {
                    BaseBean baseBean = (BaseBean) httpResult;
                    if (!baseBean.getMsgFlag().equals(ErrorType.SUCCESS)) {
                        throw new ServerException(baseBean.getMsgFlag(), baseBean.getMsgContent());
                    }
                } else if (httpResult instanceof JsonObject) {
                    JsonObject object = (JsonObject) httpResult;
                    if (!"1".equals(object.get("msgFlag").getAsString())) {
                        throw new ServerException(object.get("msgFlag").getAsString(), object.get("msgContent").getAsString());
                    }
                }
                return httpResult;
            }
        });

    }

    /**
     * @return 线程安全, 双层校验
     */
    public static <T> ErrorTransformer<T> getInstance() {

        if (errorTransformer == null) {
            synchronized (ErrorTransformer.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ErrorTransformer<>();
                }
            }
        }
        return errorTransformer;

    }
}
