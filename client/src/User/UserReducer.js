import { ADD_USERS, DELETE_USERS } from "./UserActions";

// Initial State
const initialState = { data: [] };

const UserReducer = (state = initialState, action) => {
  switch (action.type) {
    case ADD_USERS:
      return {
        data: action.users,
      };
    case DELETE_USERS:
      return {
        data: state.data.filter((user) => user.id !== action.userId),
      };
    default:
      return state;
  }
};

// Export Reducer
export default UserReducer;
