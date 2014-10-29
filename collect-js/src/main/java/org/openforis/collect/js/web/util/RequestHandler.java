package org.openforis.collect.js.web.util;

import org.apache.commons.io.IOUtils;
import org.openforis.collect.api.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class RequestHandler {
    protected abstract void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    protected final String bodyAsString(HttpServletRequest req) throws IOException {
        return IOUtils.toString(req.getInputStream(), "UTF-8");
    }

    protected final int intParam(String name, HttpServletRequest req) {
        try {
            String s = req.getParameter(name);
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new BadRequest(String.format("%s: Require query paramameter of int type with name %s",
                    req.getRequestURL(), name));
        }
    }

    protected final void writeJson(String schemaJson, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().print(schemaJson);
    }

    protected final User currentUser() {
        return null;
    }

    private static class BadRequest extends RuntimeException {
        public BadRequest(String message) {
            super(message);
        }
    }
}
