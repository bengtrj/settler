package example;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.widget.TextView;

import reactor.core.publisher.Flux;
import settler.com.reactor.DefaultTextWatcher;

import static java.util.Objects.requireNonNull;

class EditTextFluxAllInOne {

    @CheckResult @NonNull
    public static Flux<CharSequence> textChanges(@NonNull TextView view) {
        requireNonNull(view, "view == null");

        return Flux.push(s -> {
            s.next(view.getText());
            view.addTextChangedListener(new DefaultTextWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    s.next(charSequence);
                }
            });
        });

    }

    private EditTextFluxAllInOne() {
        throw new AssertionError("No instances allowed!");
    }
}