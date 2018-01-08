package fingermelody.network.exception;

/**
 * Created by gaosheng on 2016/11/6.
 * 22:23
 * com.example.gaosheng.myapplication.exception
 * 自定义的服务器异常
 */

public class ServerException extends RuntimeException {
    public String code;
    public String message;

    public ServerException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
