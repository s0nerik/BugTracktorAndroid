package com.github.sonerik.bugtracktor.utils;

import com.github.sonerik.bugtracktor.models.IssueType;

/**
 * Created by sonerik on 6/8/16.
 */
public class NamingUtils {
    public static String getReadableIssueType(IssueType type) {
        return Character.toUpperCase(type.getName().charAt(0))+type.getName().replace("_", " ").substring(1);
    }
}
