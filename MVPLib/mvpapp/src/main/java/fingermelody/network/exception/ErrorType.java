package fingermelody.network.exception;

/**
 * Created by gaosheng on 2016/11/6.
 * 21:50
 * com.example.gaosheng.myapplication.exception
 */

public interface ErrorType {

    /**
     * 请求成功
     */
    String SUCCESS = "1";
    /**
     * 未知错误
     */
    String UNKONW = "1000";

    /**
     * 解析错误
     */
    String PARSE_ERROR = "1001";
    /**
     * 网络错误
     */
    String NETWORK_ERROR = "1002";

    /**
     * 解析对象为空
     */
    String EMPTY_BEAN = "1004";


    /**
     *
     */
    String HTTP_ERROR = "1003";
}
