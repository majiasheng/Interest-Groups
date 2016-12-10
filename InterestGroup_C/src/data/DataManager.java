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
    public static final String POST_ID = "POST_ID";
    
//    public static void main(String[] args) throws IOException {
//        User user = new User();
//        File file = new File(DEFAULT_PATH + "333" + JSON_EXTENSION);
//        DataManager dm = new DataManager();
//        dm.loadUserData(user, file.toPath());
//        
//        System.out.println(user.getId());
//    }
//    
    
    public DataManager() {
        
    }
    
    private void checkExistenceOfUser() {
        
    }
    
    public void saveUserData(User user, Path saveTo) {
        JsonFactory jsonFactory = new JsonFactory();
        this.user = (User) user;
        this.post = (Post) post;
       
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
            
            // Stores posts that have been read by IDs
            generator.writeFieldName(POST_ID);
            generator.writeStartArray(user.getSubscribedGroups().size());
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
                    case POST_ID:
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
    
    public void saveDiscussionGroups(Path saveTo) {
        JsonFactory jsonFactory = new JsonFactory();
        
        
       
        try (OutputStream out = Files.newOutputStream(saveTo)) {
            JsonGenerator generator = jsonFactory.createGenerator(out, JsonEncoding.UTF8);
            generator.writeStartObject();

            // Stores subscribed groups
            generator.writeFieldName("GROUP_NAME");
            generator.writeStartArray(user.getSubscribedGroups().size());
            for (String c : user.getSubscribedGroups()) 
                generator.writeString(c);
            generator.writeEndArray();
            
            // Stores posts that have been read by IDs
            generator.writeFieldName(POST_ID);
            generator.writeStartArray(user.getSubscribedGroups().size());
            for (String c : user.getReadPosts()) 
                generator.writeString(c);
            generator.writeEndArray();
            
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
     * Saves posts with newly created posts
     * @param allPosts 
     */
    public void saveAllPosts(ArrayList<Post> allPosts) {
        
    }
    
    //TODO: probably need another method here for loading disuccion groups
    
    
}
