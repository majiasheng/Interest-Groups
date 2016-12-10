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
import java.nio.file.Paths;

/**
 *
 * @author Ruoping Lin
 */

/**
 * This class manages user data. Every user has a DataManager object for saving
 * and loading his information
 */
public class DataManager implements Serializable{
    private ArrayList<User>    users;
    private File               userDataFile;
    private User               user;
    private String             userID;
    
    public static final String DEFAULT_PATH = "src/saved/";     
    public static final String JSON_EXTENSION = ".json";     
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String SUBSCRIBED_GROUPS = "SUBSCRIBED_GROUPS";
    public static final String POSTS = "POSTS";
    
    public DataManager() {
        
    }
    
    private void checkExistenceOfUser() {
        
    }
    
    public void saveUserData(User user, Path saveTo) {
        JsonFactory jsonFactory = new JsonFactory();
        this.user = (User) user;
       
        try (OutputStream out = Files.newOutputStream(saveTo)) {
            JsonGenerator generator = jsonFactory.createGenerator(out, JsonEncoding.UTF8);
            generator.writeStartObject();

            // store ID
            generator.writeStringField(USER_ID, user.getId());
            generator.writeStringField(USER_NAME, user.getId());
            // store subscribed groups
            generator.writeFieldName(SUBSCRIBED_GROUPS);
            generator.writeStartArray(user.getSubscribedGroups().size());
            for (String c : user.getSubscribedGroups()) 
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
                    case USER_NAME:
                        jsonParser.nextToken();
                        user.setName(jsonParser.getValueAsString());
                    case SUBSCRIBED_GROUPS:
                        jsonParser.nextToken();
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY)
                            user.subscribedGroup(jsonParser.getValueAsString());
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
    public ArrayList<User> loadAllUsers() throws IOException {
        ArrayList<User> allUsers = new ArrayList<>();
        
        // load all users from data base
        File folder = new File("./src/saved");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            String userFileName = "./src/saved/" + listOfFiles[i].getName();
//            System.out.println("");
            Path userFilePath = Paths.get(userFileName);

            System.out.println(userFileName);
            System.out.println(userFilePath.getFileName());
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser  jsonParser  = jsonFactory.createParser(Files.newInputStream(userFilePath));
            User temp = new User();
            loadUserData(temp,userFilePath);
            allUsers.add(temp);
        }
        
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
    
    //TODO: probably need another method here for loading disuccion groups
    
    
}
