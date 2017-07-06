package settler.com.reactor;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditTextFluxTest {

    private TextView textViewMock;

    @Before
    public void setUp() throws Exception {
        textViewMock = mock(TextView.class);
    }

    @Test
    public void shouldPublishTheInitialValueImmediatelyAfterSubscribing() throws Exception {

        when(textViewMock.getText()).thenReturn("Initial value");

        Flux<CharSequence> flux = EditTextFlux.textChanges(textViewMock);

        StepVerifier.create(flux)
                .expectNext("Initial value")
                .thenCancel()
                .verify();

    }

    @Test
    public void shouldRegisterAFluxPublisherTextWatcher() throws Exception {

        when(textViewMock.getText()).thenReturn("ignored");

        Flux<CharSequence> flux = EditTextFlux.textChanges(textViewMock);

        StepVerifier.create(flux).thenCancel().verify();

        verify(textViewMock, times(1)).addTextChangedListener(any(FluxPublisherTextWatcher.class));

    }

}