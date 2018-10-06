package com.example.user.megadatahack;

public class Model {
    private data Data;
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
        private String session;
        private String Description;
        private String Type;

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
            return session;
        }
        public void setsession(String session) {
            this.session = session;
        }
        public String getDescription(){
            return Description;
        }
        public void setDescription(String description) {
            this.Description = description;
        }
        public String getType(){
            return Type;
        }
        public void setType(String type){
            this.Type = type;
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
