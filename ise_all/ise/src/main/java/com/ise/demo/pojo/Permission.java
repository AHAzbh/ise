package com.ise.demo.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="permission")
@IdClass(PermissionId.class)
public class Permission {
    @Id
    private String utype;
    @Id
    private String perurl;
    private String pername;
    private String pertype;

    public String getPertype() {
        return pertype;
    }
    public void setPertype(String pertype) {
        this.pertype = pertype;
    }
    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getPerurl() {
        return perurl;
    }

    public void setPerurl(String perurl) {
        this.perurl = perurl;
    }

    public String getPername() {
        return pername;
    }

    public void setPername(String pername) {
        this.pername = pername;
    }
}
