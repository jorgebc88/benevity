import React from "react";
import PropTypes from "prop-types";
import { useHistory } from "react-router-dom";
import Logo from "../../../logo.svg";
import CreateUserWidget from "../../components/CreateUserWidget";
import { createUser } from "../../UserActions";

const CreateUserPage = ({ showCreateUser }) => {
  const history = useHistory();
  const handleCreateUser = (newUser) => {
    createUser(newUser).then((response) => {
      if (!response.status) {
        alert("User Created!");
      } else {
        alert("Something wrong with the user creation!");
      }
      history.push("/createUser");
    });
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
          <CreateUserWidget
            createUser={handleCreateUser}
            showCreateUser={showCreateUser}
          />
        </div>
      </div>
    </div>
  );
};

CreateUserPage.propTypes = {
  showCreateUser: PropTypes.bool,
};

export default CreateUserPage;
