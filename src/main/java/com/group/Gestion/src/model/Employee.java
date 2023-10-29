package com.group.Gestion.src.model;


import com.group.Gestion.src.utilities.DateTimeProvider;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="employee")
@Builder
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    private Status status=Status.DEHORS;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = {@JoinColumn(name="employee_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WorkDay> workDays;

    public Employee(Employee employee) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        Instant now = Instant.now();
        StringBuilder sb = new StringBuilder();

        sb.append(now).append(" First Name: ").append(this.firstName).append("\n");
        sb.append(now).append(" Last Name: ").append(this.lastName).append("\n");
        sb.append(now).append(" Email: ").append(this.email).append("\n");
        sb.append(now).append(" Role: ").append(this.role).append("\n");
        sb.append(now).append(" Authorities: ").append(this.authorities).append("\n");
        sb.append(now).append(" Status: ").append(this.status).append("\n");

        return sb.toString();
    }

    public boolean isStatusEnTravail(){
        if(this.status == Status.EN_TRAVAIL){
            return true;
        }
        return false;
    }

    public boolean isStatusEnPause(){
        if(this.status == Status.EN_PAUSE){
            return true;
        }
        return false;
    }

    public boolean isStatusDehors(){
        if(this.status == Status.DEHORS ||this.status==null){
            return true;
        }
        return false;
    }

    public boolean isWorkDayEnded(){
        List<WorkDay> workDays = this.getWorkDays();
        for(WorkDay workDay : workDays){
            if(workDay.getDate().equals(DateTimeProvider.getCurrentDate())){
                if(workDay.getEndTime()!=null){
                    return true;
                }
            }

        }
        return false;
    }















}
