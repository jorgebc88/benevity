import React from "react";
import Logo from "../../../logo.svg";

const HomePage = () => {
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
          <h1 className="mt-4">Welcome to Alaya Blog</h1>
        </div>
      </div>
      <hr />
    </div>
  );
};

HomePage.propTypes = {};

export default HomePage;
