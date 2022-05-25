package com.example.socialnetwork;

import android.net.Uri;

public class UserConfig {
    Uri photo = null;

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    UserConfig(Uri photo){
        this.photo = photo;
    }
}
