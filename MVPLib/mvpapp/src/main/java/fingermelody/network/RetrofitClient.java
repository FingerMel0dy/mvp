package fingermelody.network;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import fingermelody.base.BaseApp;
import fingermelody.network.exception.ErrorTransformer;
import fingermelody.util.NetWorkUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zwl on 2018/1/4.
 */

public class RetrofitClient<S> {
    private static final int DEFAULT_TIMEOUT = 20;
    private final String BASE_URL;
    private Class<S> service;
    private S apiService;
    private OkHttpClient.Builder okHttpBuilder;

    public RetrofitClient(String baseUrl, final Class<S> service) {
        this.BASE_URL = baseUrl;
        this.service = service;
        if (okHttpBuilder == null) {
            synchronized (RetrofitClient.class) {
                if (okHttpBuilder == null) {
                    okHttpBuilder = new OkHttpClient.Builder();
                    okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                    okHttpBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                }
            }
        }
    }

    public void addLoggingInterceptor() {
        //测试log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(logging);
    }

    public void addCacheInterceptor() {
        // 网络文件缓存路劲
        File cacheFile = new File(BaseApp.getInstance().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        okHttpBuilder.addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR);
        okHttpBuilder.addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE);
        okHttpBuilder.cache(cache);
    }

    public OkHttpClient.Builder getOkHttpBuilder() {
        return okHttpBuilder;
    }

    public S getInstance() {
        if (apiService == null) {
            synchronized (RetrofitClient.class) {
                if (apiService == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .client(okHttpBuilder.build())
                            .baseUrl(BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                    apiService = retrofit.create(service);
                }
            }
        }
        return apiService;
    }


    public <T> void toSubscribe(RxAppCompatActivity aty, Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorTransformer.<T>getInstance())
                .compose(aty.bindToLifecycle())
                .subscribe(subscriber);
    }

    public <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorTransformer.<T>getInstance())
                .subscribe(subscriber);
    }


    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build();
            } else {
                return originalResponse;
            }
        }
    };

    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(BaseApp.getInstance())) {
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached")
                        .build();
            }
            return chain.proceed(request);
        }
    };
}
