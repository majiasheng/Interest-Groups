package data;


import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import com.fasterxml.jackson.core.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import static java.lang.ProcessBuilder.Redirect.to;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import data.Constants.*;

/**
 *
 * @author Ruoping Lin
 */

/**
 * This class manages user data. 
 * Every user has a DataManager object for saving and loading his information
 */
public class DataManager implements Serializable{
    private ArrayList<User>    users;
    private File               userDataFile;
    private User               user;
    private Post               post;
    private String             userID;
    
    // For User obj under saved directory
    public static final String DEFAULT_PATH = "src/saved/";  
    public static final String DISCUSSION_GROUP_LIST_PATH = "src/DiscussionGroupList/";
    public static final String POST_LIST_PATH = "src/PostList/";
    public static final String JSON_EXTENSION = ".json";     
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String SUBSCRIBED_GROUPS = "SUBSCRIBED_GROUPS";
    public static final String READ_POST_ID = "READ_POST_ID";
    
    public static final String GROUP_ID = "GROUP_ID";
    public static final String GROUP_NAME = "GROUP_NAME";
    public static final String POST_ID = "POST_ID";
    public static final String AUTHOR = "AUTHOR";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String SUBJECT = "SUBJECT";
    public static final String CONTENT = "CONTENT";
    
    public static void main(String[] args) throws IOException {
        
//        DataManager dm = new DataManager();
        // Creates and saves a post
//       Post post1  = new Post("555");     
//       post1.setGroup("math.statistics");
//       post1.setGroupID("15");
//       post1.setPostSubject("Statistics and math");
//       post1.setPostContent("There aren't many theorems in statistics that are likely to be of much interest to a pure mathematician. Many make use of math that's a century or more old, even if they weren't proven until relatively recently. Examples would include Cochran's theorem or the Central limit theorem, both of which are relatively young, dating only to the 1940s or so for rigorous proof, but are basically just variations on Fourier transforms, with some linear algebra and power series thrown in for good measure. The Frisch–Waugh–Lovell theorem is really just an application of linear projectors and the Eckart-Young theorem (Low-rank approximation) is just the SVD; the modern world would come to a screeching halt without it. So we're talking about cutting edge mathematics... in the 19th Century. (To be clear, I'm not saying this is bad work. It's not. It's very important.) Parts of statistics such as time series or information theory make use of deeper math, but even then I suspect most of the results wouldn't be that impressive to a pure mathematician. ");
//       post1.setPostAuthor("333");
//       post1.setPostedDate(new Date());
//       dm.savePost(post1, new File(POST_LIST_PATH + post1.getPostByID() + JSON_EXTENSION).toPath());


    }
    
    
    public DataManager() {
        
    }
    
    private void checkExistenceOfUser() {
        
    }
    
    /**
     * Saves an User obj
     * @param user User obj being saved
     * @param saveTo Path: "DEFAULT_PATH + user.getId() + JSON_EXTENSION"
     */
    public void saveUserData(User user, Path saveTo) {
        JsonFactory jsonFactory = new JsonFactory();
        this.user = (User) user;
       
        try (OutputStream out = Files.newOutputStream(saveTo)) {
            JsonGenerator generator = jsonFactory.createGenerator(out, JsonEncoding.UTF8);
            generator.writeStartObject();

            // Stores ID
            generator.writeStringField(USER_ID, user.getId());
//            generator.writeStringField(USER_NAME, user.getId());
            
            // Stores subscribed groups
            generator.writeFieldName(SUBSCRIBED_GROUPS);
            generator.writeStartArray(user.getSubscribedGroups().size());
            for (String c : user.getSubscribedGroups()) 
                generator.writeString(c);
            generator.writeEndArray();
            
            // Stores posts that have been read by this user
            generator.writeFieldName(READ_POST_ID);
            generator.writeStartArray(user.getReadPosts().size());
            for (String c : user.getReadPosts()) 
                generator.writeString(c);
            generator.writeEndArray();
            
            generator.writeEndObject();
            generator.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void loadUserData(User user, Path loadFrom) throws IOException {
        
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser  jsonParser  = jsonFactory.createParser(Files.newInputStream(loadFrom));

        this.user = (User) user;
        
        while(!jsonParser.isClosed()) {
            JsonToken token = jsonParser.nextToken();
            if (JsonToken.FIELD_NAME.equals(token)) {
                String fieldname = jsonParser.getCurrentName();
                switch (fieldname) {
                    case USER_ID:
                        jsonParser.nextToken();
                        user.setId(jsonParser.getValueAsString());
                        break;
                    case SUBSCRIBED_GROUPS:
                        jsonParser.nextToken();
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY)
                            user.subscribeGroup(jsonParser.getValueAsString());
                        break;
                    case READ_POST_ID:
                        jsonParser.nextToken();
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY)
                            user.readPost(jsonParser.getValueAsString());
                        break;
                        
                    default:
                        throw new JsonParseException(jsonParser, "Unable to load JSON data");
                }
            }
        }
        
    }
    
    /**
     * Loads all existing users from data base
     * @return list of existing users from data base
     */
    public ArrayList<User> loadAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        
        //TODO: load all users from data base
        
        return allUsers;
    }
    
    /**
     * Loads all existing discussion from data base
     * @return list of existing discussion groups from data base
     */
    public ArrayList<DiscussionGroup> loadAllGroups() {
        ArrayList<DiscussionGroup> allGroups = new ArrayList<>();
        
        //TODO: load all discussion groups from data base
        //TODO: load all posts pertaining to the discussion groups and construct a wholesome list of groups
        
        return allGroups;
    }
    
    /**
     * test function
     * @param saveTo Path: "POST_LIST_PATH + post.getPostByID() + JSON_EXTENSION"
     */
    public void savePost(Post post, Path saveTo) {
       this.post = (Post) post;
        
       JsonFactory jsonFactory = new JsonFactory();
      
        try (OutputStream out = Files.newOutputStream(saveTo)) {
            JsonGenerator generator = jsonFactory.createGenerator(out, JsonEncoding.UTF8);
            
            generator.writeStartObject();

            generator.writeStringField(POST_ID, post.getPostByID());
            generator.writeStringField(GROUP_NAME, post.getGroupName());
            generator.writeStringField(GROUP_ID, post.getGroupID());
            generator.writeStringField(AUTHOR, post.getPostAuthor());
            generator.writeStringField(TIMESTAMP, post.getPostedDate());
            generator.writeStringField(SUBJECT, post.getPostSubject());
            generator.writeStringField(CONTENT, post.getPostContent());
            
            generator.writeEndObject();
            generator.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Loads all existing posts from data base
     * @return a list of posts
     */
    public ArrayList<Post> loadAllPosts() {
        ArrayList<Post> allPosts = new ArrayList<>();
        
        return allPosts; 
    }
    
    /**
     * Loads all existing posts for the specified group from data base
     * @return a list of posts
     */
    public ArrayList<Post> loadAllPosts(String groupName) {
        ArrayList<Post> allPosts = new ArrayList<>();
        
        return allPosts; 
    }
    
    /**
     * Saves posts with newly created posts
     * @param allPosts 
     */
    public void saveAllPosts(ArrayList<Post> allPosts) {
        
    }
    
    //TODO: probably need another method here for loading disuccion groups
    
    
}
