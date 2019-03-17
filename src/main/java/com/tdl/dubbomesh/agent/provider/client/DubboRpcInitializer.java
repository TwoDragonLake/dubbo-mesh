package com.tdl.dubbomesh.agent.provider.client;

import com.tdl.dubbomesh.protocol.dubbo.DubboRpcDecoder;
import com.tdl.dubbomesh.protocol.dubbo.DubboRpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
* @Description:    
* @Author:         ceaserWang
* @CreateDate:     2019/3/17 16:31
* @UpdateUser:     yc
* @UpdateDate:     2019/3/17 16:31
* @UpdateRemark:   
* @Version:        1.0
*/
public class DubboRpcInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new DubboRpcEncoder())
                .addLast(new DubboRpcDecoder())
//                .addLast(new DubboRpcBatchDecoder())
                .addLast(new DubboRpcHandler());
    }
}
