package com.neoris.backend.apirest.exceptions;

public class BadRequestExceptions extends NeorisException {
    private static final long serialVersionUID = 100L;

    public BadRequestExceptions(String code, String message, String categoryMessage, String action, Integer codeHttp) {
        super(code, message, categoryMessage, action, codeHttp);
    }
}
