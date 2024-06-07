package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
@Repository
public class PostRepository {
    private final AtomicLong lastId = new AtomicLong(0);
    private final Map<Long, Post> repository = new ConcurrentHashMap<>();

    public Map<Long, Post> all() {
        return repository;
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(repository.get(id));
    }

    public Post save(Post post) {
        repository.put(lastId.getAndAdd(1), post);
        return post;
    }

    public void removeById(long id) {
        repository.remove(id);
    }

    public synchronized Post update(Post post) {
        if (repository.containsKey(post.getId())) {
            repository.put(post.getId(), post);
        } else {
            throw new IllegalArgumentException("Entity with id " + post.getId() + " does not exist");
        }
        return post;
    }
}
