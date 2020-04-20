package elte.phyllis.community.community.dto;

public class GithubUser {
    private String name;
    private Long id;//万一github将来的用户暴增
    private String bio;//用户描述

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
