package com.scrumsys.common.constants;

/**
 * Action Mode for CRUD operation
 *
 * @author MohamedAli
 */

public enum ActionMode {
    CREATE("CREATE"),
    MODIFY("MODIFY"),
    DELETE("DELETE"),
    VALIDATE("VALIDATE"),
    VIEW("VIEW");

    private String actionMode;

    private ActionMode(String actionMode) {
        this.actionMode = actionMode;
    }

    public String getAction() {
        return actionMode;
    }

}
