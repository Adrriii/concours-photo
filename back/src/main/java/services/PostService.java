package services;

import dao.PostDao;

import javax.inject.Inject;

public class PostService {
    @Inject PostDao postDao;
}
