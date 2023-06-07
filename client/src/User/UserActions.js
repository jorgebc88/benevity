import callApi from "../util/apiCaller";

// Export Constants
export const ADD_USERS = "ADD_USERS";
export const DELETE_USERS = "DELETE_USERS";

// Export Actions
export function login(state) {
  return callApi("authenticate", "post", {
    username: state.userName,
    password: state.password,
  }).then((res) => {
    if (res.status) {
      return res;
    }
    localStorage.setItem("jwt_token", res.jwtToken);
    localStorage.setItem("isLogged", true);
    localStorage.setItem("username", state.userName);
    return res.jwtToken;
  });
}

export function createUser(newUser) {
  return callApi("users", "post", newUser).then((res) => {
    return res;
  });
}

export function addUsers(users) {
  return {
    type: ADD_USERS,
    users,
  };
}

export function fetchUsers() {
  return (dispatch) => {
    return callApi("users").then((res) => {
      dispatch(addUsers(res));
    });
  };
}

export function deleteUser(userId) {
  return {
    type: DELETE_USERS,
    userId,
  };
}

export function deleteUserRequest(userId) {
  return (dispatch) => {
    return callApi(`users/${userId}`, "delete").then(() => {
      dispatch(deleteUser(userId));
    });
  };
}
