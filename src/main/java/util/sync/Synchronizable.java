package pl.sggw.util.sync;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Daniel
 * @date 03.01.13
 */

public abstract class Synchronizable implements Serializable {

	protected Long id;

	protected String googleId;

	protected Date updatedTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public Date getUpdatedDate() {
		return updatedTime;
	}

	public Long getUpdatedTimeInMs() {
		Long updatedTimeInMs = null;
		if (updatedTime != null) {
			updatedTimeInMs = updatedTime.getTime();
		}
		return updatedTimeInMs;
	}

	public void setUpdatedDate(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Synchronizable that = (Synchronizable) o;

		if (googleId != null ? !googleId.equals(that.googleId) : that.googleId != null) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (googleId != null ? googleId.hashCode() : 0);
		return result;
	}
}
