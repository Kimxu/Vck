package nncb.kimxu.vck.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by xuzhiguo on 15/9/11.
 */
public class User extends BmobUser {
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
}
