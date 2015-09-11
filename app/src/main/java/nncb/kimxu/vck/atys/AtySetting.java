package nncb.kimxu.vck.atys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import nncb.kimxu.vck.AtyBasic;
import nncb.kimxu.vck.R;
import nncb.kimxu.vck.utils.Klog;
import nncb.kimxu.vck.widget.SettingLayout;

public class AtySetting extends AtyBasic implements View.OnClickListener{

    SettingLayout setSkin;
    SettingLayout set2;
    SettingLayout set3;
    SettingLayout set4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_setting);
        initViews();


        setSkin.setSettingLayoutListener(new SettingLayout.SwitchCallBack() {
            @Override
            public void callback(CompoundButton buttonView, boolean isChecked) {
                Klog.d("Ss");
            }
        });
        set2.setSettingLayoutListener(new SettingLayout.SwitchCallBack() {
            @Override
            public void callback(CompoundButton buttonView, boolean isChecked) {
                Klog.d("Ss"+buttonView.getId());
            }
        });
        set3.setSettingLayoutListener(new SettingLayout.SwitchCallBack() {
            @Override
            public void callback(CompoundButton buttonView, boolean isChecked) {
                Klog.d("Ss" );
            }
        });
        set4.setSettingLayoutListener(new SettingLayout.SwitchCallBack() {
            @Override
            public void callback(CompoundButton buttonView, boolean isChecked) {
                Klog.d("Ss");
            }
        });
    }

    private void initViews() {
        setSkin= (SettingLayout) findViewById(R.id.set_skin);
        setSkin.setOnClickListener(this);
        set2= (SettingLayout) findViewById(R.id.set_2);
        set2.setOnClickListener(this);
        set3= (SettingLayout) findViewById(R.id.set_3);
        set3.setOnClickListener(this);
        set4= (SettingLayout) findViewById(R.id.set_4);
        set4.setOnClickListener(this);
    }
    public static void launch(Context context) {
        Intent intent = new Intent(context, AtySetting.class);
        context.startActivity(intent);
    }

    public void bTlogout(View v){
        logout();
        AtyLogin.launch(mActivity);
        finish();
    }

    @Override
    public void onClick(View view) {

        ((SettingLayout)view).slideToChecked();
    }


}
