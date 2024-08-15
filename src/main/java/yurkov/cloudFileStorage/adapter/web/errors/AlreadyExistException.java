package yurkov.cloudFileStorage.adapter.web.errors;

import lombok.Getter;

@Getter
public class AlreadyExistException extends RuntimeException{
    private String code = "ALREADY_EXIST";

    public AlreadyExistException(String entity) {
        super(entity + " with this username already exists");
    }
}
