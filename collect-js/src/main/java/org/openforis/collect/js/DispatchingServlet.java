package org.openforis.collect.js;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class DispatchingServlet extends HttpServlet {
    private final Map<String, RequestHandler> postHandlers = new HashMap<String, RequestHandler>();
    private final Map<String, RequestHandler> getHandlers = new HashMap<String, RequestHandler>();

    public final void init() throws ServletException {
        initHandlers();
    }

    protected abstract void initHandlers();

    protected final void post(String url, RequestHandler handler) {
        postHandlers.put(url, handler);
    }

    protected final void get(String url, RequestHandler handler) {
        getHandlers.put(url, handler);
    }

    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp, postHandlers);
    }

    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        handleRequest(req, resp, getHandlers);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp, Map<String, RequestHandler> handlers) throws IOException, ServletException {RequestHandler handler = handlers.get(req.getPathInfo());
        if (handler == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "JSON resource not found.");
            return;
        }
        resp.setCharacterEncoding("UTF-8");
        handler.handle(req, resp);
    }

    protected abstract class RequestHandler {
        abstract void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    }
}
