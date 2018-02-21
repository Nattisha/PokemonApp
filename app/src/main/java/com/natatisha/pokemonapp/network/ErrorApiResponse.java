package com.natatisha.pokemonapp.network;

public class ErrorApiResponse extends Throwable {

    private int code;

    public ErrorApiResponse(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

