import React, { useContext, useEffect, useState } from 'react'
import { UserLocalStorage } from '../../types/user';

//@ts-ignore
const AuthContext = React.createContext<AuthContextProps>()

interface AuthContextProps {
  user: UserLocalStorage | null,
  userLogin: (user: UserLocalStorage) => void,
  userLogout: () => void,
  userIsAuthenticated: () => boolean,
  getUser: () => UserLocalStorage
}

const AuthProvider = (props: React.PropsWithChildren) => {
  const [user, setUser] = useState<UserLocalStorage | null>(null);

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user')!)
    setUser(user)
  }, [])

  const getUser = (): UserLocalStorage => JSON.parse(localStorage.getItem('user')!)

  const userIsAuthenticated = () => {
    let user = localStorage.getItem('user')
    if (!user) {
      return false
    }
    user = JSON.parse(user)
    
    // @ts-ignore
    if (Date.now() > user.data.exp * 1000) {
      userLogout()
      return false
    }
    return true
  }

  const userLogin = (user: UserLocalStorage) => {
    localStorage.setItem('user', JSON.stringify(user))
    setUser(user)
  }

  const userLogout = () => {
    localStorage.removeItem('user')
    setUser(null)
  }

  return <>
      <AuthContext.Provider value={{ user, getUser, userIsAuthenticated, userLogin, userLogout, }}>
        {props.children}
      </AuthContext.Provider>
  </>
};


export default AuthContext

export function useAuth() {
  return useContext(AuthContext)
}

export { AuthProvider }