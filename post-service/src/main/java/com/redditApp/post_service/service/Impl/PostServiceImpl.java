package com.redditApp.post_service.service.Impl;



import com.redditApp.post_service.dtos.CreatePostRequest;
import com.redditApp.post_service.dtos.PostResponse;
import com.redditApp.post_service.dtos.UpdatePostRequest;
import com.redditApp.post_service.entity.Post;
import com.redditApp.post_service.repository.PostRepository;
import com.redditApp.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostResponse createPost(CreatePostRequest request) {
        return null;
    }

    @Override
    public PostResponse getPostById(Long postId) {
        return null;
    }

    @Override
    public Page<PostResponse> getMainFeed(Pageable pageable) {
        return null;
    }

    @Override
    public Page<PostResponse> getSubredditFeed(Long subredditId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<PostResponse> getUserPosts(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }

    // HELPER
    private PostResponse mapToResponse(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .subredditId(post.getSubredditId())
                .title(post.getTitle())
                .content(post.getContent())
                .type(post.getType())
                .score(post.getScore())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
