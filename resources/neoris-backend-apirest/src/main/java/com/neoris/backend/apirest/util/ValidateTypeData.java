package com.neoris.backend.apirest.util;


import com.neoris.backend.apirest.exceptions.BadRequestExceptions;

import java.math.BigDecimal;

public class ValidateTypeData {

    public static Boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBigDecimal(String number) {
        if (number == null)  {
            return false;
        }
        if(number.contains(",")){
            return false;
        }
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static void isNull(String fieldNmae, String value, Class typeData) throws BadRequestExceptions {
        if(value==null){
            throw new BadRequestExceptions("400", fieldNmae.toUpperCase().concat(" IS NULL"), fieldNmae.toUpperCase().concat(" MUST BE DIFFERENT TO NULL"), "INFO", 400);
        }
        if(value.equals("null")){
            throw new BadRequestExceptions("400", fieldNmae.toUpperCase().concat(" IS NULL"), fieldNmae.toUpperCase().concat(" MUST BE DIFFERENT TO NULL"), "INFO", 400);
        }
        if(value.length() < 0){
            throw new BadRequestExceptions("400", fieldNmae.toUpperCase().concat(" IS NULL"), fieldNmae.toUpperCase().concat(" MUST BE DIFFERENT TO NULL"), "INFO", 400);
        }
        if(value.equals("")){
            throw new BadRequestExceptions("400", fieldNmae.toUpperCase().concat(" IS NULL"), fieldNmae.toUpperCase().concat(" MUST BE DIFFERENT TO NULL"), "INFO", 400);
        }

        if(typeData.equals(Integer.class)){
            if(!isInteger(value)){
                throw new BadRequestExceptions("400", fieldNmae.toUpperCase().concat(" HAS AN INCORRECT FORMAT"), "ENTER NUMBER ONLY ", "INFO", 400);
            }
        }
        if(typeData.equals(BigDecimal.class)){
            if(!isBigDecimal(value)){
                throw new BadRequestExceptions("400", fieldNmae.toUpperCase().concat(" HAS AN INCORRECT FORMAT"), "ENTER BIGDECIMAL ONLY ", "INFO", 400);
            }
        }

    }
}
