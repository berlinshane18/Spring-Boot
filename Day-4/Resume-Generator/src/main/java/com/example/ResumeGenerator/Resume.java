package com.example.ResumeGenerator;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "resumes")
@Data
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    private List<String> skills;

    @Embedded
    private Education education;

    @Embedded
    private Contact contact;

    @Embedded
    private Experience experience;

    public Resume(String name, List<String> skills, Education education, Contact contact, Experience experience) {
        this.name = name;
        this.skills = skills;
        this.education = education;
        this.contact = contact;
        this.experience = experience;
    }

    public Resume() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }
}
