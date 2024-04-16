package com.example.evoting500;

public class voteData {

    private int votes;
    private String name;
    private String key;

    public voteData(String name, int votes) {
        this.name = name;
        this.votes = votes;
    }

    public voteData() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}

