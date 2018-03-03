package com.github.diaglyonok.contactsapi;

import java.util.ArrayList;

/**
 * Created by diaglyonok on 02.03.18.
 */

public interface AsyncResponse {
    void processFinish(ArrayList<User> output);
}
