package pl.sggw.google.task;

import com.google.api.client.util.DateTime;
import pl.sggw.task.StateType;
import pl.sggw.util.StringUtils;
import pl.sggw.util.time.DateUtil;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author Daniel
 * @date 03.01.13
 */

public class GoogleTask {

	private String id;

	private String title;

	private String notes;

	private Date dueDateWithoutTime;

	private Date updatedDate;

	private StateType status;

	public GoogleTask(String id, String title) {
		this.id = id;
		this.title = title;
	}

	public GoogleTask(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public boolean isPersist() {
		return id != null;
	}

	public String getTitle() {
		return title;
	}

	public String getNotes() {
		return notes;
	}

	public Date getDueDateWithoutTime() {
		return dueDateWithoutTime;
	}

	public DateTime getDueDateTime() {
		if (dueDateWithoutTime == null) {
			return null;
		}
		dueDateWithoutTime.setHours(1);
		return new DateTime(dueDateWithoutTime, TimeZone.getDefault());
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public DateTime getUpdatedDateTime() {
		if (updatedDate == null) {
			return null;
		}
		return new DateTime(updatedDate, TimeZone.getDefault());
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNotes(String notes) {
		if (StringUtils.isBlank(notes)) {
			this.notes = "";
		} else {
			this.notes = notes;
		}
	}

	public void setDue(DateTime due) {
		if (due == null) {
			this.dueDateWithoutTime = DateUtil.resetTime(DateUtil.todayWithHour());
			setUpdated(new Date());
		} else {
			this.dueDateWithoutTime = DateUtil.resetTime(new Date(due.getValue()));
		}
	}

	public void setDue(Date due) {
		if (due == null) {
			this.dueDateWithoutTime = DateUtil.resetTime(DateUtil.todayWithHour());
			setUpdated(new Date());
		} else {
			this.dueDateWithoutTime = DateUtil.resetTime(due);
		}
	}

	public void setUpdated(DateTime updated) {
		if (updated != null) {
			this.updatedDate = new Date(updated.getValue());
		}
	}

	public void setUpdated(Date updated) {
		if (updated != null) {
			this.updatedDate = updated;
		}
	}

	public StateType getStatus() {
		return status;
	}

	public String getServerStatus() {
		return status.getServerStatus();
	}

	public void setStatus(StateType status) {
		this.status = status;
	}

	public void setStatus(String serverStatus) {
		if (serverStatus.equals(StateType.DONE.getServerStatus())) {
			status = StateType.DONE;
		} else {
			status = StateType.IN_QUEUE;
		}
	}

}