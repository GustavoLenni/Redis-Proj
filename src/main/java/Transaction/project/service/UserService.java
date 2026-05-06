package Transaction.project.service;
import Transaction.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;


@Service
public class UserService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public User createUser(User user){
        String id = generateUserId();
        user.setId(id);
        String key = generateUserKey(id);

        try{
            String userJson = objectMapper.writeValueAsString(user);
            redisTemplate.opsForValue().set(key,userJson);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public User getUserById(String id){
        String key = generateUserKey(id);
        String userJson = redisTemplate.opsForValue().get(key);

        if(userJson == null){
            return null;
        }
        try{
            return objectMapper.readValue(userJson,User.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private String generateUserId(){
        Long id = redisTemplate.opsForValue().increment("user:id");
        return String.valueOf(id);
    }
    private String generateUserKey(String id){
        return "user:" + id;
    }
}