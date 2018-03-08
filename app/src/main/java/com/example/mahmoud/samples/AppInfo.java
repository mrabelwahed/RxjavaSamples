package com.example.mahmoud.samples;

import android.support.annotation.NonNull;

/**
 * Created by mahmoud on 08/03/18.
 */

public class AppInfo implements  Comparable<Object> {
    private String name,icon;
    private long lastupdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(long lastupdate) {
        this.lastupdate = lastupdate;
    }

    public AppInfo(String name , String icon , long lastUpdate){
      this.name = name;
      this.icon = icon;
      this.lastupdate = lastUpdate;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        AppInfo appInfo = (AppInfo) o;
        return getName().compareTo(appInfo.getName());
    }
}
