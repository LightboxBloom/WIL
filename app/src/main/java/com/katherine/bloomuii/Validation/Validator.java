/*Created by: Rohini Naidu 26/06/2020*/
package com.katherine.bloomuii.Validation;

public class Validator {
    //Authentication Validation
    private String strError;
    private boolean validatorFlag = false;
    //Date Validator
    public Validator(){
    }

    //Password Policy - At least 1 Caps, At least one number, Longer than 6 Characters
    //At least on Digit
    public void CheckIfDigitExists(String password) {
        boolean temp = false;
        char[] chars = password.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                validatorFlag  = true;
                temp = true;
            }
        }
        if(!temp){
            strError = "Password must contain at least one digit.";
            validatorFlag = false;
        }
    }
    //At least one Caps
    public void CheckIfCapsExists(String password){
        boolean temp = false;
        char[] chars = password.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                validatorFlag = true;
                temp = true;
            }
        }
        if(!temp){
            strError = "Password must contain at least one CAPS letter.";
            validatorFlag = false;
        }
    }
    //Check length of password
    public void CheckLength(String password){
        if(password.length() > 6){
            validatorFlag = true;
        }
        else {
            strError = "Password must have 6 or more characters.";
            validatorFlag = false;
        }
    }
    //Check if strings are identical
    public void CheckIfMatch(String string1, String string2){
        if(string1.equals(string2)){
            validatorFlag = true;
        }
        else{
            strError = "Passwords do not match.";
            validatorFlag = false;
        }
    }

    public String getStrError() {
        return strError;
    }

    public void setStrError(String strError) {
        this.strError = strError;
    }

    public boolean isValidatorFlag() {
        return validatorFlag;
    }

    public void setValidatorFlag(boolean validatorFlag) {
        this.validatorFlag = validatorFlag;
    }
}//End of class
