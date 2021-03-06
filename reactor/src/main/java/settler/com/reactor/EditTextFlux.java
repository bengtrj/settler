package settler.com.reactor;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.widget.TextView;

import reactor.core.publisher.Flux;

import static java.util.Objects.requireNonNull;

public class EditTextFlux {
    
    @CheckResult @NonNull
    public static Flux<CharSequence> textChanges(@NonNull TextView view) {
        requireNonNull(view, "view == null");

        return Flux.push(s -> {
            s.next(view.getText());
            view.addTextChangedListener(new FluxPublisherTextWatcher(s));
        });

    }

    private EditTextFlux() {
        throw new AssertionError("No instances allowed!");
    }
}