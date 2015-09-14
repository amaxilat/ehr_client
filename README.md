[![Build Status](https://travis-ci.org/amaxilat/ehr_client.svg?branch=master)](https://travis-ci.org/amaxilat/ehr_client)

# ehr_client

A Java Client for the FI-STAR [Electronic Health Record](http://fistarcatalogue.fiware.eng.it/enablers/ehr) (EHR) specific enabler.

## implemented functions 

1. add PatientData
2. add AdmissionData
3. add Allergies
2. update PatientData


## Maven Config

To use this library in your maven project you need to add:

    <dependencies>
        <dependency>
            <groupId>com.amaxilatis</groupId>
            <artifactId>ehr-client</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <repositories>
        <repository>
            <id>sparkworks</id>
            <url>http://nexus.sparkworks.net/nexus/content/repositories/snapshots</url>
        </repository>
    </repositories>
