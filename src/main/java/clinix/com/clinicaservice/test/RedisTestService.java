package clinix.com.clinicaservice.test;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTestService {
    private final StringRedisTemplate redisTemplate;

    public RedisTestService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String testRedisConnection() {
        try {
            redisTemplate.opsForValue().set("test_key", "Redis funcionando!");
            return redisTemplate.opsForValue().get("test_key");
        } catch (Exception e) {
            return "Erro ao conectar ao Redis: " + e.getMessage();
        }
    }
}
