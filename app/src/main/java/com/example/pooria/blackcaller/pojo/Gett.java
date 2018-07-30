
package com.example.pooria.blackcaller.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Gett<G> {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("family")
    @Expose
    private String family;
    @SerializedName("phone")
    @Expose
    private String phone;

    private Gett(Builder builder) {
        setName(builder.name);
        setFamily(builder.family);
        setPhone(builder.phone);
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Gett{" +
                "name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", phone='" + phone + '\'' +
                '}';
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

        public Gett build() {
            return new Gett(this);
        }
    }

    public Gett() {
    }

    public Gett(String name, String family, String phone) {
        this.name = name;
        this.family = family;
        this.phone = phone;
    }
}
