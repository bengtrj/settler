package settler.com.reactor;

import reactor.core.publisher.FluxSink;

class FluxPublisherTextWatcher extends DefaultTextWatcher {

    private final FluxSink<CharSequence> flux;

    FluxPublisherTextWatcher(FluxSink<CharSequence> flux) {
        this.flux = flux;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        flux.next(charSequence);
    }

}
