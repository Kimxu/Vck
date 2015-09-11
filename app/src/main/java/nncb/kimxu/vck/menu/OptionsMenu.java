package nncb.kimxu.vck.menu;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import nncb.kimxu.vck.R;
import nncb.kimxu.vck.utils.DeviceUtil;
import nncb.kimxu.vck.widget.MenuView;

/**
 * Created by xuzhiguo on 15/9/10.
 */
public class OptionsMenu {

    public static void onCreatedAddNoteMenu(Menu menu,MenuInflater inflater,Activity activity){
        inflater.inflate(R.menu.add_note, menu);
        MenuItem item = menu.findItem(R.id.title);
        MenuView menuView= (MenuView) item.getActionView();
        int screenWidth = DeviceUtil.getSceenWidth(activity);
        int itemWidth = activity.getResources().getDimensionPixelSize(
                R.dimen.abs__action_button_min_width);
        int width =  screenWidth - 2*itemWidth;
        menuView.setText("添加笔记");
        menuView.setTextSize(20);
        menuView.setTextColor(activity.getResources().getColor(R.color.white));
        menuView.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));


    }
}
