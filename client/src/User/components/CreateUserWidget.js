import React, { useState } from "react";
import { useHistory } from "react-router-dom";

import PropTypes from "prop-types";
import { Button, TextField } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
  root: {
    "& > *": {
      margin: theme.spacing(1),
    },
  },
}));

const CreateUserWidget = ({ createUser }) => {
  const history = useHistory();
  const [state, setState] = useState({});
  const classes = useStyles();

  const submit = () => {
    if (state.username && state.password && state.email) {
      createUser(state);
    }
  };

  const returnToLogin = () => {
    history.push("/");
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
      <h3>Create User</h3>
      <TextField
        variant="filled"
        label="Username"
        name="username"
        onChange={handleChange}
      />
      <TextField
        variant="filled"
        label="Password"
        name="password"
        type="password"
        onChange={handleChange}
      />
      <TextField
        variant="filled"
        label="Email"
        name="email"
        onChange={handleChange}
      />
      <Button
        className="mt-4"
        variant="contained"
        color="primary"
        onClick={() => submit()}
        disabled={!state.username || !state.password}
      >
        Create
      </Button>
      <Button
        className="mt-4"
        variant="contained"
        color="primary"
        onClick={() => returnToLogin()}
      >
        Go to login
      </Button>
    </div>
  );
};

CreateUserWidget.propTypes = {
  createUser: PropTypes.func.isRequired,
};

export default CreateUserWidget;
