package com.example.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<Note> list;
    private LayoutInflater layoutInflater;
    public MyAdapter(List<Note> list,Context context){
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            view = layoutInflater.inflate(R.layout.item_layout,null,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Note note = (Note) getItem(i);
        viewHolder.content.setText(note.getContent());
        viewHolder.time.setText(note.getTime());
        return view;
    }
    class ViewHolder{
        TextView content,time;
        public  ViewHolder(View view){
            content = view.findViewById(R.id.content);
            time = view.findViewById(R.id.time);
        }
    }
}
