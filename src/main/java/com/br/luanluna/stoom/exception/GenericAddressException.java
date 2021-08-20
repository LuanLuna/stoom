package com.br.luanluna.stoom.exception;

import java.util.ArrayList;
import java.util.List;

public class GenericAddressException extends RuntimeException {
    List<String> reasons;

    public GenericAddressException(List<String> reasons) {
        this.reasons = reasons;
    }

    GenericAddressException() {
        this.reasons = new ArrayList<>();
    }

    public String[] getReasons() {
        if (this.reasons != null && this.reasons.size() > 0) {
            String[] result = new String[this.reasons.size()];
            return this.reasons.toArray(result);
        }
        return new String[0];
    }
}
