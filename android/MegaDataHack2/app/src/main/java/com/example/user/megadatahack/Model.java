package com.example.user.megadatahack;

import java.util.List;

public class Model {
    private data Data;
    private String Type;
    public String getType(){
        return Type;
    }
    public void setType(String type){
        this.Type = type;
    }
    public data getData(){
        return Data;
    };
    public void setData(data data) {
        this.Data = data;
    }
    public class data{
        private String Login;
        private String Password;
        private String Name;
        private String Names;
        private String Session;
        private String Description;
        private String Status;
        private int id_user;

        public int getId_user() { return id_user; }
        public String getStatus() { return Status; }
        public String getNames() { return Names; }
        public String getLogin(){
            return Login;
        }
        public void setLogin(String login) {
            this.Login = login;
        }
        public String getPassword(){
            return Password;
        }
        public void setPassword(String pass) {
            this.Password = pass;
        }
        public String getName(){
            return Name;
        }
        public void setName(String name) {
            this.Name = name;
        }
        public String getsession(){
            return Session;
        }
        public void setsession(String session) {
            this.Session = session;
        }
        public String getDescription(){
            return Description;
        }
        public void setDescription(String description) {
            this.Description = description;
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
