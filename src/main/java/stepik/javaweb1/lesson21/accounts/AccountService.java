package stepik.javaweb1.lesson21.accounts;

import stepik.javaweb1.lesson21.type.plain.LongId;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountService {

    private final Map <String, UserProfile> loginToProfile;
    private final Map <String
                            //LongId<UserProfile>
                                    ,UserProfile> sessionIdToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySessionId(String
                                          //LongId<UserProfile>
                                                  sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String
                           //LongId<UserProfile>
                                   sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String
                              //LongId<UserProfile>
                                      sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

/*
    HashMap<String, String> userSessionHashMap = new HashMap<>();

    private String generateUUID(String userID) {
        UUID uuID = UUID.randomUUID();
        String sid = uuID.toString();
        userSessionHashMap = new HashMap<String, String>();
        userSessionHashMap.put(userID, sid);
        return sid;
    }
*/

}
