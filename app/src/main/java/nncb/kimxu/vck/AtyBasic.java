package nncb.kimxu.vck;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by xuzhiguo on 15/9/9.
 */
public abstract class AtyBasic extends AppCompatActivity {
    protected Activity mActivity;
    private static List<Activity> listAty = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        listAty.add(mActivity);
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    protected int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

    public boolean isLogin() {
        BmobUser currentUser = BmobUser.getCurrentUser(this); // 现在的currentUser是null了
        return currentUser != null;
    }

    public void logout() {
//        for (Activity ac:listAty){
//            if (ac!=mActivity)
//                ac.finish();
//        }
        clearAllAtyOutMe();
        BmobUser.logOut(this);   //清除缓存用户对象
    }
    public void clearAllAtyOutMe(){
        for (int i = 0; i < listAty.size(); i++) {
            Activity ac = listAty.get(i);
            if (ac != mActivity)
                ac.finish();
        }
    }

    @Override
    public void finish() {
        listAty.remove(mActivity);
        super.finish();

    }
}
