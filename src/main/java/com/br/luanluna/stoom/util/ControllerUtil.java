package com.br.luanluna.stoom.util;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public abstract class ControllerUtil {

    public static String[] extractErrors(BindingResult bindingResult) {
        List<String> errorResult = new ArrayList<>();
        String[] result = new String[bindingResult.getErrorCount()];
        try{
            bindingResult.getAllErrors().stream().forEach(item -> {
                errorResult.add(item.getDefaultMessage());
            });
        } catch (Exception e) {}

        return errorResult.size() > 0 ? errorResult.toArray(result) : new String[0];
    }
}
