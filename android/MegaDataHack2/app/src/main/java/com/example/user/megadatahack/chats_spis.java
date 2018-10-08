package com.example.user.megadatahack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import retrofit2.http.Query;

public class chats_spis extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> dialogs, archive_dialogs;
    ArrayAdapter<String> adapter, archive_adapter;

    TextView name;
    TextView status;

    String session;
    String chatActual = "";
    String strName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_spis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = getIntent().getStringExtra("session");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ListView list_dialogs = (ListView) findViewById(R.id.actual_dialogs);
        dialogs = new ArrayList<String>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, dialogs);
        list_dialogs.setAdapter(adapter);
        list_dialogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chatActual = dialogs.get(i);
                Log.d("MegaDataHack_log", chatActual);
                Intent intent = new Intent(chats_spis.this, Chat.class);
                intent.putExtra("id_chat", chatActual);
                intent.putExtra("session", session);
                startActivity(intent);
            }
        });

        ListView archive_list = (ListView) findViewById(R.id.archive_list);
        archive_dialogs = new ArrayList<String>();
        archive_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, archive_dialogs);
        archive_list.setAdapter(archive_adapter);
        archive_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chatActual = archive_dialogs.get(i);
                Log.d("MegaDataHack_log", chatActual);
                Intent intent = new Intent(chats_spis.this, ChatArchive.class);
                intent.putExtra("id_chat", chatActual);
                intent.putExtra("session", session);
                startActivity(intent);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.archive);
        navigationView.setNavigationItemSelectedListener(this);

        name = (TextView) findViewById(R.id.my_name);
        status = (TextView) findViewById(R.id.my_status);

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(chats_spis.this);
                builderSingle.setTitle("Статус");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(chats_spis.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Работаю");
                arrayAdapter.add("Отошел");
                arrayAdapter.add("Не работаю");
                arrayAdapter.add("Обед");

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: strName = "Работаю"; break;
                            case 1: strName = "Отошел"; break;
                            case 2: strName = "Не работаю"; break;
                            case 3: strName = "Обед"; break;
                        }
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://90.189.132.25:80/") //Базовая часть адреса
                                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                                .build();
                        interFACE InterFACE = retrofit.create(interFACE.class); //Создаем объект, при помощи которого будем выполнять запросы
                        String forStatus = "{\"Session\":\"" + session + "\", \"Status\":\"" + strName + "\"}";
                        Log.d("MegaDataHack_log", forStatus);
                        InterFACE.put_status(forStatus).enqueue(new Callback<Model>() {
                            @Override
                            public void onResponse(Call<Model> call, Response<Model> response) {
                                status.setText(strName);
                            }
                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {
                                Log.d("MegaDataHack_log", t.toString());
                                status.setText(strName);
                            }
                        });
                        dialog.dismiss();

                    }
                });
                builderSingle.show();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://90.189.132.25:80/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        interFACE InterFACE = retrofit.create(interFACE.class); //Создаем объект, при помощи которого будем выполнять запросы
        InterFACE.getSession(session).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                name.setText(response.body().getData().getNames());
                status.setText(response.body().getData().getStatus());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("MegaDataHack_log", t.toString());
            }
        });

//        ListView listView = (ListView)findViewById(R.id.statistic);
//        Retrofit retrofitStatistic = new Retrofit.Builder()
//                .baseUrl("http://90.189.132.25:80/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        interFACE statistic = retrofit.create(interFACE.class);
//        statistic.getStatistic("1").enqueue(new Callback<Statistic>() {
//            @Override
//            public void onResponse(Call<Statistic> call, Response<Statistic> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Statistic> call, Throwable t) {
//                Log.e("MegaDataHack_log", t.toString());
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START) || drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.START);
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    private Disposable process, processArch;
    @Override
    protected void onStart() {
        super.onStart();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://90.189.132.25:80/").
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).build();
        msgSelect messages = retrofit.create(msgSelect.class);
        Observable<ChatMessage> messageSelect = messages.get(session);
        process = messageSelect.subscribeOn(Schedulers.newThread())
                .repeatWhen(a -> a.flatMap(n -> Observable.timer(1, TimeUnit.SECONDS))) // выгружать сообщения каждую 1 секунду
                .retryWhen(a -> a.flatMap(n -> Observable.timer(1, TimeUnit.SECONDS))) // перепроверять
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msgList -> {
                    Log.d("MegadataHack_log", String.valueOf(msgList.getMsgs().size()));
                    for (ChatMessage.Msg msg : msgList.getMsgs()) {
                        if (dialogs.indexOf(msg.getId_chat()) == -1) {
                            dialogs.add(msg.getId_chat());
                        }
//                        if (dialogs.indexOf(msg.getName()) == -1) {
//                            dialogs.add(msg.getName());
//                        }
                    }
                    adapter.notifyDataSetChanged();
                }, e -> {
                    Log.e("MegaDataHack_log", e.toString().concat(" <- error from ChatActivity"));
                });

        Retrofit retrofitArch = new Retrofit.Builder().baseUrl("http://90.189.132.25:80/").
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).build();
        msgSelect messagesArch = retrofitArch.create(msgSelect.class);
        Observable<ChatMessage> messageArchSelect = messagesArch.getArchive(session, "1");
        processArch = messageArchSelect.subscribeOn(Schedulers.newThread())
                .repeatWhen(a -> a.flatMap(n -> Observable.timer(1, TimeUnit.SECONDS))) // выгружать сообщения каждую 1 секунду
                .retryWhen(a -> a.flatMap(n -> Observable.timer(1, TimeUnit.SECONDS))) // перепроверять
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msgList -> {
                    Log.d("MegadataHack_log1", String.valueOf(msgList.getMsgs().size()));
                    for (ChatMessage.Msg msg : msgList.getMsgs()) {
                        if (archive_dialogs.indexOf(msg.getId_chat()) == -1) {
                            archive_dialogs.add(msg.getId_chat());
                        }
                    }
                    archive_adapter.notifyDataSetChanged();
                }, e -> {
                    Log.e("MegaDataHack_log1", e.toString().concat(" <- error from ChatActivity"));
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        process.dispose();
        processArch.dispose();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
