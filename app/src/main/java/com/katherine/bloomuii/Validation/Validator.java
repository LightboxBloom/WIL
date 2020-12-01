/*Created by: Rohini Naidu 26/06/2020*/
package com.katherine.bloomuii.Validation;
public class Validator {
    //Fields
    private String strError;
    private boolean validatorFlag = false;
    private boolean digitExistsFlag = false;
    private boolean capsExistFlag = false;
    private boolean lengthCheckFlag = false;
    private boolean matchFlag = false;
    //Constructor
    public Validator(){ }
    //Password Policy - At least 1 Caps, At least one number, Longer than 6 Characters
    //At least on Digit
    public void CheckIfDigitExists(String password) {
        boolean temp = false;
        char[] chars = password.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                digitExistsFlag  = true;
                temp = true;
            }
        }
        if(!temp){
            strError = "Password must contain at least one digit.";
            digitExistsFlag = false;
        }
    }
    //At least one Caps
    public void CheckIfCapsExists(String password){
        boolean temp = false;
        char[] chars = password.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                capsExistFlag = true;
                temp = true;
            }
        }
        if(!temp){
            strError = "Password must contain at least one CAPS letter.";
            capsExistFlag = false;
        }
    }
    //Check length of password
    public void CheckLength(String password){
        if(password.length() > 6){
            lengthCheckFlag = true;
        }
        else {
            strError = "Password must have 6 or more characters.";
            lengthCheckFlag = false;
        }
    }
    //Check if strings are identical
    public void CheckIfMatch(String string1, String string2){
        if(string1.equals(string2)){
            matchFlag = true;
        }
        else{
            strError = "Passwords do not match.";
            matchFlag = false;
        }
    }
    //Getters and Setters
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
    public boolean isDigitExistsFlag() {
        return digitExistsFlag;
    }
    public void setDigitExistsFlag(boolean digitExistsFlag) {
        this.digitExistsFlag = digitExistsFlag;
    }
    public boolean isCapsExistFlag() {
        return capsExistFlag;
    }
    public void setCapsExistFlag(boolean capsExistFlag) {
        this.capsExistFlag = capsExistFlag;
    }
    public boolean isLengthCheckFlag() {
        return lengthCheckFlag;
    }
    public void setLengthCheckFlag(boolean lengthCheckFlag) {
        this.lengthCheckFlag = lengthCheckFlag;
    }
    public boolean isMatchFlag() {
        return matchFlag;
    }
    public void setMatchFlag(boolean matchFlag) {
        this.matchFlag = matchFlag;
    }
}
