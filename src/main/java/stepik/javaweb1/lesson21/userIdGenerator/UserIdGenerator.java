package stepik.javaweb1.lesson21.userIdGenerator;

import stepik.javaweb1.lesson21.accounts.UserProfile;
import stepik.javaweb1.lesson21.type.plain.LongId;

public class UserIdGenerator <T>{

    private static long id = -1;
    public LongId<UserProfile> getAndIncrement(){
        return new LongId(++id);
    };
}
