package com.kangjun.niodemo;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *  NIO服务端
 */
public class NIOServer {
    //默认监听端口号
    private int port = 8080;
    //服务地址
    private InetSocketAddress address = null;
    //选择器 大厅大管家
    private Selector selector;
    //构造方法 初始化
    public NIOServer(int port){
        try {
            this.port = port;
            address = new InetSocketAddress(this.port);
            //要想富，先修路
            //高速公路修起来
            ServerSocketChannel server = ServerSocketChannel.open();
            //绑定服务端地址
            server.bind(address);
            //默认为阻塞的，手动设置非阻塞
            server.configureBlocking(false);
            //大管家开始工作，开门营业
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器准备就绪，监听端口是：" + this.port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //监听客户端的业务方法
    public void listen(){
        try {
            //轮训
            while (true){
                //有多少人在服务大厅排队
                int wait = this.selector.select();
                if(wait == 0) continue;
                Set<SelectionKey> keys = this.selector.selectedKeys();
                //迭代器
                Iterator<SelectionKey> i = keys.iterator();
                while (i.hasNext()){
                    SelectionKey key = i.next();
                    //处理客户端业务请求
                    process(key);
                    i.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void process(SelectionKey key) throws Exception{
        //分配缓存区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        if(key.isAcceptable()){
            ServerSocketChannel server = (ServerSocketChannel)key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector,SelectionKey.OP_READ);
        }else if(key.isReadable()){
            SocketChannel client = (SocketChannel)key.channel();
            //写到缓存区
            int length = client.read(buffer);
            if(length > 0){
                buffer.flip();
                String content = new String(buffer.array(),0,length);
                System.out.println("服务端收到消息:");
                System.out.println(content);
                client.register(selector,SelectionKey.OP_WRITE);
            }
            buffer.clear();
        }else if(key.isWritable()){
            SocketChannel client = (SocketChannel)key.channel();
            client.write(buffer.wrap("Hello World".getBytes()));
            client.close();
        }
    }

    public static void main(String[] args) {
        new NIOServer(8080).listen();
    }


}
