package com.app.sample.model;

public class JobTab {

    String JobID = "";

    String JobName = "";

    String JobDesc = "";

    public String getMaterialCount() {
        return MaterialCount;
    }

    public void setMaterialCount(String materialCount) {
        MaterialCount = materialCount;
    }

    String MaterialCount = "";

    public String getJobID() {
        return JobID;
    }

    public void setJobID(String jobID) {
        JobID = jobID;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public String getJobDesc() {
        return JobDesc;
    }

    public void setJobDesc(String jobDesc) {
        JobDesc = jobDesc;
    }
}
