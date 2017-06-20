package com.luv2code.springdemo.constants;

public enum ActivityEmail {
	
	CREATE("C"),
    UPDATE("U"),
    DELETE("D"),
    INUSE("I"),
    RELEASE("R");
	
	private String activityFlag;	
	
	ActivityEmail(String activityFlag) {
        this.activityFlag = activityFlag;
    }

    public String activityFlag() {
        return activityFlag;
    }

}
