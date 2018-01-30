package com.entities;


public enum UserRole {
    ADMIN, TEACHER, STUDENT;

    public static String getAutority(String role) {
        String userRole = "";
        switch (role) {
            case "STUDENT":
                userRole = "ROLE_" + STUDENT;
                break;
            case "ADMIN":
                userRole = "ROLE_" + ADMIN;
                break;
            case "TEACHER":
                userRole = "ROLE_" + TEACHER;
                break;
        }
        return userRole;
    }

}
