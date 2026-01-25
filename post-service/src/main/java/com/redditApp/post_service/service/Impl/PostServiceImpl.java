package com.redditApp.post_service.service.Impl;



import com.redditApp.post_service.auth.UserContextHolder;
import com.redditApp.post_service.dtos.CreatePostRequest;
import com.redditApp.post_service.dtos.PostResponse;
import com.redditApp.post_service.dtos.UpdatePostRequest;
import com.redditApp.post_service.entity.Post;
import com.redditApp.post_service.exceptions.ResourceNotFoundException;
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

    //    CREATE

    @Override
    public PostResponse createPost(CreatePostRequest request) {
        Long userId = UserContextHolder.getCurrentUserId();

        if(userId == null){
            throw new SecurityException("Unauthorized");
        }
        Post post = Post.builder()
                .userId(userId)
                .subredditId(request.getSubRedditId())
                .title(request.getTitle())
                .content(request.getContent())
                .type(request.getType())
                .build();

        Post savedPost = postRepository.save(post);
        return mapToResponse(savedPost);
    }

    //       READ

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        Post post = postRepository
                .findByIdAndIsDeletedFalse(postId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Post not found with id: "+postId));

        return mapToResponse(post);
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
