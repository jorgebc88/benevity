import React from "react";
import { useHistory } from "react-router-dom";
import Logo from "../../../logo.svg";
import LoginWidget from "../../components/LoginWidget";
import { login } from "../../UserActions";

const LoginPage = () => {
  const history = useHistory();

  const handleLogin = (username, password) => {
    login(username, password).then((resp) => {
      if (!resp.status) {
        history.go(-1);
      } else {
        alert("Wrong credentials!");
        history.push("/login");
      }
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
          <LoginWidget login={handleLogin} />
        </div>
      </div>
    </div>
  );
};

LoginPage.propTypes = {};

export default LoginPage;
