package com.example.user.megadatahack;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {

    Context context;
    List<ChatMessage.Msg> messages;
    LayoutInflater lInflater;

    ChatListAdapter(Context context, List<ChatMessage.Msg> lst){
        this.context = context;
        this.messages = lst;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
//        Log.d("MegaDataHack_log_type", messages.get(i).getSender() + " " + messages.get(i).getText());
        if (messages.get(i).getSender().equals("Client"))
            view = lInflater.inflate(R.layout.chat_my_adapter, viewGroup, false);
        else
            view = lInflater.inflate(R.layout.chat_you_adapter, viewGroup, false);
        TextView msg = (TextView) view.findViewById(R.id.msg);
        msg.setText(messages.get(i).getDate() + ":\n" + messages.get(i).getText());
        return view;
    }
}
