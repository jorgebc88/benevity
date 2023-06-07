import React from "react";
import PropTypes from "prop-types";
import { createTheme, ThemeProvider } from "@material-ui/core/styles";
import "./App.css";
import { Route, BrowserRouter, Switch } from "react-router-dom";
import CreateUserPage from "./User/pages/CreateUserPage/CreateUserPage";
import LoginPage from "./User/pages/Login/LoginPage";
import HomePage from "./User/pages/Home/HomePage";
import PostListPage from "./Post/pages/PostListPage/PostListPage";
import PostDetailPage from "./Post/pages/PostDetailPage/PostDetailPage";
import { Provider } from "react-redux";

import "bootstrap/dist/css/bootstrap.min.css";
import Navbar from "./Nav/components/Navbar";
import UsersPage from "./User/pages/UsersPage/UsersPage";

const theme = createTheme({
  palette: {
    primary: {
      main: "#1ecde2",
    },
  },
});

function App(props) {
  return (
    <ThemeProvider theme={theme}>
      <Provider store={props.store}>
        <div className="w-100">
          <Navbar />
          <div className="w-100 pt-5 mt-5">
            <BrowserRouter>
              <Switch>
                <Route path="/" exact component={HomePage} />
                <Route path="/login" exact component={LoginPage} />
                <Route path="/createUser" exact component={CreateUserPage} />
                <Route path="/users" exact component={UsersPage} />
                <Route path="/postslist" exact component={PostListPage} />
                <Route
                  path="/posts/:id/:slug"
                  exact
                  component={PostDetailPage}
                />
              </Switch>
            </BrowserRouter>
          </div>
        </div>
      </Provider>
    </ThemeProvider>
  );
}

App.propTypes = {
  store: PropTypes.object.isRequired,
};

export default App;
