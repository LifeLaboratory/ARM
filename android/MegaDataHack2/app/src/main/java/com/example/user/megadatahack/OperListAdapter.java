package com.example.user.megadatahack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class OperListAdapter extends BaseAdapter {

    List<ModelForOpers.data> opers;
    Context context;
    LayoutInflater lInflater;
    String session;

    OperListAdapter(Context context, List<ModelForOpers.data> lst, String session){
        this.context = context;
        this.opers = lst;
        this.session = session;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return opers.size();
    }

    @Override
    public Object getItem(int i) {
        return opers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_adapter, viewGroup, false);
        }

        TextView nameOper = (TextView) view.findViewById(R.id.name_oper);
        TextView statusOper = (TextView) view.findViewById(R.id.status_oper);
        nameOper.setText(opers.get(i).getName());
        statusOper.setText(opers.get(i).getStatus());

        ImageView change = (ImageView) view.findViewById(R.id.change_oper);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChangeOper.class);
                intent.putExtra("name", opers.get(i).getName());
                intent.putExtra("login", opers.get(i).getLogin());
                intent.putExtra("id_user", opers.get(i).getId_user());
                intent.putExtra("session", session);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
