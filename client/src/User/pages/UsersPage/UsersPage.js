import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import Logo from "../../../logo.svg";
import {
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@material-ui/core";

import { fetchUsers, deleteUserRequest } from "../../UserActions";

const UsersPage = () => {
  const dispatch = useDispatch();
  const users = useSelector((state) => state.users.data);
  const userNameLogged = localStorage.getItem("username");

  useEffect(() => {
    dispatch(fetchUsers());
    // eslint-disable-next-line
  }, []);

  const handleDeleteUser = (userId) => {
    // eslint-disable-next-line
    if (confirm("Do you want to delete this user")) {
      dispatch(deleteUserRequest(userId));
    }
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

      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>User id</TableCell>
              <TableCell align="right">Username</TableCell>
              <TableCell align="right">Email</TableCell>
              <TableCell align="right">Posts quantity</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {users.map((row) => (
              <TableRow
                key={row.name}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {row.id}
                </TableCell>
                <TableCell align="right">{row.username}</TableCell>
                <TableCell align="right">{row.email}</TableCell>
                <TableCell align="right">{row.postsPosted}</TableCell>
                <TableCell align="right">
                  {userNameLogged !== row.username && row.postsPosted === 0 && (
                    <Button
                      align="right"
                      variant="contained"
                      color="secondary"
                      onClick={() => handleDeleteUser(row.id)}
                    >
                      Delete
                    </Button>
                  )}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

UsersPage.propTypes = {};

export default UsersPage;
