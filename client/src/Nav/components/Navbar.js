import React from "react";
import Box from "@material-ui/core/Box";
import { AppBar, Link, Toolbar, Tooltip, Typography } from "@material-ui/core";

function Navbar() {
  var isLogged = localStorage.getItem("isLogged");
  var userlogged = localStorage.getItem("username");
  const handleChange = (event) => {
    isLogged = "false";
    localStorage.setItem("isLogged", isLogged);
    localStorage.removeItem("jwt_token");
    localStorage.removeItem("username");
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar variant="regular">
          <Typography
            variant="h5"
            component="div"
            sx={{ width: 1000, flexGrow: 1, borderColor: "error.main" }}
          >
            <Link href="/" className="text-white">
              Home
            </Link>
          </Typography>
          <hr />

          {isLogged !== "true" && (
            <Typography
              variant="h5"
              component="div"
              width={100}
              // sx={{ flexGrow: 1 }}
            >
              <Link href="/login" className="text-white">
                Login
              </Link>
            </Typography>
          )}
          <hr />

          <Typography
            variant="h5"
            component="div"
            width={100}
            // sx={{ flexGrow: 1 }}
          >
            <Link href="/createUser" className="text-white">
              Create User
            </Link>
          </Typography>
          <hr />

          {isLogged === "true" && (
            <Typography variant="h5" component="div" sx={{ flexGrow: 1 }}>
              <Link href="/users" className="text-white">
                Users
              </Link>
            </Typography>
          )}
          <hr />

          {isLogged === "true" && (
            <Typography variant="h5" component="div" sx={{ flexGrow: 1 }}>
              <Link href="/postslist" className="text-white">
                Posts
              </Link>
            </Typography>
          )}
          <hr />

          {isLogged === "true" && (
            <Tooltip
              title={<Typography fontSize={30}>{userlogged}</Typography>}
            >
              <Typography variant="h5" sx={{ flexGrow: 1 }}>
                <Link href="/" className="text-white" onClick={handleChange}>
                  Logout
                </Link>
              </Typography>
            </Tooltip>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}

export default Navbar;
