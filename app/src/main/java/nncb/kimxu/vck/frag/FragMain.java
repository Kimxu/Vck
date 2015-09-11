package nncb.kimxu.vck.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.AddFloatingActionButton;
import com.marshalchen.ultimaterecyclerview.uiUtils.ScrollSmoothLineaerLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import nncb.kimxu.vck.R;
import nncb.kimxu.vck.adapter.FragMainAdapter;
import nncb.kimxu.vck.atys.AtyAddNote;
import nncb.kimxu.vck.model.Note;
import nncb.kimxu.vck.utils.GlobalUtil;
import nncb.kimxu.vck.utils.Klog;

public class FragMain extends Fragment {


    private static final String ARG_POSITION = "position";

    private int mPosition;
    private final int[] drawables = new int[2];
    private CustomUltimateRecyclerview mRecyclerView;
    private FragMainAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    private AddFloatingActionButton addButton;

    private int editPosition;

    private TextView loadingText;
    private LinearLayout llLoading;
    private LinearLayout llFragMain;
    public static FragMain newInstance(int position) {
        FragMain f = new FragMain();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPosition = getArguments().getInt(ARG_POSITION);
        drawables[0] = getActivity().getResources().getColor(R.color.background_pink);
        drawables[1] = getActivity().getResources().getColor(R.color.background_blue);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main, container, false);
        addButton = (AddFloatingActionButton) view.findViewById(R.id.add_note);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AtyAddNote.launch(getActivity(), mPosition,null);

            }
        });
        llLoading= (LinearLayout) view.findViewById(R.id.loading);
        llFragMain= (LinearLayout) view.findViewById(R.id.ll_frag_main);
        llFragMain.setBackgroundColor(drawables[mPosition]);
        setUltimateRecyclerView(view);
        return view;
    }

    private void setUltimateRecyclerView(View view) {
        mRecyclerView = (CustomUltimateRecyclerview) view.findViewById(R.id.ultimate_recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(false);
        mAdapter = new FragMainAdapter(getActivity(), mPosition);
        getDatas(true);

        linearLayoutManager = new ScrollSmoothLineaerLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false, 300);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.enableLoadmore();
        View loadingView =LayoutInflater.from(getActivity())
                .inflate(R.layout.custom_bottom_progressbar, null);
        loadingText = (TextView) loadingView.findViewById(R.id.load_text);
        mAdapter.setCustomLoadMoreView(loadingView);

        mRecyclerView.setRecylerViewBackgroundColor(drawables[mPosition]);
        //加载更多数据
        mRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                loadingText.setText("没有新数据了");
            }
        });
        ItemTouchListenerAdapter itemTouchListenerAdapter = new ItemTouchListenerAdapter(mRecyclerView.mRecyclerView,
                new ItemTouchListenerAdapter.RecyclerViewOnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View clickedView, int position) {
                        //加载中不能点击
                        if(mAdapter.getItemCount()-1>position) {
                            editPosition = position;
                            AtyAddNote.launch(getActivity(), mPosition, mAdapter.getItem(position));
                        }
                    }

                    @Override
                    public void onItemLongClick(RecyclerView parent, View clickedView, final int position) {
                        GlobalUtil.showSubmitDialog(getActivity(), "确认删除吗？",
                                new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        mAdapter.remove(position);
                                        sweetAlertDialog.dismiss();
                                    }
                                }, new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                });


                    }
                });
        mRecyclerView.mRecyclerView.addOnItemTouchListener(itemTouchListenerAdapter);

        refreshingString();

    }

    private void refreshingString() {
        mRecyclerView.setCustomSwipeToRefresh();
        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(getActivity());
        storeHouseHeader.initWithString("Nuo NoteBook");
        storeHouseHeader.setTextColor(getActivity().getResources().getColor(R.color.white));
        storeHouseHeader.setBackgroundColor(drawables[mPosition]);
        mRecyclerView.mPtrFrameLayout.setBackgroundColor(drawables[mPosition]);
        mRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        mRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        mRecyclerView.mPtrFrameLayout.autoRefresh(false);
        mRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                return canbePullDown;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                getDatas(false);
            }
        });

    }

    private void getDatas(final boolean isFirst){
        BmobQuery<Note> query = new BmobQuery<>();
        query.order("-createdAt");
        String userId =BmobUser.getCurrentUser(getActivity()).getObjectId();
        query.addWhereEqualTo("userId", userId);
        if (!isFirst&&mAdapter.getItemCount()>1) {
            String createdAt =mAdapter.getItem(0).getObjectId();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = sdf.parse(createdAt);
                query.addWhereGreaterThan("createdAt", new BmobDate(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        query.setLimit(20);
        query.findObjects(getActivity(), new FindListener<Note>() {
            @Override
            public void onSuccess(List<Note> object) {
                Klog.d("查询成功：共" + object.size() + "条数据。");
                if (isFirst) {
                    mAdapter.addAll(object);
                } else {
                    mRecyclerView.mPtrFrameLayout.refreshComplete();
                    if (object.size() == 1 | object.size() == 0) {
                        GlobalUtil.showToast(getActivity(), "没有新笔记~");
                        return;
                    }
                    //这里查询出来的数据比包含那一条数据应该删除掉
                    object.remove(object.size() - 1);
                    mAdapter.addAll(object);
                    linearLayoutManager.scrollToPosition(0);
                    mRecyclerView.mPtrFrameLayout.refreshComplete();
                }
                llLoading.setVisibility(View.GONE);
            }

            @Override
            public void onError(int code, String msg) {
                Klog.d("error");
                mRecyclerView.mPtrFrameLayout.refreshComplete();
                llLoading.setVisibility(View.GONE);
            }
        });
    }

    public FragMainAdapter getAdapter(){
        return mAdapter;
    }
    public LinearLayoutManager getLinearLayoutManager(){
        return linearLayoutManager;
    }
    public int getEditPosition() {
        return editPosition;
    }
}