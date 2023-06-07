import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { Box, CardMedia, Typography } from "@material-ui/core";

import { fetchPost } from "../../PostActions";

export function PostDetailPage() {
  const { id } = useParams();
  const post = useSelector((state) =>
    state.posts.data.find((currentPost) => currentPost.id === id)
  );
  const dispatch = useDispatch();

  useEffect(() => {
    if (!post) dispatch(fetchPost(id));
    // eslint-disable-next-line
  }, []);

  return post ? (
    <div className="container">
      <Box sx={{ alignContent: "center", width: "100%", maxWidth: 1000 }}>
        <Typography sx={{ alignContent: "center" }} variant="h1" gutterBottom>
          {post.title}
          <Typography variant="h6" gutterBottom>
            {post.dateAddedFormatted}{" "}
          </Typography>
        </Typography>

        {post.imageUrl && (
          <Typography variant="h1" gutterBottom>
            <CardMedia
              component="img"
              sx={{ height: 140 }}
              image={`${post.imageUrl}`}
            />{" "}
          </Typography>
        )}
        <Typography variant="body1" gutterBottom>
          {post.content}
        </Typography>
        <Typography variant="overline" display="block" gutterBottom>
          From {post.name}
        </Typography>
      </Box>
    </div>
  ) : (
    <div>Loading</div>
  );
}
// return post ? (
//   <div className="container">
//     <div className="row">
//       <div className="col-12">
//         <h1>{post.title}</h1>
//         <p>By {post.name}</p>
//         {post.imageUrl && (
//           <img src={post.imageUrl} alt="Italian Trulli"></img>
//         )}
//         <p>{post.content}</p>
//       </div>
//     </div>
//   </div>
// ) : (
//   <div>Loading</div>
// );
// }
export default PostDetailPage;
