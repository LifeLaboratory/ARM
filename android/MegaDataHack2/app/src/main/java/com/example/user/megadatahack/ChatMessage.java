package com.example.user.megadatahack;

import java.util.List;

public class ChatMessage {
    private String Answer;
    class Msg {
        private String id_chat;
        private String Data;
        private String id_client;
        private String Text;
        private String Sender;
        private String Name;

        public String getName() {
            return Name;
        }

        public String getSender() {
            return Sender;
        }

        public String getDate() {
            return Data;
        }

        public String getId_chat() {
            return id_chat;
        }

        public String getId_client() {
            return id_client;
        }

        public String getText() {
            return Text;
        }
    }

    List<Msg> Data;

    public List<Msg> getMsgs() {
        return Data;
    }

    public String getAnswer() {
        return Answer;
    }

}
