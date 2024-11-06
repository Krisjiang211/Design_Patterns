package com.jiang.MakeClassPattern.BuilderPattern;

public class Client {
    public static void main(String[] args) {
        Server tomcat = new Server.Builder()
                .ip("192.168.1.1")
                .port(8080)
                .size(4981)
                .serverName("tomcat")
                .build();
    }
}
