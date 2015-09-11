package nncb.kimxu.vck.model;

import android.content.Context;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import nncb.kimxu.vck.utils.FormatDate;

/**
 * Created by xuzhiguo on 15/9/9.
 */
public class Note extends BmobObject implements Serializable{
    private String time;
    private String content;
    private String userId;

    public Note(Context context){
        super();
        setTime(FormatDate.get(7));
        String userId =BmobUser.getCurrentUser(context).getObjectId();
        setUserId(userId);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
