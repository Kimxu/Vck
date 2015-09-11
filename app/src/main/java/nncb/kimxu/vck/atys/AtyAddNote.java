package nncb.kimxu.vck.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.greenrobot.event.EventBus;
import nncb.kimxu.vck.AtyBasicWithEvent;
import nncb.kimxu.vck.R;
import nncb.kimxu.vck.menu.OptionsMenu;
import nncb.kimxu.vck.model.Note;
import nncb.kimxu.vck.modelev.AddNote2Main;
import nncb.kimxu.vck.utils.GlobalUtil;
import nncb.kimxu.vck.utils.Klog;

/**
 * Created by xuzhiguo on 15/9/10.
 */
public class AtyAddNote extends AtyBasicWithEvent<Object> {
    private Toolbar mToolbar;
    private final int[] drawables = new int[2];
    private EditText edContent;
    private boolean isEdit =false;
    private Note editNote;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_add_note);
        initViews();

    }

    @Override
    public void onEventMainThread(Object event) {

    }

    private void setBgColor() {
        drawables[0] = getResources().getColor(R.color.background_pink);
        drawables[1] = getResources().getColor(R.color.background_blue);
        int position = getIntent().getIntExtra("position", 0);
        findViewById(R.id.ll_add_note).setBackgroundColor(drawables[position]);
        mToolbar.setBackgroundColor(colorBurn(drawables[position]));
    }



    private void initViews() {

        initLayoutId();
        initToolbar();
        setIntentData();
        setBgColor();
    }

    private void setIntentData() {
        editNote = (Note) getIntent().getSerializableExtra("note");
        if (editNote!=null){
            isEdit=true;
            edContent.setText(editNote.getContent());
        }
    }


    private void initLayoutId() {
        edContent= (EditText) findViewById(R.id.content);
        Editable etext = edContent.getText();
        Selection.setSelection(etext, etext.length());
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setContentInsetsRelative(0, 0);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cancel:
                        finish();
                        break;
                    case R.id.submit:
                        submitData();
                        break;

                }
                return false;
            }
        });
    }

    private void submitData() {

        String content =edContent.getText().toString();
        if (TextUtils.isEmpty(content))
            return;
        GlobalUtil.showLoadingDialog(mActivity, "请稍后");

        //修改模式
        if (isEdit){
            if(editNote.getContent().equals(content)){
                Klog.i("no edit");
                GlobalUtil.closeDialog();
                return;
            }
            editNote.setContent(content);
            editNote.update(mActivity,editNote.getObjectId(),new UpdateListener() {
                @Override
                public void onSuccess() {
                    Klog.i("onSuccess");
                    GlobalUtil.closeDialog();
                    EventBus.getDefault().post(
                            new AddNote2Main(editNote,true));
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    Klog.i("onFailure");
                    GlobalUtil.closeDialog();
                }
            });
        }else {
            final Note note = new Note(mActivity);
            note.setContent(content);
            note.save(mActivity, new SaveListener() {
                @Override
                public void onSuccess() {
                    GlobalUtil.closeDialog();
                    EventBus.getDefault().post(
                            new AddNote2Main(note));
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    GlobalUtil.closeDialog();
                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        OptionsMenu.onCreatedAddNoteMenu(menu, getMenuInflater(), this);
        return super.onCreateOptionsMenu(menu);
    }

    public static void launch(Activity activity, int position,Note note) {

        Intent intent = new Intent(activity, AtyAddNote.class).putExtra("position", position);
        if (note!=null) {
            intent.putExtra("note", note);
        }
        activity.startActivity(intent);
    }


}
