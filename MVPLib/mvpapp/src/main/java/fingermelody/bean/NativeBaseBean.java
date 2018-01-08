package fingermelody.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by FinerMelody on 2017/9/16.
 */

public class NativeBaseBean implements MultiItemEntity,Serializable {
    public int getVIEWSTYLE() {
        return VIEWSTYLE;
    }

    private  int  VIEWSTYLE=0;
    public void setVIEWSTYLE(int VIEWSTYLE) {
        this.VIEWSTYLE = VIEWSTYLE;
    }

    @Override
    public int getItemType() {
        return VIEWSTYLE;
    }
}
