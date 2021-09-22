package com.my.time.accounting.entity;

import java.util.Objects;

public class Team {
    private long teamId;
    private String name;
    private String company;
    private String description;
    private long administratorId;

    public static Team createTeam(String name, String company, String description, long administratorId){
        Team team = new Team();
        team.setName(name);
        team.setCompany(company);
        team.setDescription(description);
        team.setAdministratorId(administratorId);
        return team;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(long administratorId) {
        this.administratorId = administratorId;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", description='" + description + '\'' +
                ", administratorId=" + administratorId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return name.equals(team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
