/*Created by: Matthew Talbot
* Edited by: Rohini Naidu 28/06/2020 */

package com.katherine.bloomuii.ObjectClasses;

public class User {
    private String Email_Address;
    private String Full_Name;
    private String Date_Of_Birth;
    private String Language_Preference;

    //TODO: Add List of Usernames for each subAccount/child belonging to a single parent
    //TODO: Language Preference DEFAULT set to English in Sign Up Activity
    //TODO: Type of User not resolved in SignUp and not incl in User class or not needed just check if classroom was created by user?

    public  User(){ }

    public User(String email_Address, String full_name, String date_Of_Birth, String language) {
        this.setEmail_Address(email_Address);
        this.setFull_Name(full_name);
        this.setDate_Of_Birth(date_Of_Birth);
        this.setLanguage_Preference(language);
    }
    //Edit Profile Constructor
    public User(String email_Address, String full_Name, String date_Of_Birth){
        this.setEmail_Address(email_Address);
        this.setFull_Name(full_Name);
        this.setDate_Of_Birth(date_Of_Birth);
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String email_Address) {
        Email_Address = email_Address;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String first_Name) {
        Full_Name = first_Name;
    }

    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(String date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
    }

    public String getLanguage_Preference() {
        return Language_Preference;
    }

    public void setLanguage_Preference(String language_Preference) {
        Language_Preference = language_Preference;
    }


}//End of class
