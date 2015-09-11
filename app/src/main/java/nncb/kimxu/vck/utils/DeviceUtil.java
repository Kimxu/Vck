package nncb.kimxu.vck.utils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by xuzhiguo on 15/9/10.
 */
public class DeviceUtil {
    public static int getSceenWidth(Context context) {
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth();
    }

}
