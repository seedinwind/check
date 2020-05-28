package com.passion.check;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LogUtil {

    private static String TAG = "CheckService";

    @NonNull
    public static List<Field> getFieldsWithPrefix(Class<?> clazz, String prefix) {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> list = new ArrayList<>();
        for (Field field : fields) {
            if (field.getName().startsWith(prefix)) {
                list.add(field);
            }
        }
        return list;
    }

    @NonNull
    public static List<Pair<String, String>> getMatchedFlags(@NonNull String className, @NonNull List<Field> fields, int flags) {
        List<Pair<String, String>> list = new ArrayList<>();
        try {
            for (Field field : fields) {
                if (int.class.equals(field.getType())) {
                    Integer value = (Integer) field.get(null);
                    if (value != null && (flags & value) == value) {
                        list.add(new Pair<>(field.getName(), String.valueOf(value)));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, "object class: " + className, e);
        }
        return list;
    }

    @NonNull
    public static String makeMultipleChoiceStringResult(Class<?> searchIn, String prefix, int flags) {
        List<Field> flagsFields = getFieldsWithPrefix(searchIn, prefix);
        List<Pair<String, String>> resultList = getMatchedFlags(searchIn.getName(), flagsFields, flags);
        StringBuilder flagDesc = new StringBuilder(64);
        for (Pair<String, String> flag : resultList) {
            flagDesc.append(flag.first + ", ");
        }
        if (!resultList.isEmpty()) {
            flagDesc.delete(flagDesc.length() - 2, flagDesc.length());
        }
        return flagDesc.toString();
    }
}
