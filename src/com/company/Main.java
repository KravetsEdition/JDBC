package com.company;

import com.company.DBConnect.DbHandler;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;


public class Main extends JFrame {

    public static void main(String[] argv) throws Exception {
        DbHandler dbHandler = new DbHandler();
        Set<String> studentIds = new HashSet<>();
        Set<String> userIds = new HashSet<>();

        ResultSet appUserResult = dbHandler.getInformationTable("""
                SELECT id FROM app_user
                """, dbHandler.dbConnection2);

        ResultSet studentResult = dbHandler.getInformationTable("""
                SELECT student.id
                FROM student_degree st
                JOIN student ON student.id = st.student_id
                WHERE st.active = true
                """, dbHandler.dbConnection1);

        while (appUserResult.next()) {
            userIds.add(appUserResult.getString(1));
        }

        while (studentResult.next()) {
            studentIds.add(studentResult.getString(1));
        }

        System.out.println(studentIds);
        System.out.println(userIds);

        System.out.println(getStudentIdsFoundInUsers(studentIds, userIds));
        System.out.println(getStudentIdsNotFoundInUsers(studentIds, userIds));
        System.out.println(getUserIdsNotFoundInUsersStudent(userIds, studentIds));

        deleteUsersSoft(getUserIdsNotFoundInUsersStudent(userIds, studentIds));
        deleteUsersHard(getUserIdsNotFoundInUsersStudent(userIds, studentIds));
    }

    public static Set<String> getStudentIdsFoundInUsers(Set<String> studentId, Set<String> userIds) {
        Set<String> idsEntry = new HashSet<>(studentId);
        idsEntry.retainAll(userIds);
        return idsEntry;
    }

    public static Set<String> getStudentIdsNotFoundInUsers(Set<String> studentId, Set<String> userIds) {
        Set<String> idsEntry = new HashSet<>(studentId);
        idsEntry.removeAll(userIds);
        return idsEntry;
    }

    public static Set<String> getUserIdsNotFoundInUsersStudent(Set<String> userIds, Set<String> studentId) {
        Set<String> idsEntry = new HashSet<>(userIds);
        idsEntry.removeAll(studentId);
        return idsEntry;
    }

    public static void deleteUsersSoft(Set<String> idsSet) {
        if(idsSet.size() != 0) {
            DbHandler dbHandler = new DbHandler();
            StringBuilder deleteIds = new StringBuilder();
            for (String s : idsSet)
                deleteIds.append(s).append(",");
            deleteIds = new StringBuilder(deleteIds.substring(0, deleteIds.length() - 1)); // режем последний символ ","
            dbHandler.deleteInformationTable("UPDATE app_user SET active = false WHERE id IN(" + deleteIds + ");", dbHandler.dbConnection2);
        }
    }

    public static void deleteUsersHard(Set<String> idsSet) {
        if(idsSet.size() != 0) {
            DbHandler dbHandler = new DbHandler();
            StringBuilder deleteIds = new StringBuilder();
            for (String s : idsSet)
                deleteIds.append(s).append(",");
            deleteIds = new StringBuilder(deleteIds.substring(0, deleteIds.length() - 1)); // режем последний символ ","
            System.out.println("DELETE FROM app_user WHERE id IN(" + deleteIds + ");");
            //dbHandler.deleteInformationTable("DELETE FROM app_user WHERE id in(" + deleteIds + ");", dbHandler.dbName2);
        }
    }
}
