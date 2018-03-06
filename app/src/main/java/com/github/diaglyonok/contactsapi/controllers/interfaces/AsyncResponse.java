package com.github.diaglyonok.contactsapi.controllers.interfaces;

import com.github.diaglyonok.contactsapi.data.model.User;

import java.util.ArrayList;

/**
 * Created by diaglyonok on 02.03.18.
 * https://github.com/Diaglyonok
 * diaglyonok@yandex.ru
 */
//Interface, that helps to pipe data from Data to Controller
public interface AsyncResponse {
    void processFinished(ArrayList<User> output);
}
