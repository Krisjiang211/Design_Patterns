package com.jiang.MakeClassPattern.BuilderPattern;


//由于构造函数太多,无法一一写,那么我们就建造者模式
public class Server {

    private String ip;
    private int port;
    private long size;
    private String serverName;

    public static final class Builder{
        private Server server=new Server();

        public Builder ip(String ip){
            server.setIp(ip);
            return this;
        }
        public Builder port(int port){
            server.setPort(port);
            return this;
        }
        public Builder size(long size){
            server.setSize(size);
            return this;
        }
        public Builder serverName(String serverName){
            server.setServerName(serverName);
            return this;
        }

        public Server build(){
            return this.server;
        }

    }
    public Server(){};

    //setter方法
    private void setIp(String ip) {
        this.ip = ip;
    }
    private void setPort(int port) {
        this.port = port;
    }
    private void setSize(long size) {
        this.size = size;
    }
    private void setServerName(String serverName) {
        this.serverName = serverName;
    }


}


