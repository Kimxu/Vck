package nncb.kimxu.vck.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kimxu.widget.SwitchButton;
import nncb.kimxu.vck.R;

/**
 * 设置Layout
 */
public class SettingLayout extends RelativeLayout {

    private TextView titleTv;
    private SwitchButton switchButton;

    public SettingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData(context, attrs);
    }

    public SettingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);

    }

    public SettingLayout(Context context) {
        super(context);
        initData(context, null);
    }

    private void initData(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.w_setting_layout, this, true);
        titleTv = (TextView) v.findViewById(R.id.title);
        switchButton = (SwitchButton) v.findViewById(R.id.switch_button);
        setViewDatas(context, attrs);
    }

    private void setViewDatas(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingLayout);
        if (!typedArray.getBoolean(R.styleable.SettingLayout_hasChecked, true)) {
            switchButton.setVisibility(View.GONE);
        }
        String title = typedArray.getString(R.styleable.SettingLayout_text);
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }
        typedArray.recycle();
    }

    public void setTitleText(String titleStr) {
        titleTv.setText(titleStr);
    }


    public boolean setSettingLayoutListener(final SwitchCallBack sBCallBack) {
        if (switchButton.getVisibility() == View.VISIBLE) {
            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (sBCallBack != null) {
                        sBCallBack.callback(buttonView, isChecked);
                    }
                }
            });
            return switchButton.isChecked();
        } else {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sBCallBack != null) {
                        sBCallBack.callback(null, false);
                    }
                }
            });
            return false;
        }

    }

    public boolean slideToChecked() {
        switchButton.slideToChecked(!switchButton.isChecked());
        return switchButton.isChecked();
    }

    /**
     * 是否有点击滑动框
     */
    public void noChecked() {
        switchButton.setVisibility(View.GONE);

    }


    public SwitchButton getSwitchButton() {
        return switchButton;
    }

    public interface SwitchCallBack {
        void callback(CompoundButton buttonView, boolean isChecked);
    }
}
