package com.example.user.megadatahack;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Chat extends AppCompatActivity {

    String id_chat = "";
    String session = "";

    AlertDialog.Builder builderSingle;
    ArrayList<ChatMessage.Msg> messagesList;
    ChatListAdapter adapter;
    EditText msg;
    int count = 0;
    ImageView give_nn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id_chat = getIntent().getStringExtra("id_chat");
        session = getIntent().getStringExtra("session");
        toolbar.setTitle("Чат " + id_chat);

        ListView archive_list = (ListView) findViewById(R.id.msgs);
        messagesList = new ArrayList<ChatMessage.Msg>();
        adapter = new ChatListAdapter(this, messagesList);
        archive_list.setAdapter(adapter);

        give_nn = (ImageView) findViewById(R.id.give_nn);
        ImageView send = (ImageView) findViewById(R.id.send_to_server);
        msg = (EditText) findViewById(R.id.my_msg);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://90.189.132.25:80/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                msgSelect msgServer = retrofit.create(msgSelect.class);
                String toServer = "{\"id_client\": \"" + id_chat + "\", \"Message\": \"" + msg.getText().toString()+ "\"}";
                msgServer.send(toServer).enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        msg.setText("");
                    }
                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) { }
                });

                Retrofit retrofitDataSet = new Retrofit.Builder()
                        .baseUrl("http://90.189.132.25:80/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                msgSelect msgDatasetServer = retrofitDataSet.create(msgSelect.class);
                String toDataSet = "{\"Message\": \"" + messagesList.get(messagesList.size() - 1).getText().toString() + "\", \"Answer\": \"" + msg.getText().toString() + "\"}";
                msgDatasetServer.sendToDataset(toDataSet).enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) { }
                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) { }
                });
            }
        });


    }

    private Disposable process;
    String[] answers;
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
                    if (messagesList.size() != msgList.getMsgs().size()) {
                        messagesList.clear();
                        messagesList.addAll(msgList.getMsgs());
                        adapter.notifyDataSetChanged();
                    }
                    if (count != msgList.getMsgs().size()) {
                        count = msgList.getMsgs().size();
                        if (msgList.getMsgs().get(msgList.getMsgs().size() - 1).getSender().equals("Client")){
                            Log.d("MegaDataHack_log", msgList.getMsgs().get(msgList.getMsgs().size() - 1).getText());
                            Retrofit nn = new Retrofit.Builder()
                                                .baseUrl("http://90.189.132.25:80/")
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                            interFACE nnServer = nn.create(interFACE.class);
                            String msg = "{\"Message\":\"" + msgList.getMsgs().get(msgList.getMsgs().size() - 1).getText() + "\"}";
                            nnServer.classfication(msg).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.d("MegaDataHack_log", response.body());
                                    try {
                                        JSONObject json = new JSONObject(response.body());
                                        answers = json.get("Answer").toString().replace("[\"","").replace("\"]","").split("\",\"");
                                        builderSingle = new AlertDialog.Builder(Chat.this);
                                        builderSingle.setTitle("Варианты ответов");

                                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Chat.this, R.layout.dialog_text);
                                        answers[0] = answers[0].replace("\n","");
                                        answers[1] = answers[1].replace("\n","");
                                        answers[2] = answers[2].replace("\n","");
                                        answers[3] = answers[3].replace("\n","");
                                        arrayAdapter.add(answers[0]);
                                        arrayAdapter.add(answers[1]);
                                        arrayAdapter.add(answers[2]);
                                        arrayAdapter.add(answers[3]);
                                        arrayAdapter.add("Свой ответ");

                                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String strName = answers[which];
                                                Log.d("MegaDataHack_log", strName);
                                                if (which <= 3) {
                                                    Retrofit retrofit = new Retrofit.Builder()
                                                            .baseUrl("http://90.189.132.25:80/") //Базовая часть адреса
                                                            .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                                                            .build();
                                                    msgSelect msgServer = retrofit.create(msgSelect.class); //Создаем объект, при помощи которого будем выполнять запросы
                                                    String toServer = "{\"id_client\": \"" + id_chat + "\", \"Message\": \"" + strName + "\"}";
                                                    msgServer.send(toServer).enqueue(new Callback<JSONObject>() {
                                                        @Override
                                                        public void onResponse(Call<JSONObject> call, Response<JSONObject> response) { }
                                                        @Override
                                                        public void onFailure(Call<JSONObject> call, Throwable t) { }
                                                    });
                                                }
                                                dialog.cancel();

                                            }
                                        });
                                        give_nn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                builderSingle.show();
                                            }
                                        });

                                    } catch (JSONException e) {
                                        Log.e("MegaDataHack_log", e.getMessage());
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.e("MegaDataHack_log", t.toString());
                                }
                            });
                        }
                    }
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
