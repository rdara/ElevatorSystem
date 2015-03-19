/**
 * 
 */
package com.rdara.solutions.elevatorsystem.utils;

/**
 * @author Ramesh Dara
 */
public class Utilities {

    //Refer my blog at, http://developerdigest.blogspot.com/2014/10/enum-creation-from-caseinsensitive.html
    public static <T extends Enum<T>> T getEnumFromString(Class<T> enumClass, String value) {
        StringBuilder errorMessageValue = null;
        if (enumClass != null) {
            for (Enum<?> enumValue : enumClass.getEnumConstants()) {
                if (enumValue.toString().equalsIgnoreCase(value)) {
                    return (T) enumValue;
                }
            }
            if (value.trim().isEmpty()) {
                throw new IllegalArgumentException("Must not be blank");
            }
            errorMessageValue = new StringBuilder();
            boolean bFirstTime = true;
            for (Enum<?> enumValue : enumClass.getEnumConstants()) {
                errorMessageValue.append(bFirstTime ? "" : ", ").append(enumValue);
                bFirstTime = false;
            }
            throw new IllegalArgumentException(value + " is invalid value. Supported values are " + errorMessageValue);
        }

        throw new IllegalArgumentException("EnumClass value can't be null.");
    }

}
