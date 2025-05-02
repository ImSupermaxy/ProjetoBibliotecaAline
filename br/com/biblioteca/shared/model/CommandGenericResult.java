package br.com.biblioteca.shared.model;

import br.com.biblioteca.shared.model.interfaces.ICommandResult;

public class CommandGenericResult<T> implements ICommandResult {
    private final boolean success;
    private final String message;
    private final T data;

    public CommandGenericResult(){
        this.success = false;
        this.message = "";
        this.data = null;
    }

    public CommandGenericResult(boolean success, String message){
        this.success = success;
        this.message = message;
        this.data = null;
    }

    public CommandGenericResult(boolean success, String message, T data){
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

    public T getData() {
        return data;
    }
}
