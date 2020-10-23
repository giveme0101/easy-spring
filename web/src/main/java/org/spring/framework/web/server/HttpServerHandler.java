package org.spring.framework.web.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.AsciiString;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.framework.web.exception.RequestMethodNotSupport;
import org.spring.framework.web.exception.UrlNotFoundException;
import org.spring.framework.web.factory.FullHttpResponseFactory;
import org.spring.framework.web.factory.RequestHandlerResolver;
import org.spring.framework.web.handler.RequestHandler;
import org.spring.framework.web.util.UrlUtil;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name HttpServerHandler
 * @Date 2020/10/23 16:51
 */
@Slf4j
@AllArgsConstructor
@ChannelHandler.Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private RequestHandlerResolver requestHandlerResolver;

    private static final String FAVICON_ICO = "/favicon.ico";
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {

        log.debug("request info:{}", fullHttpRequest);
        String uri = fullHttpRequest.uri();
        if (uri.equals(FAVICON_ICO)) {
            return;
        }

        FullHttpResponse fullHttpResponse;
        try {
            RequestHandler requestHandler = requestHandlerResolver.getHandler(fullHttpRequest.method());
            fullHttpResponse = requestHandler.handle(fullHttpRequest);
        } catch (UrlNotFoundException ex) {
            log.warn(ex.getMessage());
            String requestPath = UrlUtil.getRequestPath(fullHttpRequest.uri());
            fullHttpResponse = FullHttpResponseFactory.getErrorResponse(requestPath, ex.getMessage(), HttpResponseStatus.NOT_FOUND);
        } catch (RequestMethodNotSupport ex) {
            log.warn(ex.getMessage());
            String requestPath = UrlUtil.getRequestPath(fullHttpRequest.uri());
            fullHttpResponse = FullHttpResponseFactory.getErrorResponse(requestPath, ex.getMessage(), HttpResponseStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            String requestPath = UrlUtil.getRequestPath(fullHttpRequest.uri());
            fullHttpResponse = FullHttpResponseFactory.getErrorResponse(requestPath, e.toString(), HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }

        boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
        if (!keepAlive) {
            ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
        } else {
            fullHttpResponse.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(fullHttpResponse);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

}
