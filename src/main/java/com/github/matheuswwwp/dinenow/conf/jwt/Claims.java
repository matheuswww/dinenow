package com.github.matheuswwwp.dinenow.conf.jwt;

import java.util.List;
import java.util.Objects;

public class Claims {
    private String user_id;
    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Claims claims = (Claims) o;
        return Objects.equals(getUser_id(), claims.getUser_id()) && Objects.equals(getRoles(), claims.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser_id(), getRoles());
    }
}
