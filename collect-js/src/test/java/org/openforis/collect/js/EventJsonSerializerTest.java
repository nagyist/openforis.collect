package org.openforis.collect.js;

import org.junit.Test;
import org.openforis.collect.api.command.Command;
import org.openforis.collect.api.event.EntityAddedEvent;
import org.openforis.collect.js.json.EventJsonSerializer;

import static java.util.Arrays.asList;

public class EventJsonSerializerTest {

    public static final Command ANY_COMMAND = null;

    @Test
    public void test() {
        String s = new EventJsonSerializer().serialize(asList(
                new EntityAddedEvent(ANY_COMMAND, null, "event id", "def id")
                ));
        System.out.println(s);
        // TODO: Assert something
    }
}
