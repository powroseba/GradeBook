package com.entities;


public enum UserRole {
    ADMIN, DIRECTOR, TEACHER, STUDENT, PARENT;

    public static String getAutority(String role) {
        String userRole = "";
        switch (role) {
            case "STUDENT":
                userRole = "ROLE_" + STUDENT;
                break;
            case "ADMIN":
                userRole = "ROLE_" + ADMIN;
                break;
            case "DIRECTOR":
                userRole = "ROLE_" + DIRECTOR;
                break;
            case "TEACHER":
                userRole = "ROLE_" + TEACHER;
                break;
            case "PARENT":
                userRole = "ROLE_" + PARENT;
                break;
        }
        return userRole;
    }

}
