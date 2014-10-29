package org.openforis.collect.js.web.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class JsonQueryHandler extends RequestHandler {
    protected final void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeJson(json(req, resp), resp);
    }

    protected abstract String json(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
