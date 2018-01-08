package fingermelody.base;

import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import fingermelody.bean.NativeBaseBean;

/**
 * Created by FingerMelody on 2017/9/14.
 */

public abstract class BaseAdapter<T extends NativeBaseBean, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private static final int DEFAULT_VIEW_TYPE = -0xff;
    public static final int TYPE_NOT_FOUND = -404;
    public static final int VIEW_STYLE_1 = 0;
    public static final int VIEW_STYLE_2 = 1;
    public static final int VIEW_STYLE_3 = 2;
    public static final int VIEW_STYLE_4 = 3;
    public static final int VIEW_STYLE_5 = 4;
    public static final int VIEW_STYLE_6 = 5;
    public static final int VIEW_STYLE_7 = 6;
    public static final int VIEW_STYLE_8 = 7;
    public static final int VIEW_STYLE_9 = 8;
    public static final int VIEW_STYLE_10 = 9;
    private SparseIntArray layouts;


    public BaseAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }


    public BaseAdapter(List<T> data) {
        super(data);
    }

    public BaseAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(K helper, T item) {
    }

    public void updataView(List<T> data, int position) {

    }

    @Override
    protected int getDefItemViewType(int position) {
        //  更新页面
        updataView(mData, position);
        Object item = mData.get(position);
        if (item instanceof MultiItemEntity) {
            return ((MultiItemEntity) item).getItemType();
        }
        return DEFAULT_VIEW_TYPE;
    }

    protected void setDefaultViewTypeLayout(int layoutResId) {
        addItemType(DEFAULT_VIEW_TYPE, layoutResId);
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }


    private int getLayoutId(int viewType) {
        return layouts.get(viewType, TYPE_NOT_FOUND);
    }

    protected void addItemType(int type, int layoutResId) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(type, layoutResId);
    }

    /**
     * 设置页面样式
     *
     * @param t
     * @param viewStytle
     */
    public void setViewStyle(T t, int viewStytle) {
        if (t != null) {
            t.setVIEWSTYLE(viewStytle);
        }
    }


}
