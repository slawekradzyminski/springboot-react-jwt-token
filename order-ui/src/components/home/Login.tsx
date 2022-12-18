import React, { useContext, useEffect, useState } from "react";
import { Redirect } from "react-router-dom";
import { Button, Form, Grid, Message, Segment } from "semantic-ui-react";
import AuthContext, { AuthProvider } from "../context/AuthContext";
import { orderApi } from "../misc/OrderApi";
import { handleLogError, parseJwt } from "../misc/Helpers";

export const Login = () => {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [isError, setIsError] = useState<boolean>(false);
  // @ts-ignore
  const authContext: AuthProvider = useContext(AuthContext);

  useEffect(() => {
    const isLoggedIn = authContext.userIsAuthenticated();
    setIsLoggedIn(isLoggedIn)
  }, []);

  // @ts-ignore
  const handleInputChange = (e, { name, value }) => {
    return name === 'username' ? setUsername(value) : setPassword(value)
  }

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!(username && password)) {
      setIsError(true)
      return;
    }

    orderApi
      .authenticate(username, password)
      .then((response) => {
        const { accessToken } = response.data;
        const data = parseJwt(accessToken);
        const user = { data, accessToken };

        authContext.userLogin(user);
        setUsername('')
        setPassword('')
        setIsLoggedIn(true)
        setIsError(false)
      })
      .catch((error) => {
        handleLogError(error);
        setIsError(true)
      });
  };

  const redirect = () => (
    <Redirect to={'/'} />
  )

  const main = () => (
    <Grid textAlign="center">
      <Grid.Column style={{ maxWidth: 450 }}>
        <Form size="large" onSubmit={handleSubmit}>
          <Segment>
            <Form.Input
              fluid
              autoFocus
              name="username"
              icon="user"
              iconPosition="left"
              placeholder="Username"
              // @ts-ignore
              onChange={handleInputChange}
            />
            <Form.Input
              fluid
              name="password"
              icon="lock"
              iconPosition="left"
              placeholder="Password"
              type="password"
              // @ts-ignore
              onChange={handleInputChange}
            />
            <Button color="violet" fluid size="large">
              Login
            </Button>
          </Segment>
        </Form>
        <Message>
          {`Don't have already an account? `}
          <a href="/signup" color="violet">
            Sign Up
          </a>
        </Message>
        {isError && (
          <Message negative>
            The username or password provided are incorrect!
          </Message>
        )}
      </Grid.Column>
    </Grid>
  );

  return <>
    {isLoggedIn ? redirect() : main()}
  </>
};

export default Login;
