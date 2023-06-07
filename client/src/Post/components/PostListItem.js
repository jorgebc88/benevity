import React from "react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";
import {
  Card,
  CardContent,
  CardMedia,
  CardHeader,
  Typography,
  CardActions,
  Button,
} from "@material-ui/core";

function PostListItem({ post, key, onDelete, onDeleteImage, onUploadImage }) {
  var userNameLogged = localStorage.getItem("username");
  return (
    <Card className="w-100 my-4">
      <CardHeader
        title={<Link to={`/posts/${post.id}/${post.slug}`}>{post.title}</Link>}
        subheader={`${post.dateAddedFormatted}`}
      />
      {post.imageUrl && (
        <CardMedia
          component="img"
          sx={{ height: 140 }}
          image={`${post.imageUrl}`}
        />
      )}

      <CardContent>
        <Typography component="p" className="mt-3">
          {post.content}
        </Typography>
        <Typography
          color="textSecondary"
          component="p"
          className="mt-3 font-italic"
        >
          From {post.name}
        </Typography>
      </CardContent>
      {userNameLogged === post.user.username && (
        <CardActions>
          <Button size="small" color="secondary" onClick={onDelete}>
            Delete post
          </Button>
        </CardActions>
      )}
      {userNameLogged === post.user.username && post.imageUrl && (
        <CardActions>
          <Button size="small" color="secondary" onClick={onDeleteImage}>
            Delete image
          </Button>
        </CardActions>
      )}
      {userNameLogged === post.user.username && !post.imageUrl && (
        <CardActions>
          <input
            accept="image/*"
            type="file"
            id="select-image"
            style={{ display: "none" }}
            onChange={(e) => {
              onUploadImage(e.target.files[0]);
            }}
          />
          <label htmlFor="select-image">
            <Button
              size="small"
              variant="contained"
              color="primary"
              component="span"
            >
              Upload Image
            </Button>
          </label>
        </CardActions>
      )}
    </Card>
  );
}

PostListItem.propTypes = {
  post: PropTypes.shape({
    name: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    content: PropTypes.string.isRequired,
    slug: PropTypes.string.isRequired,
    id: PropTypes.string.isRequired,
    dateAdded: PropTypes.string.isRequired,
    dateAddedFormatted: PropTypes.string.isRequired,
    imageUrl: PropTypes.string,
    user: PropTypes.shape({ username: PropTypes.string.isRequired }),
  }).isRequired,
  key: PropTypes.string.isRequired,
  onDelete: PropTypes.func.isRequired,
  onDeleteImage: PropTypes.func.isRequired,
  onUploadImage: PropTypes.func.isRequired,
};

export default PostListItem;
