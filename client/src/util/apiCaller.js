import fetch from "isomorphic-fetch";

export const API_URL = "http://localhost:8080";

export default async (
  endpoint,
  method = "get",
  body,
  contentType = "application/json"
) => {
  var headers = {};

  switch (contentType) {
    case "application/json":
      body = JSON.stringify(body);
      headers = {
        "content-type": contentType,
      };
      break;
    case "multipart/form-data":
      let formData = new FormData();
      formData.append("file", body);
      body = formData;
      break;
    default:
  }

  const jwtToken = localStorage.getItem("jwt_token");
  if (jwtToken) {
    headers.Authorization = `Bearer ${jwtToken}`;
  }

  return fetch(`${API_URL}/${endpoint}`, {
    headers,
    method,
    body: body,
  })
    .then((response) => {
      return response.json().then((json) => ({ json, response }));
    })
    .then(({ json, response }) => {
      if (!response.ok) {
        return Promise.reject(json);
      }
      return json;
    })
    .then(
      (response) => {
        return response;
      },
      (error) => {
        return error;
      }
    );
};
