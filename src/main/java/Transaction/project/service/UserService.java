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

        saveUser(user);
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
    public String transfer(String fromId, String toId, Double amount){
        User sender = getUserById(fromId);
        User receiver = getUserById(toId);

        if(sender == null){
            return "User not found";
        }
        if(receiver == null){
            return "User not found";
        }
        if(sender.getBalance() < amount){
            return "Insufficient balance";
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        saveUser(sender);
        saveUser(receiver);
        return "Transaction completed successfully";
    }


    private void saveUser(User user){
        String key = generateUserKey(user.getId());

        try{
            String userJson = objectMapper.writeValueAsString(user);
            redisTemplate.opsForValue().set(key,userJson);
        }catch (Exception e){
            e.printStackTrace();
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