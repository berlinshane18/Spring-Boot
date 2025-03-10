package com.example.ResumeGenerator;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Data;

@Embeddable
@Data
public class Experience {
    String job_title,company,start_date,end_date;
    String[] responsibilities,skills;

    public Experience(String job_title, String company, String start_date, String end_date, String[] responsibilities, String[] skills) {
        this.job_title = job_title;
        this.company = company;
        this.start_date = start_date;
        this.end_date = end_date;
        this.responsibilities = responsibilities;
        this.skills = skills;
    }
    public Experience(){}

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String[] getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String[] responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }
}
