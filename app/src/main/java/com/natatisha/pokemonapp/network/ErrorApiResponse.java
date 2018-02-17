package com.natatisha.pokemonapp.network;

public class ErrorApiResponse extends Throwable {

    private int mCode;

    public ErrorApiResponse(int code, String message) {
        super(message);
        this.mCode = code;
    }

    public int getCode() {
        return mCode;
    }
}

