package com.nguyentronghao.realtime_restapi_blogapp.service.implement;

import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Category;
import com.nguyentronghao.realtime_restapi_blogapp.model.error.ResourceNotFoundException;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.post.PostDto;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.post.PostResponse;
import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Post;
import com.nguyentronghao.realtime_restapi_blogapp.repository.CategoryRepository;
import com.nguyentronghao.realtime_restapi_blogapp.repository.PostRepository;
import com.nguyentronghao.realtime_restapi_blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }
    
    /**
     * Creates a new post based on the provided PostDto.
     *
     * @param postDto The PostDto containing the information of the new post
     * @return The DTO representation of the newly created post
     * @throws ResourceNotFoundException if the post with category id is not found
     */
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = convertPostDtoToPost(postDto);
        post.setCategory(findCategoryByCategoryId(postDto.getCategoryId()));
        return convertPostToPostDto(postRepository.save(post));
    }
    
    /**
     * Retrieves a paginated list of posts from the repository.
     *
     * @param pageNum   The page number (1-based) to retrieve
     * @param pageSize  The maximum number of posts per page
     * @param pageOrderBy   The field to sort the results by
     * @param direction The direction of sorting (ASC or DESC)
     * @return A PostResponse object containing a paginated list of posts
     */
    @Override
    public PostResponse getPageOfPosts(int pageNum, int pageSize, String pageOrderBy, String direction) {
        // Create a Pageable object to specify pagination parameters and sorting criteria
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, getSortFromRequest(pageOrderBy, direction));
        
        // Retrieve a page of posts from the repository
        Page<Post> posts = postRepository.findAll(pageable);
        
        // Convert the Page<Post> object to a PostResponse object and return
        return getPostResponseFromPageOfPosts(posts);
    }
    
    /**
     * Retrieves a post by its unique identifier.
     *
     * @param id The unique identifier of the post
     * @return The DTO representation of the post
     * @throws ResourceNotFoundException If the post with the specified ID is not found
     */
    @Override
    public PostDto getPostById(Long id) {
        Post post = getPostFromRepositoryById(id);
        return convertPostToPostDto(post);
    }
    
    /**
     * Updates a post with new data.
     *
     * @param postDto The DTO containing the updated post information
     * @param id The unique identifier of the post to update
     * @return The DTO representation of the updated post
     * @throws ResourceNotFoundException If the post with the specified ID is not found
     */
    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = getPostFromRepositoryById(id);
        return convertPostToPostDto(updatePostFields(post, postDto));
    }
    
    /**
     * Delete a post based on its unique identifier.
     *
     * @param id The unique identifier of the post to be deleted
     * @throws ResourceNotFoundException If the post with the specified ID is not found
     */
    @Override
    public void deletePost(Long id) {
        Post post = getPostFromRepositoryById(id);
        postRepository.delete(post);
    }
    
    /**
     * Retrieves all posts belonging to a category with the provided category ID.
     *
     * @param categoryId The ID of the category to retrieve posts for.
     * @return A list of PostDto objects representing all posts belonging to the category.
     * @throws ResourceNotFoundException if no Category is found with the provided categoryId.
     */
    @Override
    public List<PostDto> getAllPostsByCategoryId(Long categoryId) {
        return postRepository.findByCategoryId(findCategoryByCategoryId(categoryId).getId())
                .stream().map(
                        this::convertPostToPostDto
                ).toList();
    }
    
    /**
     * Searches for posts based on a keyword.
     * <p>
     * This method searches for posts containing the provided keyword in their content, title or category.
     *
     * @param keyword The keyword to search for.
     * @return A list of PostDto objects representing the posts that match the search criteria.
     */
    @Override
    public List<PostDto> searchPosts(String keyword) {
        return postRepository.searchPosts(keyword)
                .stream()
                .map(this::convertPostToPostDto)
                .toList();
    }
    
    /**
     * Retrieves a Category from the repository by its ID.
     *
     * @param categoryId The ID of the Category to retrieve.
     * @return The Category object corresponding to the provided ID if found; otherwise, throws a ResourceNotFoundException.
     * @throws ResourceNotFoundException if no Category is found with the provided ID.
     */
    private Category findCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId.toString()));
    }
    
    /**
     * Retrieves a post from the repository by its unique identifier.
     *
     * @param id The unique identifier of the post
     * @return The retrieved post
     * @throws ResourceNotFoundException If the post with the specified ID is not found
     */
    private Post getPostFromRepositoryById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
    }
    
    /**
     * Updates the fields of a post with the data from the provided DTO.
     *
     * @param post The post to update
     * @param postDto The DTO containing the updated post information
     * @return post
     */
    private Post updatePostFields(Post post, PostDto postDto) {
        post.setAuthor(postDto.getAuthor());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setCategory(findCategoryByCategoryId(postDto.getCategoryId()));
        
        return postRepository.save(post);
    }
    
    /**
     * Converts a list of posts to a PostResponse object.
     *
     * @param posts The list of posts to be converted
     * @return A PostResponse object containing the converted list of posts
     */
    private PostResponse convertPostListToPostResponse(List<Post> posts) {
        // Create a new PostResponse object
        PostResponse postResponse = new PostResponse();
        
        // Set the list of PostDto objects in the PostResponse
        postResponse.setPostList(
                posts.stream().map(this::convertPostToPostDto).toList()
        );
        return postResponse;
    }
    
    /**
     * Creates a PostResponse object from a Page of posts.
     *
     * @param page The Page object containing posts
     * @return A PostResponse object created from the Page of posts
     */
    private PostResponse getPostResponseFromPageOfPosts(Page<Post> page) {
        // Convert the Page<Post> object to a PostResponse object
        PostResponse postResponse = convertPostListToPostResponse(page.getContent());
        
        // Set pagination information in the PostResponse
        postResponse.setPageNum(page.getNumber() + 1);
        postResponse.setPageSize(page.getSize());
        postResponse.setTotalElements(page.getTotalElements());
        postResponse.setTotalPages(page.getTotalPages());
        postResponse.setHasNext(page.hasNext());
        return postResponse;
    }
    
    /**
     * Creates a Sort object based on the specified sorting criteria.
     *
     * @param pageOrderBy   The field to sort by
     * @param direction The direction of sorting (ASC or DESC)
     * @return A Sort object representing the specified sorting criteria
     */
    private Sort getSortFromRequest(String pageOrderBy, String direction) {
        return Sort.by(direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.Direction.ASC : Sort.Direction.DESC, pageOrderBy);
    }
    
    /**
     * Converts a PostDto data transfer object (DTO) to a Post entity.
     *
     * @param postDto The PostDto object to be converted
     * @return The corresponding Post entity object after conversion
     */
    private Post convertPostDtoToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
    
    /**
     * Converts a Post entity to a PostDto data transfer object (DTO).
     *
     * @param post The Post entity object to be converted
     * @return The corresponding PostDto object after conversion
     */
    private PostDto convertPostToPostDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
}
