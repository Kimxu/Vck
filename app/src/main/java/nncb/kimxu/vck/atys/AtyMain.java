package nncb.kimxu.vck.atys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import nncb.kimxu.vck.AtyBasicWithEvent;
import nncb.kimxu.vck.R;
import nncb.kimxu.vck.adapter.FragMainAdapter;
import nncb.kimxu.vck.frag.FragMain;
import nncb.kimxu.vck.model.Note;
import nncb.kimxu.vck.modelev.AddNote2Main;
import nncb.kimxu.vck.widget.NoScrollViewPager;
import nncb.kimxu.vck.widget.PagerSlidingTabStrip;

public class AtyMain  extends AtyBasicWithEvent<AddNote2Main> {
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private NoScrollViewPager mViewPager;
    private Toolbar mToolbar;
    private  MyPagerAdapter adapter;
    private int mPosition;
    private FragMain[] fragMains =new FragMain[2];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);

        initView();
    }

    @Override
    public void onEventMainThread(AddNote2Main event) {
        FragMain fragMain = adapter.getItem(mPosition);
        FragMainAdapter fmad= fragMain.getAdapter();
        LinearLayoutManager llmng= fragMain.getLinearLayoutManager();
        if (!event.isEdit()) {

            fmad.insert(event.getNote(), 0);
            llmng.scrollToPosition(0);



        }else {
            int editPosition= fragMain.getEditPosition();
            Note editNote = event.getNote();
            fmad.setItem(editNote,editPosition);
            llmng.scrollToPosition(editPosition);
        }
    }

    private void initView() {
        initToolbar();
        initFrag();
        initTab();
        initTabsValue();

    }

    private void initFrag() {
        fragMains[0]=FragMain.newInstance(0);
        fragMains[1]=FragMain.newInstance(1);



    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setTitle("诺笔记");
        // toolbar.setSubtitle("副标题");
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ab_setting:
                        AtySetting.launch(mActivity);

                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }


    private void initTab() {
        adapter =new MyPagerAdapter(getSupportFragmentManager());
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (NoScrollViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                colorChange(position);
                mPosition=position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        mViewPager.setNoScroll(false);
    }





    /**
     * mPagerSlidingTabStrip默认值配置
     */
    private void initTabsValue() {
        // tab的分割线颜色
        mPagerSlidingTabStrip.setDividerColor(Color.TRANSPARENT);
        // tab底线高度
        mPagerSlidingTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1, getResources().getDisplayMetrics()));
        // 游标高度
        mPagerSlidingTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                5, getResources().getDisplayMetrics()));
        mPagerSlidingTabStrip.setTextSize(30);

        colorChange(0);

    }

    /**
     * 界面颜色的更改
     */
    @SuppressLint("NewApi")
    private void colorChange(int position) {
        int[] colors = new int[]{getResources().getColor(R.color.background_pink), getResources().getColor(R.color.background_blue)};
                /* 界面颜色UI统一性处理,看起来更Material一些 */
        mPagerSlidingTabStrip.setBackgroundColor(colors[position]);
        // 其中状态栏、游标、底部导航栏的颜色需要加深一下，也可以不加，具体情况在代码之后说明
        mPagerSlidingTabStrip.setIndicatorColor(colorBurn(colors[position]));
        mPagerSlidingTabStrip.setTextColor(colors[position]);
        mPagerSlidingTabStrip.setSelectedTextColor(getResources().getColor(R.color.white));
        mToolbar.setBackgroundColor(colorBurn(colors[position]));
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            // 很明显，这两货是新API才有的。
            window.setStatusBarColor(colorBurn(colors[position]));
            window.setNavigationBarColor(colorBurn(colors[position]));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /* ***************FragmentPagerAdapter***************** */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {getString(R.string.tab1), getString(R.string.tab2)};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public FragMain getItem(int position) {

            return fragMains[position];
        }

    }


    public static void launch(Activity activity) {

        Intent intent = new Intent(activity, AtyMain.class);
        activity.startActivity(intent);
    }

}
