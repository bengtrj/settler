package example;

import android.text.TextWatcher;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import settler.com.reactor.EditTextFlux;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditTextFluxAllInOneTest {

    private TextView textViewMock;

    @Before
    public void setUp() throws Exception {
        textViewMock = mock(TextView.class);
    }

    @Test
    public void textChanges() throws Exception {

        when(textViewMock.getText()).thenReturn("Initial value");

        ArgumentCaptor<TextWatcher> argumentCaptor = ArgumentCaptor.forClass(TextWatcher.class);

        doNothing().when(textViewMock).addTextChangedListener(argumentCaptor.capture());

        Flux<CharSequence> flux = EditTextFlux.textChanges(textViewMock);

        StepVerifier.create(flux)
                .expectNext("Initial value")
                .then(() -> argumentCaptor.getValue().onTextChanged("Second value", 0, 0, 0))
                .expectNext("Second value")
                .then(() -> argumentCaptor.getValue().onTextChanged("Third value", 0, 0, 0))
                .expectNext("Third value")
                .thenCancel()
                .verify();

    }

}