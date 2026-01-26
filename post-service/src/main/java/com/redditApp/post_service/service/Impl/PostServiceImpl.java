package com.redditApp.post_service.service.Impl;



import com.redditApp.post_service.auth.UserContextHolder;
import com.redditApp.post_service.dtos.CreatePostRequest;
import com.redditApp.post_service.dtos.PostResponse;
import com.redditApp.post_service.dtos.UpdatePostRequest;
import com.redditApp.post_service.entity.Post;
import com.redditApp.post_service.event.PostCreatedEvent;
import com.redditApp.post_service.exceptions.ResourceNotFoundException;
import com.redditApp.post_service.kafka.PostEventProducer;
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
    private final PostEventProducer producer;

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
                .score(0)
                .commentCount(0)
                .isDeleted(false)
                .build();


        Post saved = postRepository.save(post);
        producer.publishPostCreated(
                PostCreatedEvent.builder()
                        .postId(saved.getId())
                        .userId(saved.getUserId())
                        .subRedditId(saved.getSubredditId())
                        .createdAt(saved.getCreatedAt())
                        .build()
        );
        return mapToResponse(saved);
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
    @Transactional(readOnly = true)
    public Page<PostResponse> getMainFeed(Pageable pageable) {
        return postRepository
                .findByIsDeletedFalse(pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getSubredditFeed(Long subredditId, Pageable pageable) {
        return postRepository
                .findBySubredditIdAndIsDeletedFalse(subredditId, pageable)
                .map(this::mapToResponse);
    }


    @Override
    public Page<PostResponse> getUserPosts(Long userId, Pageable pageable) {
        return postRepository
                .findByUserIdAndIsDeletedFalse(userId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        Long currentUser = UserContextHolder.getCurrentUserId();

        Post post = postRepository
                .findByIdAndIsDeletedFalse(postId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Post not found with id "+currentUser));
        // only owner can update
        if(!post.getUserId().equals(currentUser)){
            throw new SecurityException("Forbidden");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        return mapToResponse(post);
    }

    @Override
    public void deletePost(Long postId) {

        Long currentUser = UserContextHolder.getCurrentUserId();

        Post post = postRepository
                .findByIdAndIsDeletedFalse(postId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Post not found"));
        // only owner can delete
        if(!post.getUserId().equals(currentUser)){
            throw new SecurityException("Forbidden");
        }
        post.setIsDeleted(true);
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
