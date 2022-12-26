import React, { useContext } from 'react'
import { Route, Redirect } from 'react-router-dom'
import AuthContext from '../components/context/AuthContext'

//@ts-ignore
const PrivateRoute = ({ component: Component, ...rest }) => {
  const authContext = useContext(AuthContext);

  return <Route {...rest} render={props => (
    authContext.userIsAuthenticated()
      ? <Component {...props} />
      : <Redirect to={{ pathname: '/login', state: { referer: props.location } }} />
  )} />
}

export default PrivateRoute