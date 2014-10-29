package org.openforis.collect.js.web.util;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class StreamingRequestHandler extends RequestHandler {
    private final int timeout;

    public StreamingRequestHandler(int timeout) {
        this.timeout = timeout;
    }

    protected final void handle(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("UTF-8");
        final AsyncContext ctx = req.startAsync(req, resp);
        ctx.setTimeout(timeout);
        StreamingRequestHandler.this.subscribe(req, resp, ctx);
    }

    protected abstract void subscribe(HttpServletRequest req, HttpServletResponse resp, AsyncContext ctx) throws IOException;

    public static class DefaultAsyncListener implements AsyncListener {
        public void onStartAsync(AsyncEvent event) throws IOException {
        }

        public void onComplete(AsyncEvent event) throws IOException {
        }

        public void onTimeout(AsyncEvent event) throws IOException {
        }

        public void onError(AsyncEvent event) throws IOException {
        }
    }
}
