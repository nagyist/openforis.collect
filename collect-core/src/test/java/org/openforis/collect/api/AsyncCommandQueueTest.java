package org.openforis.collect.api;

import org.junit.Test;
import org.openforis.collect.api.command.AsyncCommandQueue;
import org.openforis.collect.api.command.Command;
import org.openforis.collect.api.command.CommandQueue;
import org.openforis.collect.api.command.UpdateAttributeValueCommand;

import static org.mockito.Mockito.*;

public class AsyncCommandQueueTest {
    static int i = 0;
    CommandQueue delegate = mock(CommandQueue.class);
    AsyncCommandQueue queue = new AsyncCommandQueue(delegate);

    @Test
    public void testCommandsAreSubmitted() {
        queue.submit(command(1));
        queue.submit(command(1));

        verify(delegate, timeout(1000).times(2)).submit(any(Command.class));
    }

    private Command command(int recordId) {
        int attributeId = ++i;
        return new UpdateAttributeValueCommand(0, recordId, null, attributeId, null) {
            public String toString() {
                return "Command(recordId: " + recordId + ", i: " + attributeId + ")";
            }
        };
    }
}
