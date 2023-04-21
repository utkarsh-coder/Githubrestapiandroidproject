package com.android.githubrepomanager;

public class Repo {

    private int id;
    private String ownerName;
    private String repoName;
    private String description;
    private String url;

    public Repo(String ownerName, String repoName, String description, String url) {
        this.ownerName = ownerName;
        this.repoName = repoName;
        this.description = description;
        this.url = url;
    }
    public Repo(int id, String ownerName, String repoName, String description, String url) {
        this.id = id;
        this.ownerName = ownerName;
        this.repoName = repoName;
        this.description = description;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
