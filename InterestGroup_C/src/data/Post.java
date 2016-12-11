package data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Ruoping Lin
 */

/**
 * Manager a post
 */
public class Post  implements Serializable{
    private String groupName;       // Group that the post belongs to
    private String subject;         // Post's subject
    private String author;          // Post's author
    private String content;        
    private String postedDate;      // Timestamp    
    private String postID;          // Post is identified by ID

    /**
    * If a Post object is created by using the default constructor,
    * a post ID must be specified by using the setter method
    */
    public Post() {

    }
    
    /**
     * Every post is assigned a unique ID
     * @param postID post's ID
     */
    public Post(String postID) {
        this.postID = postID;
    }
    /** 
     * Group that this post belongs to
     * @return group
     */
    public String getGroupName() {
        return this.groupName;
    }
    public void setGroup(String groupName) {
        this.groupName = groupName;
    }
    
    /**
     * Retrieves a post by ID
     * @return 
     */
    public String getPostByID() {
        return this.postID;
    }
    public void setPostID(String postID) {
        this.postID = postID;
    }
    
    public String getPostSubject() {
        return this.subject;
    }
    public void setPostSubject(String subject) {
        this.subject = subject;
    }
    /**
     * Gets post's author
     * @return 
     */
    public String getPostAuthor() {
        return this.author;
    }
    public void setPostAuthor(String author) {
        this.author = author;
    }
    
    /**
     * 
     * @return 
     */
    public String getPostContent() {
        return this.content;
    }
    
    /**
     * Writes content of a post
     * For subcommand rgp (post to group) of rg (read group)
     * @param content 
     */
    public void setPostContent(String content) {
        this.content = content;
    }
    
    /**
     * When the post is created
     * @return a string representation of posted date
     */
    public String getPostedDate() {
        return this.postedDate;
    }
    /**
     * Takes a Date obj, converts it to String
     * @param curDate 
     */
    public void setPostedDate(Date curDate) {
        SimpleDateFormat format = new SimpleDateFormat();
        String DateToString = format.format(curDate);
        this.postedDate = DateToString;
    }
    

}