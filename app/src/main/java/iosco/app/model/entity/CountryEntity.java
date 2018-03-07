package iosco.app.model.entity;

import java.io.Serializable;

/**
 * Created by usuario on 17/02/2016.
 */
public class CountryEntity  implements Serializable {
    private String Name;
    private String Code;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
