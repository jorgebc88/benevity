import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
// Import Components
import PostList from "../../components/PostList";
import PostCreateWidget from "../../components/PostCreateWidget";
// Import Actions
import {
  addPostRequest,
  deletePostRequest,
  deleteImagePostRequest,
  uploadImagePostRequest,
  fetchPosts,
} from "../../PostActions";
import Logo from "../../../logo.svg";

const PostListPage = () => {
  const dispatch = useDispatch();
  const posts = useSelector((state) => state.posts.data);

  useEffect(() => {
    dispatch(fetchPosts());
    // eslint-disable-next-line
  }, []);

  const handleDeletePost = (post) => {
    // eslint-disable-next-line
    if (confirm("Do you want to delete this post")) {
      dispatch(deletePostRequest(post));
    }
  };

  const handleDeleteImagePost = (id) => {
    // eslint-disable-next-line
    if (confirm("Do you want to delete this image")) {
      dispatch(deleteImagePostRequest(id));
    }
  };

  const handleUploadImagePost = (id, image) => {
    // eslint-disable-next-line
    dispatch(uploadImagePostRequest(id, image));
  };

  const handleAddPost = (post) => {
    dispatch(addPostRequest(post));
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-12 d-flex align-items-center">
          <img
            className="mx-3"
            src={Logo}
            alt="Logo"
            style={{ height: "72px" }}
          />
          <h1 className="mt-4">Alaya Blog</h1>
        </div>
      </div>
      <hr />
      <div className="row">
        <div className="col-6">
          <PostCreateWidget addPost={handleAddPost} />
        </div>
        <div className="col-6">
          <PostList
            handleDeletePost={handleDeletePost}
            handleDeleteImagePost={handleDeleteImagePost}
            handleUploadImagePost={handleUploadImagePost}
            posts={posts}
          />
        </div>
      </div>
    </div>
  );
};

PostListPage.propTypes = {};

export default PostListPage;
