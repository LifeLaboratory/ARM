package com.example.user.megadatahack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatArchive extends AppCompatActivity {

    String id_chat = "";
    String session = "";

    ArrayList<ChatMessage.Msg> messagesList;
    ChatListAdapter adapter;
    EditText msg;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_archive);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id_chat = getIntent().getStringExtra("id_chat");
        session = getIntent().getStringExtra("session");
        toolbar.setTitle("Чат " + id_chat);

        ListView archive_list = (ListView) findViewById(R.id.msgs);
        messagesList = new ArrayList<ChatMessage.Msg>();
        adapter = new ChatListAdapter(this, messagesList);
        archive_list.setAdapter(adapter);
    }

    private Disposable process;
    @Override
    protected void onStart() {
        super.onStart();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://90.189.132.25:80/").
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).build();
        msgSelect messages = retrofit.create(msgSelect.class);
        Observable<ChatMessage> messageSelect = messages.getAllChat(id_chat, session);
        process = messageSelect.subscribeOn(Schedulers.newThread())
                .repeatWhen(a -> a.flatMap(n -> Observable.timer(1, TimeUnit.SECONDS))) // выгружать сообщения каждую 1 секунду
                .retryWhen(a -> a.flatMap(n -> Observable.timer(1, TimeUnit.SECONDS))) // перепроверять
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msgList -> {
                    Log.d("MegadataHack_log", String.valueOf(msgList.getMsgs().size()));
                    messagesList.clear();
                    messagesList.addAll(msgList.getMsgs());
                    if (count != msgList.getMsgs().size()) {
                        count = msgList.getMsgs().size();
                        if (msgList.getMsgs().get(msgList.getMsgs().size() - 1).getSender().equals("Client")) {
                            Log.d("MegaDataHack_log", msgList.getMsgs().get(msgList.getMsgs().size() - 1).getText());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }, e -> {
                    Log.e("MegaDataHack_log", e.toString().concat(" <- error from ChatActivity"));
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        process.dispose();
    }

}
