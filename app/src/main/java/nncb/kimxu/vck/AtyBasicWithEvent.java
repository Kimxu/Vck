package nncb.kimxu.vck;

import android.os.Bundle;

import de.greenrobot.event.EventBus;

public abstract class AtyBasicWithEvent  <T> extends AtyBasic {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    public abstract void onEventMainThread(T event);




}
