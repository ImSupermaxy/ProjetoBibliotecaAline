package br.com.biblioteca.shared.model;

import br.com.biblioteca.shared.model.interfaces.ICommandResult;

public class CommandResult implements ICommandResult {
    private final boolean success;
    private final String message;
    private final Object data;

    public CommandResult(){
        this.success = false;
        this.message = "";
        this.data = null;
    }

    public CommandResult(boolean success, String message){
        this.success = success;
        this.message = message;
        this.data = null;
    }

    public  CommandResult(boolean success, String message, Object data){
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
