package com.amaxilatis.ehr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Dimitrios Amaxilatis.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient {
    private String patientId;
    private String nationalHealthcarePatientId;
    private String givenName;
    private String familyName;
    private String dateOfBirth;
    private String street;
    private String city;
    private String state;
    private String country;
    private String email;
    private String nameOfTheHcpLegalOrganization;
    private String hcpTelephoneNo;
    private String hcpEmail;
    private String cpGivenName;
    private String cpFamilyName;
    private String cpEmail;
    private String bloodGroupTestDate;
    private String carePlan;
    private String genderCoding;
    private String numberOfStreet;
    private String postCode;
    private String telephoneNo;
    private String cpRoleCoding;
    private String cpTelephoneNo;
    private String bloodGroupCoding;


    public Patient() {
        patientId = "novalue";
        givenName = "novalue";
        familyName = "novalue";
        street = "novalue";
        city = "novalue";
        state = "novalue";
        country = "novalue";
        email = "novalue";
        nameOfTheHcpLegalOrganization = "novalue";
        hcpEmail = "novalue";
        cpGivenName = "novalue";
        cpFamilyName = "novalue";
        cpEmail = "novalue";
        nationalHealthcarePatientId = "0";
        hcpTelephoneNo = "0";
        carePlan = "1";
        numberOfStreet = "0";
        postCode = "0";
        telephoneNo = "0";
        cpTelephoneNo = "0";
        dateOfBirth = "novalue";
        bloodGroupTestDate = "novalue";
        genderCoding = "1";
        bloodGroupCoding = "1";
        cpRoleCoding = "1";
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getNationalHealthcarePatientId() {
        return nationalHealthcarePatientId;
    }

    public void setNationalHealthcarePatientId(String nationalHealthcarePatientId) {
        this.nationalHealthcarePatientId = nationalHealthcarePatientId;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameOfTheHcpLegalOrganization() {
        return nameOfTheHcpLegalOrganization;
    }

    public void setNameOfTheHcpLegalOrganization(String nameOfTheHcpLegalOrganization) {
        this.nameOfTheHcpLegalOrganization = nameOfTheHcpLegalOrganization;
    }

    public String getHcpTelephoneNo() {
        return hcpTelephoneNo;
    }

    public void setHcpTelephoneNo(String hcpTelephoneNo) {
        this.hcpTelephoneNo = hcpTelephoneNo;
    }

    public String getHcpEmail() {
        return hcpEmail;
    }

    public void setHcpEmail(String hcpEmail) {
        this.hcpEmail = hcpEmail;
    }

    public String getCpGivenName() {
        return cpGivenName;
    }

    public void setCpGivenName(String cpGivenName) {
        this.cpGivenName = cpGivenName;
    }

    public String getCpFamilyName() {
        return cpFamilyName;
    }

    public void setCpFamilyName(String cpFamilyName) {
        this.cpFamilyName = cpFamilyName;
    }

    public String getCpEmail() {
        return cpEmail;
    }

    public void setCpEmail(String cpEmail) {
        this.cpEmail = cpEmail;
    }

    public String getBloodGroupTestDate() {
        return bloodGroupTestDate;
    }

    public void setBloodGroupTestDate(String bloodGroupTestDate) {
        this.bloodGroupTestDate = bloodGroupTestDate;
    }

    public String getCarePlan() {
        return carePlan;
    }

    public void setCarePlan(String carePlan) {
        this.carePlan = carePlan;
    }

    public String getGenderCoding() {
        return genderCoding;
    }

    public void setGenderCoding(String genderCoding) {
        this.genderCoding = genderCoding;
    }

    public String getNumberOfStreet() {
        return numberOfStreet;
    }

    public void setNumberOfStreet(String numberOfStreet) {
        this.numberOfStreet = numberOfStreet;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getCpRoleCoding() {
        return cpRoleCoding;
    }

    public void setCpRoleCoding(String cpRoleCoding) {
        this.cpRoleCoding = cpRoleCoding;
    }

    public String getCpTelephoneNo() {
        return cpTelephoneNo;
    }

    public void setCpTelephoneNo(String cpTelephoneNo) {
        this.cpTelephoneNo = cpTelephoneNo;
    }

    public String getBloodGroupCoding() {
        return bloodGroupCoding;
    }

    public void setBloodGroupCoding(String bloodGroupCoding) {
        this.bloodGroupCoding = bloodGroupCoding;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", nationalHealthcarePatientId='" + nationalHealthcarePatientId + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", nameOfTheHcpLegalOrganization='" + nameOfTheHcpLegalOrganization + '\'' +
                ", hcpTelephoneNo='" + hcpTelephoneNo + '\'' +
                ", hcpEmail='" + hcpEmail + '\'' +
                ", cpGivenName='" + cpGivenName + '\'' +
                ", cpFamilyName='" + cpFamilyName + '\'' +
                ", cpEmail='" + cpEmail + '\'' +
                ", bloodGroupTestDate='" + bloodGroupTestDate + '\'' +
                ", carePlan='" + carePlan + '\'' +
                ", genderCoding='" + genderCoding + '\'' +
                ", numberOfStreet='" + numberOfStreet + '\'' +
                ", postCode='" + postCode + '\'' +
                ", telephoneNo='" + telephoneNo + '\'' +
                ", cpRoleCoding='" + cpRoleCoding + '\'' +
                ", cpTelephoneNo='" + cpTelephoneNo + '\'' +
                ", bloodGroupCoding='" + bloodGroupCoding + '\'' +
                '}';
    }
}
