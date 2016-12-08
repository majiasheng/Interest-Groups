package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author majiasheng
 */

/**
 * 
 */
public class Post implements Serializable{
	private String group;
	private String subject;
	private String author;
	private String date;
        private String content;

	public Post() {
            
	}

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}