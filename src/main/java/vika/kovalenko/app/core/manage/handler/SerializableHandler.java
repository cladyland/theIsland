package vika.kovalenko.app.core.manage.handler;

import java.io.Serializable;

public interface SerializableHandler extends Serializable {
    void serialize();

    void deserialize();
}
