package fingermelody.base;

import java.io.Serializable;

/**
 * Data:2016/5/27 17:47
 * Created by WCP
 */
public class BaseBean implements Serializable {

    private String msgFlag;
    private String msgContent;

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgFlag() {
        return msgFlag;
    }

    public void setMsgFlag(String msgFlag) {
        this.msgFlag = msgFlag;
    }

    @Override
    public String toString() {
        return "NativeBaseBean{" +
                ", msgFlag='" + msgFlag + '\'' +
                ", msgContent='" + msgContent + '\'' +
                '}';
    }
}
