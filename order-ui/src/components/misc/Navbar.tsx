import React, { useContext } from 'react'
import { Link } from 'react-router-dom'
import { Container, Menu } from 'semantic-ui-react'
import AuthContext from '../context/AuthContext'

const Navbar = () => {
  const authContext = useContext(AuthContext);

  const logout = () => {
    authContext.userLogout()
  }

  const enterMenuStyle = () => {
    return authContext.userIsAuthenticated() ? { "display": "none" } : { "display": "block" }
  }

  const logoutMenuStyle = () => {
    return authContext.userIsAuthenticated() ? { "display": "block" } : { "display": "none" }
  }

  const adminPageStyle = () => {
    const user = authContext.getUser()
    return user && user.data.rol[0] === 'ADMIN' ? { "display": "block" } : { "display": "none" }
  }

  const userPageStyle = () => {
    const user = authContext.getUser()
    return user && user.data.rol[0] === 'USER' ? { "display": "block" } : { "display": "none" }
  }

  const getUserName = () => {
    const user = authContext.getUser()
    return user ? user.data.name : ''
  }

  return (
    <Menu inverted color='violet' stackable size='massive' style={{borderRadius: 0}}>
      <Container>
        <Menu.Item header>Order-UI</Menu.Item>
        <Menu.Item as={Link} exact='true' to="/">Home</Menu.Item>
        <Menu.Item as={Link} to="/adminpage" style={adminPageStyle()}>AdminPage</Menu.Item>
        <Menu.Item as={Link} to="/userpage" style={userPageStyle()}>UserPage</Menu.Item>
        <Menu.Menu position='right'>
          <Menu.Item as={Link} to="/login" style={enterMenuStyle()}>Login</Menu.Item>
          <Menu.Item as={Link} to="/signup" style={enterMenuStyle()}>Sign Up</Menu.Item>
          <Menu.Item header style={logoutMenuStyle()}>{`Hi ${getUserName()}`}</Menu.Item>
          <Menu.Item as={Link} to="/" style={logoutMenuStyle()} onClick={logout}>Logout</Menu.Item>
        </Menu.Menu>
      </Container>
    </Menu>
  )
}

export default Navbar
