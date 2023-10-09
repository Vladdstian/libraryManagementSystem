package com.libManag;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientSession {
    private static ClientSession cs = null;
    private List<String> notifications;
    private List<Book> cartList;

    private ClientSession() {
        cartList = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    public static ClientSession ClientSession() {
        if (cs == null) {
            cs = new ClientSession();
        }
        return cs;
    }

    public void resetSession() {
        cs = null;
    }


}
