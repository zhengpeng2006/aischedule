package com.asiainfo.monitor.tools.model;

import java.util.EventObject;

public class ContainerEvent extends EventObject{


    private BaseContainer container = null;

    private Object data = null;

    private String type = null;

    public ContainerEvent(BaseContainer container, String type, Object data) {

        super(container);
        this.container = container;
        this.type = type;
        this.data = data;

    }

    public Object getData() {

        return (this.data);

    }


    public BaseContainer getContainer() {

        return (this.container);

    }

    public String getType() {

        return (this.type);

    }

    public String toString() {

        return ("ContainerEvent['" + getContainer() + "','" +getType() + "','" + getData() + "']");

    }
}
