package resources;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         Concert class
 *
 */
public class Concert {

	private String concertname;
	private Date date;
	private Timestamp createdDate;
	private String description;
	private int completed;
	private String artistname;

	public Concert(String concertname, Date date, Timestamp createdDate, String description, int completed,
			String artistname) {
		super();
		this.concertname = concertname;
		this.date = date;
		this.createdDate = createdDate;
		this.description = description;
		this.completed = completed;
		this.artistname = artistname;
	}

	public String getConcertname() {
		return concertname;
	}

	public void setConcertname(String concertname) {
		this.concertname = concertname;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	public String getArtistname() {
		return artistname;
	}

	public void setArtistname(String artistname) {
		this.artistname = artistname;
	}

	@Override
	public String toString() {
		return "Concert [concertname=" + concertname + ", date=" + date + ", createdDate=" + createdDate
				+ ", description=" + description + ", completed=" + completed + ", artistname=" + artistname + "]";
	}

}
