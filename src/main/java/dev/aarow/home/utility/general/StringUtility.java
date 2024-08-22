package dev.aarow.home.utility.general;

public class StringUtility {

    public static boolean containsSpecialCharacters(String input){
        String specialCharsPattern = "[^a-zA-Z0-9 ]";

         return input.matches(".*" + specialCharsPattern + ".*");
    }
}
