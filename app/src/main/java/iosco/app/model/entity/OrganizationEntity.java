package iosco.app.model.entity;

import java.io.Serializable;

import iosco.app.utils.Helpers;

/**
 * Created by usuario on 17/02/2016.
 */
public class OrganizationEntity  implements Serializable {
    private String Name;
    private String Acronym;
    private JurisdictionEntity Jurisdiction;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAcronym() {
        return Acronym;
    }

    public void setAcronym(String acronym) {
        Acronym = acronym;
    }

    public JurisdictionEntity getJurisdiction() {
        return Jurisdiction;
    }

    public void setJurisdiction(JurisdictionEntity jurisdiction) {
        Jurisdiction = jurisdiction;
    }
}
