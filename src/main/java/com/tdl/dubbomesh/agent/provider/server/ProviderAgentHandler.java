package com.tdl.dubbomesh.agent.provider.server;

import com.tdl.dubbomesh.agent.provider.client.DubboClient;
import com.tdl.dubbomesh.protocol.dubbo.DubboRpcRequest;
import com.tdl.dubbomesh.protocol.dubbo.RpcInvocation;
import com.tdl.dubbomesh.protocol.pb.DubboMeshProto;
import com.tdl.dubbomesh.transport.Client;
import com.tdl.dubbomesh.util.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.collection.LongObjectHashMap;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
* @Description:    
* @Author:         ceaserWang
* @CreateDate:     2019/3/17 16:31
* @UpdateUser:     yc
* @UpdateDate:     2019/3/17 16:31
* @UpdateRemark:   
* @Version:        1.0
*/
public class ProviderAgentHandler extends SimpleChannelInboundHandler<DubboMeshProto.AgentRequest> {

    private Logger logger = LoggerFactory.getLogger(ProviderAgentHandler.class);

    public static FastThreadLocal<LongObjectHashMap<Promise<DubboMeshProto.AgentResponse>>> promiseHolder = new FastThreadLocal<LongObjectHashMap<Promise<DubboMeshProto.AgentResponse>>>() {
        @Override
        protected LongObjectHashMap<Promise<DubboMeshProto.AgentResponse>> initialValue() throws Exception {
            return new LongObjectHashMap<>();
        }
    };
    private static FastThreadLocal<Client> dubboClientHolder = new FastThreadLocal<>();

    public ProviderAgentHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        if (dubboClientHolder.get() == null) {
            DubboClient dubboClient = new DubboClient(ctx.channel().eventLoop());
            dubboClient.init();
            dubboClientHolder.set(dubboClient);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DubboMeshProto.AgentRequest msg) throws Exception {
        Promise<DubboMeshProto.AgentResponse> promise = new DefaultPromise<>(ctx.executor());
        promise.addListener(future -> {
            DubboMeshProto.AgentResponse agentResponse = (DubboMeshProto.AgentResponse) future.get();
            ctx.writeAndFlush(agentResponse);

        });
        promiseHolder.get().put(msg.getRequestId(), promise);
        dubboClientHolder.get().getMeshChannel().getChannel().writeAndFlush(messageToMessage(msg));
    }

    private DubboRpcRequest messageToMessage(DubboMeshProto.AgentRequest agentRequest) {
        RpcInvocation invocation = new RpcInvocation();
        invocation.setMethodName(agentRequest.getMethod());
        invocation.setAttachment("path", agentRequest.getInterfaceName());
        invocation.setParameterTypes(agentRequest.getParameterTypesString());
//        invocation.setMethodName("hash");
//        invocation.setAttachment("path", "com.alibaba.dubbo.performance.demo.provider.IHelloService");
//        invocation.setParameterTypes("Ljava/lang/String;");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
        try {
            JsonUtils.writeObject(agentRequest.getParameter(), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        invocation.setArguments(out.toByteArray());
        DubboRpcRequest dubboRpcRequest = new DubboRpcRequest();
        dubboRpcRequest.setId(agentRequest.getRequestId());
        dubboRpcRequest.setVersion("2.0.0");
        dubboRpcRequest.setTwoWay(true);
        dubboRpcRequest.setData(invocation);
        return dubboRpcRequest;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.channel().close();
    }

}
