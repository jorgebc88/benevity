import callApi from "../util/apiCaller";

// Export Constants
export const ADD_POST = "ADD_POST";
export const ADD_POSTS = "ADD_POSTS";
export const DELETE_POST = "DELETE_POST";

// Export Actions
export function addPost(post) {
  return {
    type: ADD_POST,
    post,
  };
}

export function addPostRequest(post) {
  return (dispatch) => {
    return callApi("posts", "post", {
      name: post.name,
      title: post.title,
      content: post.content,
    }).then((res) => dispatch(addPost(res)));
  };
}

export function addPosts(posts) {
  return {
    type: ADD_POSTS,
    posts,
  };
}

export function fetchPosts() {
  return (dispatch) => {
    return callApi("posts").then((res) => {
      dispatch(addPosts(res));
    });
  };
}

export function fetchPost(id) {
  return (dispatch) => {
    return callApi(`posts/${id}`).then((res) => dispatch(addPost(res)));
  };
}

export function deletePost(id) {
  return {
    type: DELETE_POST,
    id,
  };
}

export function deletePostRequest(id) {
  return (dispatch) => {
    return callApi(`posts/${id}`, "delete").then(() =>
      dispatch(deletePost(id))
    );
  };
}

export function deleteImagePost(id) {
  return {
    type: DELETE_POST,
    id,
  };
}

export function deleteImagePostRequest(id) {
  return (dispatch) => {
    return callApi(`posts/${id}/image`, "delete").then(() =>
      dispatch(fetchPosts())
    );
  };
}

export function uploadImagePostRequest(id, image) {
  return (dispatch) => {
    return callApi(
      `posts/${id}/image`,
      "post",
      image,
      "multipart/form-data"
    ).then(() => dispatch(fetchPosts()));
  };
}
