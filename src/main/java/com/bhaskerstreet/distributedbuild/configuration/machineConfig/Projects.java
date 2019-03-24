package com.bhaskerstreet.distributedbuild.configuration.machineConfig;


import org.springframework.stereotype.Component;

@Component
public class Projects {


    private String artifactId;
    private String groupId;
    private String path;
    private int executionOrder;
    private String copyBuildFromPath;
    private String copyBuildToPath;
    


    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(int executionOrder) {
        this.executionOrder = executionOrder;
    }

	public String getCopyBuildFromPath() {
		return copyBuildFromPath;
	}

	public void setCopyBuildFromPath(String copyBuildFromPath) {
		this.copyBuildFromPath = copyBuildFromPath;
	}

	public String getCopyBuildToPath() {
		return copyBuildToPath;
	}

	public void setCopyBuildToPath(String copyBuildToPath) {
		this.copyBuildToPath = copyBuildToPath;
	}

	
    
    
}
