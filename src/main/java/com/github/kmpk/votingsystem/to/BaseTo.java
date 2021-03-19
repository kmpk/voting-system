package com.github.kmpk.votingsystem.to;

import com.github.kmpk.votingsystem.HasId;

public abstract class BaseTo implements HasId {
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
