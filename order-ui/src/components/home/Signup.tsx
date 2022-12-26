import React, { ChangeEvent, useContext, useEffect, useState } from 'react'
import { Redirect } from 'react-router-dom'
import { Button, Form, Grid, Message, Segment } from 'semantic-ui-react'
import AuthContext from '../context/AuthContext'
import { orderApi } from '../../api/OrderApi'
import { handleLogError, parseJwt } from '../../util/Helpers'

const Signup = () => {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [name, setName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [isError, setIsError] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const authContext = useContext(AuthContext);

  useEffect(() => {
    const isLoggedIn = authContext.userIsAuthenticated();
    setIsLoggedIn(isLoggedIn)
  }, []);

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value
    switch (event.target.name) {
      case 'username':
        setUsername(value);
        break;
      case 'password':
        setPassword(value);
        break;
      case 'name':
        setName(value);
        break;
      case 'email':
        setEmail(value)
        break;
    }
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    if (!(username && password && name && email)) {
      setIsError(true)
      setErrorMessage('Please, fill all fields!')
      return
    }

    orderApi.signup({ username, password, name, email })
      .then(response => {
        const { accessToken } = response.data
        const data = parseJwt(accessToken)
        const user = { data, accessToken }

        authContext.userLogin(user)
        setUsername('')
        setPassword('')
        setIsLoggedIn(true)
        setIsError(false)
        setErrorMessage('')
      })
      .catch(error => {
        handleLogError(error)
        if (error.response && error.response.data) {
          const errorData = error.response.data
          let errorMessage = 'Invalid fields'
          if (errorData.status === 409) {
            errorMessage = errorData.message
          } else if (errorData.status === 400) {
            errorMessage = errorData.errors[0].defaultMessage
          }

          setIsError(true)
          setErrorMessage(errorMessage)
        }
      })
  };

  const redirect = () => (
    <Redirect to={'/'} />
  )

  const main = () => (
    <Grid textAlign='center'>
      <Grid.Column style={{ maxWidth: 450 }}>
        <Form size='large' onSubmit={handleSubmit}>
          <Segment>
            <Form.Input
              fluid
              autoFocus
              name='username'
              icon='user'
              iconPosition='left'
              placeholder='Username'
              onChange={handleInputChange}
            />
            <Form.Input
              fluid
              name='password'
              icon='lock'
              iconPosition='left'
              placeholder='Password'
              type='password'
              onChange={handleInputChange}
            />
            <Form.Input
              fluid
              name='name'
              icon='address card'
              iconPosition='left'
              placeholder='Name'
              onChange={handleInputChange}
            />
            <Form.Input
              fluid
              name='email'
              icon='at'
              iconPosition='left'
              placeholder='Email'
              onChange={handleInputChange}
            />
            <Button color='violet' fluid size='large'>Signup</Button>
          </Segment>
        </Form>
        <Message>{`Already have an account? `}
          <a href='/login' color='violet'>Login</a>
        </Message>
        {isError && <Message negative>{errorMessage}</Message>}
      </Grid.Column>
    </Grid>
  )

  return <>
    {isLoggedIn ? redirect() : main()}
  </>
};

export default Signup