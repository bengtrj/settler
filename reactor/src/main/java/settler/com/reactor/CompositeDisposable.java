package settler.com.reactor;

import java.util.*;

import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.util.concurrent.OpenHashSet;

import static java.util.Objects.requireNonNull;


/**
 * A disposable container that can hold onto multiple other disposables and
 * offers O(1) add and removal complexity.
 */
public class CompositeDisposable implements Disposable {

    OpenHashSet<Disposable> resources;

    volatile boolean disposed;

    /**
     * Creates an empty CompositeDisposable.
     */
    public CompositeDisposable() {
    }

    /**
     * Creates a CompositeDisposables with the given array of initial elements.
     * @param resources the array of Disposables to start with
     */
    public CompositeDisposable(Disposable... resources) {
        requireNonNull(resources, "resources is null");
        this.resources = new OpenHashSet<>(resources.length + 1, 0.75f);
        for (Disposable d : resources) {
            requireNonNull(d, "Disposable item is null");
            this.resources.add(d);
        }
    }

    /**
     * Creates a CompositeDisposables with the given Iterable sequence of initial elements.
     * @param resources the Iterable sequence of Disposables to start with
     */
    public CompositeDisposable(Iterable<? extends Disposable> resources) {
        requireNonNull(resources, "resources is null");
        this.resources = new OpenHashSet<>();
        for (Disposable d : resources) {
            requireNonNull(d, "Disposable item is null");
            this.resources.add(d);
        }
    }

    @Override
    public void dispose() {
        if (disposed) {
            return;
        }
        OpenHashSet<Disposable> set;
        synchronized (this) {
            if (disposed) {
                return;
            }
            disposed = true;
            set = resources;
            resources = null;
        }

        dispose(set);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    public boolean add(Disposable d) {
        requireNonNull(d, "d is null");
        if (!disposed) {
            synchronized (this) {
                if (!disposed) {
                    OpenHashSet<Disposable> set = resources;
                    if (set == null) {
                        set = new OpenHashSet<Disposable>();
                        resources = set;
                    }
                    set.add(d);
                    return true;
                }
            }
        }
        d.dispose();
        return false;
    }

    /**
     * Atomically adds the given array of Disposables to the container or
     * disposes them all if the container has been disposed.
     * @param ds the array of Disposables
     * @return true if the operation was successful, false if the container has been disposed
     */
    public boolean addAll(Disposable... ds) {
        requireNonNull(ds, "ds is null");
        if (!disposed) {
            synchronized (this) {
                if (!disposed) {
                    OpenHashSet<Disposable> set = resources;
                    if (set == null) {
                        set = new OpenHashSet<Disposable>(ds.length + 1, 0.75f);
                        resources = set;
                    }
                    for (Disposable d : ds) {
                        requireNonNull(d, "d is null");
                        set.add(d);
                    }
                    return true;
                }
            }
        }
        for (Disposable d : ds) {
            d.dispose();
        }
        return false;
    }

    public boolean remove(Disposable d) {
        if (delete(d)) {
            d.dispose();
            return true;
        }
        return false;
    }

    public boolean delete(Disposable d) {
        requireNonNull(d, "Disposable item is null");
        if (disposed) {
            return false;
        }
        synchronized (this) {
            if (disposed) {
                return false;
            }

            OpenHashSet<Disposable> set = resources;
            if (set == null || !set.remove(d)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Atomically clears the container, then disposes all the previously contained Disposables.
     */
    public void clear() {
        if (disposed) {
            return;
        }
        OpenHashSet<Disposable> set;
        synchronized (this) {
            if (disposed) {
                return;
            }

            set = resources;
            resources = null;
        }

        dispose(set);
    }

    /**
     * Returns the number of currently held Disposables.
     * @return the number of currently held Disposables
     */
    public int size() {
        if (disposed) {
            return 0;
        }
        synchronized (this) {
            if (disposed) {
                return 0;
            }
            OpenHashSet<Disposable> set = resources;
            return set != null ? set.size() : 0;
        }
    }

    /**
     * Dispose the contents of the OpenHashSet by suppressing non-fatal
     * Throwables till the end.
     * @param set the OpenHashSet to dispose elements of
     */
    void dispose(OpenHashSet<Disposable> set) {
        if (set == null) {
            return;
        }
        List<Throwable> errors = null;
        Object[] array = set.keys();
        for (Object o : array) {
            if (o instanceof Disposable) {
                try {
                    ((Disposable) o).dispose();
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    if (errors == null) {
                        errors = new ArrayList<>(5);
                    }
                    errors.add(ex);
                }
            }
        }
        if (errors != null) {
            if (errors.size() == 1) {
                throw new RuntimeException(errors.get(0));
            }
            throw Exceptions.multiple(errors.toArray(new Throwable[errors.size()]));
        }
    }
}
