package com.nisum.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import javax.json.JsonArray;
import javax.json.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static boolean validatePassword(String password, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).matches();
    }

    public static <T> T parseJsonObject(JsonObject jsonObject, Class<T> objectClass)
        throws JsonProcessingException {
        return objectMapper.readValue(jsonObject.toString(), objectClass);
    }

    public static <T> List<T> parseJsonArray(JsonArray jsonArray, Class<T> arrClass)
        throws JsonProcessingException, ClassNotFoundException {
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + arrClass.getName() +";");
        return Arrays.asList(
            objectMapper.readValue(jsonArray.toString(), arrayClass)
        );
    }

}
