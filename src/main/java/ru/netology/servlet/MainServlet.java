package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;

    private static final String postsPath = "/api/posts";
    private static final String get = "GET";
    private static final String post = "POST";
    private static final String delete = "DELETE";
    private static final String paramId = "\\d+";

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            // primitive routing
            if (method.equals(get) && path.equals(postsPath)) {
                controller.all(resp);
                return;
            }
            if (method.equals(get) && path.matches(postsPath + paramId)) {
                // easy way
                final var id = getId(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(post) && path.equals(postsPath)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(delete) && path.matches(postsPath + paramId)) {
                // easy way
                final var id = getId(path);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Long getId(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/")));
    }
}

