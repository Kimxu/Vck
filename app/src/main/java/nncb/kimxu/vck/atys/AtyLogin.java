package nncb.kimxu.vck.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import cn.bmob.v3.listener.SaveListener;
import nncb.kimxu.vck.AtyBasicWithEvent;
import nncb.kimxu.vck.R;
import nncb.kimxu.vck.model.User;
import nncb.kimxu.vck.utils.GlobalUtil;

/**
 * Created by xuzhiguo on 15/9/11.
 */
public class AtyLogin extends AtyBasicWithEvent<Object> {

    private EditText edUsername;
    private EditText edPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isLogin()){
            AtyMain.launch(mActivity);
            finish();
        }
        setContentView(R.layout.aty_login);
        initView();
    }

    private void initView() {
        edUsername= (EditText) findViewById(R.id.username);
        edPassword= (EditText) findViewById(R.id.password);

    }


    public void submit(View v){
        String username =edUsername.getText().toString();
        String password =edPassword.getText().toString();
        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            GlobalUtil.showToast(mActivity,"请输入用户名和密码");
            return;
        }
        User user =new User(username,password);
        user.login(mActivity, new SaveListener() {
            @Override
            public void onSuccess() {
                AtyMain.launch(mActivity);
                finish();
            }
            @Override
            public void onFailure(int i, String s) {
                GlobalUtil.showToast(mActivity,s);
            }
        });

    }
    public void register(View v){
        AtyRegister.launch(mActivity);
    }


    @Override
    public void onEventMainThread(Object event) {

    }

    public static void launch(Activity activity) {

        Intent intent = new Intent(activity, AtyLogin.class);
        activity.startActivity(intent);
    }

}
