package vika.kovalenko.app.core.manage.handler;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.utility.Coordinates;

import java.io.Serial;
import java.io.Serializable;

public record EggsHandler(GameObjectName objectName, Coordinates coordinates, int eggsCount) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
