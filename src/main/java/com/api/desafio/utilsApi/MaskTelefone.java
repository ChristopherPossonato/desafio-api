package com.api.desafio.utilsApi;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class MaskTelefone {
    private static final String PADRAO = "(##) #####-####";
    public static String format(String telefone) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(PADRAO);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(telefone);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
