package com.geom.geomdriver.classes;

import java.util.List;

/**
 * Created by cryph on 07/06/2016.
 */
public class SharedData {

    public List<PublicTransport> pt_list;

    public String username;
    public String password;
    public String response;

    public SharedData() {
        response = "";
    }

    synchronized public String response() {
        return response;
    }
    synchronized public void response(String response) {
        this.response = response;
    }
}
