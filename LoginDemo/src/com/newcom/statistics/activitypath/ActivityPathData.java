package com.newcom.statistics.activitypath;

public class ActivityPathData {
	private int activityNum;//页面编号
	private long activityDuration;//页面访问时长
	private String startTime;//软件启动时间
	private String appVersion;//软件版本号

	public int getActivityNum() {
		return activityNum;
	}
	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}
	public long getActivityDuration() {
		return activityDuration;
	}
	public void setActivityDuration(long activityDuration) {
		this.activityDuration = activityDuration;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
}
