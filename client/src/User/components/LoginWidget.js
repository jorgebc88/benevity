import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import PropTypes from "prop-types";
import { Button, TextField } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";
// Import Style

const useStyles = makeStyles((theme) => ({
  root: {
    "& > *": {
      margin: theme.spacing(1),
    },
  },
}));

const LoginWidget = ({ login }) => {
  const history = useHistory();
  const [state, setState] = useState({});
  const classes = useStyles();

  const submit = () => {
    if (state.userName && state.password) {
      login(state);
    }
  };

  const goToCreatePage = () => {
    history.push("/createUser");
  };

  const handleChange = (evt) => {
    const value = evt.target.value;
    setState({
      ...state,
      [evt.target.name]: value,
    });
  };

  return (
    <div className={`${classes.root} d-flex flex-column my-4 w-100`}>
      <h3>Login</h3>
      <TextField
        variant="filled"
        label="Username"
        name="userName"
        onChange={handleChange}
      />
      <TextField
        variant="filled"
        label="Password"
        name="password"
        type="password"
        onChange={handleChange}
      />
      <Button
        className="mt-4"
        variant="contained"
        color="primary"
        onClick={() => submit()}
        disabled={!state.userName || !state.password}
      >
        Submit
      </Button>
      <Button
        className="mt-4"
        variant="contained"
        color="primary"
        onClick={() => goToCreatePage()}
      >
        Create User
      </Button>
    </div>
  );
};

LoginWidget.propTypes = {
  login: PropTypes.func.isRequired,
};

export default LoginWidget;
