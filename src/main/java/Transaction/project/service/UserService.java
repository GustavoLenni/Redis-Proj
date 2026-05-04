package Transaction.project.service;


import Transaction.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public User createUser(User user){
        Long id = null;
        try {
            id = redisTemplate.opsForValue().increment("user:id");
            System.out.println("ID GERADO: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setId(String.valueOf(id));
        String key = "user:" + user.getId();

        redisTemplate.opsForHash().put(key,"id", user.getId());
        redisTemplate.opsForHash().put(key,"name", user.getName());
        redisTemplate.opsForHash().put(key,"password", user.getPassword());
        redisTemplate.opsForHash().put(key,"balance", String.valueOf(user.getBalance()));
        System.out.println("Criando usuário...");

        return user;
    }
}
