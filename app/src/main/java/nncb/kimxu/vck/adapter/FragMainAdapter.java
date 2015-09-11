package nncb.kimxu.vck.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

import nncb.kimxu.vck.R;
import nncb.kimxu.vck.model.Note;


public class FragMainAdapter extends UltimateViewAdapter<FragMainAdapter.SimpleAdapterViewHolder> {

    private List<Note> datas;
    private int mPosition;
    private Activity mActivity;
    public FragMainAdapter(Activity mActivity, int position) {
        this.mPosition=position;
        this.mActivity=mActivity;
        datas=new ArrayList<>();
    }

    public void addAll(List<Note> datas){
        this.datas.addAll(0,datas);
        notifyDataSetChanged();
    }
    public List<Note> getDatas(){
        return datas;

    }
    @Override
    public SimpleAdapterViewHolder getViewHolder(View view) {
        return  new SimpleAdapterViewHolder(view, false);
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_frag_main, parent, false);
        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
        return vh;
    }

    @Override
    public int getAdapterItemCount() {
        return datas.size();
    }

    public void insert(Note note, int position) {
        //notifyItemInserted(position);
        insert(datas, note, position);

    }

    public void remove(int position) {
        //notifyItemRemoved(position);
        remove(datas, position);
    }
    public void clear() {
        clear(datas);
    }
    @Override
    public long generateHeaderId(int position) {
        if (getItem(position).getObjectId() !=null)
            return getItem(position).getObjectId().charAt(0);
        else return -1;
    }

    @Override
    public void onBindViewHolder(SimpleAdapterViewHolder holder, int position) {

        if (position < getItemCount() && (customHeaderView != null ? position <= datas.size() : position < datas.size()) && (customHeaderView == null || position > 0)) {
            Note note = datas.get(position);
            String week =note.getTime().split(" ")[2];
            holder.tVWeek.setText(week);
            int dateLength =note.getTime().length()-week.length();
            holder.tVDate.setText(note.getTime().substring(0,dateLength));
            holder.tVcontent.setText(note.getContent());
        }


    }
    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {

        return null;
    }
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

    }
    @Override
    public void toggleSelection(int pos) {
        super.toggleSelection(pos);
    }

    @Override
    public void setSelected(int pos) {
        super.setSelected(pos);
    }

    @Override
    public void clearSelection(int pos) {
        super.clearSelection(pos);
    }

    public Note getItem(int position) {

       return datas.get(position);
    }

    public void setItem(Note note,int position) {
        datas.get(position).setContent(note.getContent());
        notifyDataSetChanged();
    }

    public class SimpleAdapterViewHolder extends UltimateRecyclerviewViewHolder {
        TextView tVcontent;
        TextView tVDate;
        TextView tVWeek;
        RelativeLayout rLcontent;


        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);

            if (isItem) {

                tVcontent = (TextView) itemView.findViewById(
                        R.id.content);

                tVDate = (TextView) itemView.findViewById(
                        R.id.date);
                tVWeek = (TextView) itemView.findViewById(
                        R.id.week);
                rLcontent= (RelativeLayout) itemView.findViewById(R.id.content_layout);
                if (mPosition==1){
                    rLcontent.setBackgroundColor(mActivity.getResources().getColor(R.color.background_blue));
                    tVDate.setBackgroundColor(mActivity.getResources().getColor(R.color.background_blue_primary));
                    tVWeek.setBackgroundColor(mActivity.getResources().getColor(R.color.background_blue_primary));
                }
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }



}
