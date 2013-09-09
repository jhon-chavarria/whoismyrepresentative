package com.example.rain.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Jhon on 02/09/13.
 */
@DatabaseTable(tableName = "personsTable")
public class Person {

    @DatabaseField(id = true, generatedId = false)
    private String id;
    @DatabaseField
    private  String name;
    @DatabaseField
    private  String state;
    @DatabaseField
    private  String district;
    @DatabaseField
    private  String phone;
    @DatabaseField
    private  String office;
    @DatabaseField
    private String link;
    @DatabaseField
    private String party;
    @DatabaseField
    private String type;


    public Person() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getWebsite() {
        return link;
    }

    public void setWebsite(String website) {
        this.link = website;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
