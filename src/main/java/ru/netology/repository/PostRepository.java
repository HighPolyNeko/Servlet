package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Stub
public class PostRepository {
    private final Map<Long, Post> repository = new ConcurrentHashMap<>();

    public Map<Long, Post> all() {
        return repository;
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(repository.get(id));
    }

    public Post save(Post post) {
        repository.put(post.getId(), post);
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
