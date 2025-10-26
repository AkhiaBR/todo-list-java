package com.akhiastudios.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    public static void copyNonNullProperties(Object source, Object target, String... ignoreProperties) {
        // Pega as propriedades nulas do objeto de origem
        String[] nullNames = getNullPropertyNames(source);
        
        // Combina as propriedades nulas com as propriedades explicitamente ignoradas
        Set<String> combinedIgnored = new HashSet<>(Arrays.asList(nullNames));
        Collections.addAll(combinedIgnored, ignoreProperties);
        
        String[] finalIgnored = combinedIgnored.toArray(new String[0]);
        
        // Copia as propriedades, ignorando nulas e as explicitamente ignoradas (como o "id" e "idUser")
        BeanUtils.copyProperties(source, target, finalIgnored);
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}