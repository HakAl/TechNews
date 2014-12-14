package com.jacmobile.technews.injection;

import dagger.ObjectGraph;

/**
 * An instance which is capable of injecting dependencies.
 */
public interface DaggerInjector {
    /**
     * Inject to <code>object</code>
     *
     * @param object
     */
    void inject(Object object);

    ObjectGraph getObjectGraph();
}