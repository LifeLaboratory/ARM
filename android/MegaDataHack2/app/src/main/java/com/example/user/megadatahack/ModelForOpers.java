package com.example.user.megadatahack;

import java.util.List;

public class ModelForOpers {
    private List<data> Data;
    public List<data> getData(){
        return Data;
    };
    public void setData(List<data> data) {
        this.Data = data;
    }
    public class data{
        private String Login;
        private String Status;
        private String Name;
        private int id_user;

        public String getLogin(){
            return Login;
        }
        public void setLogin(String login) {
            this.Login = login;
        }
        public String getName(){
            return Name;
        }
        public void setName(String name) {
            this.Name = name;
        }

        public String getStatus() {
            return Status;
        }

        public int getId_user() {
            return id_user;
        }
    }
    private String Answer;

    public String getAnswer() {
        return Answer;
    }
    public void setAnswer(String Answer){
        this.Answer = Answer;
    }
}
