package settler.com.reactor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import reactor.core.publisher.FluxSink;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FluxPublisherTextWatcherTest {

    @Mock
    private FluxSink<CharSequence> mockSink;

    @Test
    public void shouldPublishAnyTextChangesIntoTheSink() throws Exception {

        FluxPublisherTextWatcher watcher = new FluxPublisherTextWatcher(mockSink);
        verifyZeroInteractions(mockSink);

        watcher.onTextChanged("Random text", 0, 0, 0);

        verify(mockSink, times(1)).next("Random text");
        verifyNoMoreInteractions(mockSink);

    }

}