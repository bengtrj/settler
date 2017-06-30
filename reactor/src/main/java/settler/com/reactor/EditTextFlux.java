package settler.com.reactor;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.widget.TextView;

import reactor.core.publisher.Flux;

import static java.util.Objects.requireNonNull;

public final class EditTextFlux {
    
    @CheckResult @NonNull
    public static Flux<CharSequence> textChanges(@NonNull TextView view) {
        requireNonNull(view, "view == null");

        return Flux.push(s -> {
            s.next(view.getText());
            view.addTextChangedListener(new NoopTextWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    s.next(charSequence);
                }
            });
        });

    }

    private EditTextFlux() {
        throw new AssertionError("No instances allowed!");
    }
}