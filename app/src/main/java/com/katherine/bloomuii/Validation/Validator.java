/*Created by: Rohini Naidu 26/06/2020*/
package com.katherine.bloomuii.Validation;

public class Validator {
    //Authentication Validation
    //Date Validator
    public Validator(){
    }

    //Password Policy - At least 1 Caps, At least one number, Longer than 6 Characters
    //At least on Digit
    public boolean CheckIfDigitExists(String password) {
        char[] chars = password.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    //At least one Caps
    public boolean CheckIfCapsExists(String password){
        char[] chars = password.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }
}//End of class
