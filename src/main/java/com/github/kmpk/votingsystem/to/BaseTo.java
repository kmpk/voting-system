package com.github.kmpk.votingsystem.to;

public abstract class BaseTo {
    protected Integer id;

    BaseTo() {
    }

    BaseTo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return getId() == null;
    }
}
