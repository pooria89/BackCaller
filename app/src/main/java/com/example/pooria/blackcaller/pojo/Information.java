
package com.example.pooria.blackcaller.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Information {


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("family")
    @Expose
    private String family;
    @SerializedName("phone")
    @Expose
    private String phone;
    /*private String name, family;
    private String phone;*/

    private Information(Builder builder) {
        setName(builder.name);
        setFamily(builder.family);
        setPhone(builder.phone);
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Information() {
    }


    public static final class Builder {
        private String name;
        private String family;
        private String phone;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder family(String val) {
            family = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Information build() {
            return new Information(this);
        }
    }
}
