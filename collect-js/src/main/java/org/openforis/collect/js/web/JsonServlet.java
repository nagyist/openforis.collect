package org.openforis.collect.js.web;

import org.openforis.collect.api.command.CommandQueue;
import org.openforis.collect.api.command.UpdateAttributeValueCommand;
import org.openforis.collect.api.query.RecordEventsProvider;
import org.openforis.collect.api.query.SchemaProvider;
import org.openforis.collect.api.schema.EntityDef;
import org.openforis.collect.apiadapter.DefaultRecordEventsProvider;
import org.openforis.collect.apiadapter.DefaultSchemaProvider;
import org.openforis.collect.apiadapter.SynchronousCommandQueue;
import org.openforis.collect.js.json.JsonCommandParser;
import org.openforis.collect.js.json.SchemaJsonSerializer;
import org.openforis.collect.js.web.util.DispatchingServlet;
import org.openforis.collect.js.web.util.JsonQueryHandler;
import org.openforis.collect.js.web.util.RequestHandler;
import org.openforis.collect.js.web.util.StreamingRequestHandler;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonServlet extends DispatchingServlet {
    private final CommandQueue commandQueue = new SynchronousCommandQueue(null);
    private final RecordEventsProvider recordEventsProvider = new DefaultRecordEventsProvider();
    private final SchemaProvider schemaProvider = new DefaultSchemaProvider();

    protected void initHandlers() {
        post("/update-attribute", new RequestHandler() {
            protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                UpdateAttributeValueCommand command = new JsonCommandParser().updateAttributeValueCommand(
                        bodyAsString(req),
                        currentUser()
                );
                commandQueue.submit(command);
            }
        });


        get("/schema", new JsonQueryHandler() {
            protected String json(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                EntityDef schema = schemaProvider.schema(intParam("surveyId", req));
                return new SchemaJsonSerializer().serialize(schema);
            }
        });


        get("/record", new StreamingRequestHandler(0) {
            protected void subscribe(HttpServletRequest req, HttpServletResponse resp, final AsyncContext ctx) throws IOException {
                new EventResponseWriter(
                        intParam("surveyId", req),
                        intParam("recordId", req),
                        ctx
                ).initialize(recordEventsProvider, commandQueue);
            }
        });
    }


}
