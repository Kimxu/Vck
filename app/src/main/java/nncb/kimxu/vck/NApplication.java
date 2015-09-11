package nncb.kimxu.vck;

import android.app.Application;

import cn.bmob.v3.Bmob;
import nncb.kimxu.vck.utils.Klog;

/**
 * Created by xuzhiguo on 15/9/9.
 */
public class NApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Klog.getInstance().setDebug(true);
        Bmob.initialize(this, "f96043439dec8b450cc111b9b48be12c");
    }
}
