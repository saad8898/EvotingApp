package com.example.evoting500;

public class CandidatesData {

    private String partyName;
    private String partyHead;
    private String partyLogo;
    private String key;

    public CandidatesData() {
    }

    public CandidatesData(String partyName, String partyHead, String partyLogo) {
        this.partyName = partyName;
        this.partyHead = partyHead;
        this.partyLogo = partyLogo;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyHead() {
        return partyHead;
    }

    public void setPartyHead(String partyHead) {
        this.partyHead = partyHead;
    }

    public String getPartyLogo() {
        return partyLogo;
    }

    public void setPartyLogo(String partyLogo) {
        this.partyLogo = partyLogo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
