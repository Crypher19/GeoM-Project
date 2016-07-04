package com.geom.geomdriver.classes;

import android.view.View;

/**
 * Created by cryph on 04/07/2016.
 */
public class MyBundle {
    public SharedData s;
    public View v;

    public MyBundle() {
        this.s = null;
        this.v = null;
    }
    public MyBundle(SharedData s, View v) {
        this.s = s;
        this.v = v;
    }
}
