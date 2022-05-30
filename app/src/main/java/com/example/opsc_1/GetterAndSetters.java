package com.example.opsc_1;

public class GetterAndSetters {

    //All the data that must be stored, some of it must go in array lists others stored once in getters and setters
    /*

private string DOB;
private string medal;
private string itemName;
private string collectionName;
private string descriptionOfItem;
private string DATE_OF_ITEM_CREATED;
private short level;
private short xp;
private short nbrCollections;
private short nbrItems;
private short nbrOfItemsInCollection;
private short GOAL_FOR_COLLECTION;
 */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username;
    private String gmail;
    private String password;

    public GetterAndSetters() {

    }

    public GetterAndSetters(String username, String gmail, String password) {
        this.username = username;
        this.gmail = gmail;
        this.password = password;
    }
}
