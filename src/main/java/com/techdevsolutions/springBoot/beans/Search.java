package com.techdevsolutions.springBoot.beans;


import java.util.Objects;

public class Search extends Filter {
    private String term = "";

    public Search() {
    }

    public Search(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

}
