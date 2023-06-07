import React from "react";
import PropTypes from "prop-types";

// Import Components
import PostListItem from "./PostListItem";

function PostList(props) {
  return (
    <div className="d-flex flex-column w-100">
      <h3 className="mt-4">Posts</h3>
      {props.posts.map((post) => (
        <PostListItem
          post={post}
          key={post.slug.toString()}
          onDelete={() => props.handleDeletePost(post.id)}
          onDeleteImage={() => props.handleDeleteImagePost(post.id)}
          onUploadImage={(file) => props.handleUploadImagePost(post.id, file)}
        />
      ))}
    </div>
  );
}

PostList.propTypes = {
  posts: PropTypes.arrayOf(
    PropTypes.shape({
      name: PropTypes.string.isRequired,
      title: PropTypes.string.isRequired,
      content: PropTypes.string.isRequired,
      slug: PropTypes.string.isRequired,
      id: PropTypes.string.isRequired,
      dateAdded: PropTypes.string.isRequired,
      dateAddedFormatted: PropTypes.string.isRequired,
      imageUrl: PropTypes.string,
    })
  ).isRequired,
  handleDeletePost: PropTypes.func.isRequired,
  handleDeleteImagePost: PropTypes.func.isRequired,
  handleUploadImagePost: PropTypes.func.isRequired,
};

export default PostList;
