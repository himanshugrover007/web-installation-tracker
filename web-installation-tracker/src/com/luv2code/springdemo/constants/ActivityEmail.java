package com.luv2code.springdemo.constants;

public enum ActivityEmail {
	
	CREATE("C"),
    UPDATE("U"),
    DELETE("D"),
    PERMANENTDELETE("P"),
    INUSE("I"),
    RELEASE("R"),
    DEMOBLOCK("DB"),
	DEMORELEASE("DR");
	
	private String activityFlag;	
	
	ActivityEmail(String activityFlag) {
        this.activityFlag = activityFlag;
    }

    public String activityFlag() {
        return activityFlag;
    }

}
