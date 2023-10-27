package com.group.Gestion.src.DTOs;

import com.group.Gestion.src.model.Employee;

public class LoginResponseDto {
    private Employee employee;
    private String jwt;

    public LoginResponseDto(Employee employee, String jwt) {
        this.employee = employee;
        this.jwt = jwt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getJwt() {
        return jwt;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
