package data;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.fasterxml.jackson.core.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import static java.lang.ProcessBuilder.Redirect.to;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import data.Constants.*;
import java.text.ParseException;

/**
 *
 * @author Ruoping Lin
 */

/**
 * This class manages user data. 
 * server side
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
    public static final String DISCUSSION_GROUP_FILENAME = "DiscussionGroupList";
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
    
//    public static void main(String[] args) throws IOException, ParseException {
//        
//        DataManager dm = new DataManager();
//        
//        // load all posts in java group
//        ArrayList<Post> posts = new ArrayList<>();
//        posts = dm.loadAllPosts(Constants.JAVA);
//        for (int i = 0; i < posts.size(); i++)
//            System.out.println(posts.get(i).getPostByID());
//
//    }
//    
    
    public DataManager() {
        
    }
    
    private void checkExistenceOfUser() {
        
    }
    
    /**
     * Saves an User obj
     * @Usage login. If login id does not exist, create one then, and save id to database
     * @param user 
     * @param saveTo
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
    
    /**
     * Loads an existing user's data
     * @Usage login
     * @param user an empty User object with ID given
     * @param loadFrom
     * @throws IOException 
     */
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
     * @Usage once server starts running
     * @return list of existing users from data base
     */
    public ArrayList<User> loadAllUsers() throws IOException {
        ArrayList<User> allUsers = new ArrayList<>();
        
        // load all users from data base
        File folder = new File("./src/saved");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            String userFileName = "./src/saved/" + listOfFiles[i].getName();
            Path userFilePath = Paths.get(userFileName);

            // System.out.println(userFileName);
            // System.out.println(userFilePath.getFileName());
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser  jsonParser  = jsonFactory.createParser(Files.newInputStream(userFilePath));
            User temp = new User();

            loadUserData(temp, userFilePath);
            allUsers.add(temp);
        }
        
        return allUsers;
    }
    
    /**
     * Loads all existing posts from data base
     * @Usage once server starts running
     * @return a list of posts
     */
    public ArrayList<Post> loadAllPosts() throws IOException, ParseException {
        ArrayList<Post> allPosts = new ArrayList<>();
        
        // load all posts from data base
        File folder = new File(POST_LIST_PATH);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            String postFileName = POST_LIST_PATH + listOfFiles[i].getName();
            Path postFilePath = Paths.get(postFileName);

            JsonFactory jsonFactory = new JsonFactory();
            JsonParser  jsonParser  = jsonFactory.createParser(Files.newInputStream(postFilePath));
            Post post = new Post();
            loadPost(post, postFilePath);
            allPosts.add(post);
        }
        
        return allPosts; 
    }
    
    /**
     * Loads all posts under a particular group from data base
     * @Usage rg, reads "gname" group
     * @return a list of posts
     */
    public ArrayList<Post> loadAllPosts(String groupName) throws IOException, ParseException {
        ArrayList<Post> allPostsInDB = new ArrayList<>();
        ArrayList<Post> allPosts     = new ArrayList<>();
        allPostsInDB = loadAllPosts();
        
        for (Post post: allPostsInDB)
            if (post.getGroupName().equals(groupName))
                allPosts.add(post);
        
        return allPosts; 
    }
    
    /**
     * Loads a post
     * @param post post with ID given
     * @param loadFrom
     * @throws IOException
     * @throws ParseException parses String to SimpleDateFormat
     */
    public void loadPost(Post post, Path loadFrom) throws IOException, ParseException {
        
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser  jsonParser  = jsonFactory.createParser(Files.newInputStream(loadFrom));

        this.post = (Post) post;
        
        while(!jsonParser.isClosed()) {
            JsonToken token = jsonParser.nextToken();
            if (JsonToken.FIELD_NAME.equals(token)) {
                String fieldname = jsonParser.getCurrentName();
                switch (fieldname) {
                    case GROUP_NAME:
                        jsonParser.nextToken();
                        post.setGroupName(jsonParser.getValueAsString());
                        break;
                    case GROUP_ID:
                        jsonParser.nextToken();
                        post.setGroupID(jsonParser.getValueAsString());
                        break;    
                    case POST_ID:
                        jsonParser.nextToken();
                        post.setPostID(jsonParser.getValueAsString());
                        break;
                    case AUTHOR:
                        jsonParser.nextToken();
                        post.setPostAuthor(jsonParser.getValueAsString());
                        break;
                    case TIMESTAMP:
                        jsonParser.nextToken();
                        post.setPostedDate(new SimpleDateFormat().parse(jsonParser.getValueAsString()));
                        break;
                    case SUBJECT:
                        jsonParser.nextToken();
                        post.setPostSubject(jsonParser.getValueAsString());
                        break;
                    case CONTENT:
                        jsonParser.nextToken();
                        post.setPostContent(jsonParser.getValueAsString());
                        break;
                    default:
                        throw new JsonParseException(jsonParser, "Unable to load JSON data");
                }
            }
        }
        
    } 
    
    /**
     * Saves newly created post
     * @Usage subcommand of rg: creates a new post
     * @param saveTo 
     */
    public void SaveCreatedPost(Post post, Path saveTo) {
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
     * Saves posts with newly created posts
     * @param allPosts 
     */
    public void saveAllPosts(ArrayList<Post> allPosts) {
        
    }
    
    /**
     * Loads all existing discussion from data base
     * @Usage subcommand of ag: lists all groups
     * @return list of existing discussion groups from data base
     */
    public ArrayList<DiscussionGroup> loadDiscussionGroups() throws IOException {
        ArrayList<DiscussionGroup> allGroups = new ArrayList<>();
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser  jsonParser  = jsonFactory.createParser(Files.newInputStream(new File(DISCUSSION_GROUP_LIST_PATH + DISCUSSION_GROUP_FILENAME + JSON_EXTENSION).toPath()));

        int index = 0;
        while(!jsonParser.isClosed()) {
            JsonToken token = jsonParser.nextToken();
            if (JsonToken.FIELD_NAME.equals(token)) {
                    jsonParser.nextToken();
                    allGroups.add(new DiscussionGroup(jsonParser.getValueAsString()));
                    index++;
            }
        }
        return allGroups;
    }
    
    /**
     * Saves all discussion groups
     * @Usage 
     * @param saveTo 
     */
    public void saveDiscussionGroups(Path saveTo) {
        JsonFactory jsonFactory = new JsonFactory();
        
        try (OutputStream out = Files.newOutputStream(saveTo)) {
            JsonGenerator generator = jsonFactory.createGenerator(out, JsonEncoding.UTF8);
            generator.writeStartObject();

            // Stores subscribed groups
            generator.writeFieldName(GROUP_NAME);
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
    
    
    

    
    
    
    
}
