package com.tdl.dubbomesh.agent.provider.client;

import com.tdl.dubbomesh.agent.provider.server.ProviderAgentHandler;
import com.tdl.dubbomesh.protocol.dubbo.DubboRpcResponse;
import com.tdl.dubbomesh.protocol.pb.DubboMeshProto;
import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
* @Description:    
* @Author:         ceaserWang
* @CreateDate:     2019/3/17 16:30
* @UpdateUser:     yc
* @UpdateDate:     2019/3/17 16:30
* @UpdateRemark:   
* @Version:        1.0
*/
public class DubboRpcHandler extends SimpleChannelInboundHandler<Object> {

    static final Logger logger = LoggerFactory.getLogger(DubboRpcHandler.class);

    public DubboRpcHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof List){
            List<DubboRpcResponse> responses = (List<DubboRpcResponse>) msg;
            for (DubboRpcResponse response : responses) {
                process(response);
            }
        }else if(msg instanceof DubboRpcResponse){
            process((DubboRpcResponse) msg);
        }

    }

    private void process(DubboRpcResponse msg) {
        Promise<DubboMeshProto.AgentResponse> promise = ProviderAgentHandler.promiseHolder.get().remove(msg.getRequestId());
        if (promise != null) {
            promise.trySuccess(messageToMessage(msg));
        }
    }

    private DubboMeshProto.AgentResponse messageToMessage(DubboRpcResponse dubboRpcResponse) {
        return DubboMeshProto.AgentResponse.newBuilder()
                .setRequestId(dubboRpcResponse.getRequestId())
                .setHash(ByteString.copyFrom(dubboRpcResponse.getBytes()))
                .build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.channel().close();
    }

}
