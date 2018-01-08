package fingermelody.util;

import android.view.Gravity;
import android.widget.Toast;

import fingermelody.base.BaseApp;


public class ToastUtil {
    public static Toast toast;

    public static void showToastShort(String str) {

        if (toast == null) {
            toast = Toast.makeText(BaseApp.getInstance(), str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    public static void showToastLong(String str) {

        if (toast == null) {
            toast = Toast.makeText(BaseApp.getInstance(), str, Toast.LENGTH_LONG);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    public static void showToastCenterShort(String str) {
        Toast toast = Toast.makeText(BaseApp.getInstance(), str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
