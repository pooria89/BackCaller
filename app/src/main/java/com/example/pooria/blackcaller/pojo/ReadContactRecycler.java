package com.example.pooria.blackcaller.pojo;

public class ReadContactRecycler {
    private String name;
    private String phone;

    public ReadContactRecycler() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static final class Builder {
        private String name;
        private String phone;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

    }
}
