import React, { ChangeEvent, useContext, useEffect, useState } from 'react'
import { Redirect } from 'react-router-dom'
import { Container } from 'semantic-ui-react'
import AuthContext from '../context/AuthContext'
import { orderApi } from '../misc/OrderApi'
import AdminTab from './AdminTab'
import { handleLogError } from '../misc/Helpers'
import { ApiUser } from '../../types/user'
import { Order } from '../../types/order'
import { Roles } from '../../types/roles'

export const AdminPage = () => {
  const [users, setUsers] = useState<ApiUser[]>([]);
  const [orders, setOrders] = useState<Order[]>([]);
  const [orderDescription, setOrderDescription] = useState<string>('')
  const [orderTextSearch, setOrderTextSearch] = useState<string>('')
  const [userUsernameSearch, setUsernameSearch] = useState<string>('')
  const [isAdmin, setIsAdmin] = useState<boolean>(true)
  const [isUsersLoading, setIsUsersLoading] = useState<boolean>(false)
  const [isOrdersLoading, setIsOrdersLoading] = useState<boolean>(false)
  const authContext = useContext(AuthContext);

  useEffect(() => {
    const user = authContext.getUser()
    const isAdmin = user.data.rol[0] === Roles.ADMIN
    setIsAdmin(isAdmin)
    handleGetUsers()
    handleGetOrders()
  }, [])

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value
    switch (event.target.name) {
      case 'userUsernameSearch':
        setUsernameSearch(value);
        break;
      case 'orderDescription':
        setOrderDescription(value);
        break;
      case 'orderTextSearch':
        setOrderTextSearch(value)
        break;
    }
  };

  const handleGetUsers = () => {
    const user = authContext.getUser()

    setIsUsersLoading(true)
    orderApi.getUsers(user)
      .then(response => {
        setUsers(response.data)
      })
      .catch(error => {
        handleLogError(error)
      })
      .finally(() => {
        setIsUsersLoading(false)
      })
  }

  const handleDeleteUser = (username: string) => {
    const user = authContext.getUser()

    orderApi.deleteUser(user, username)
      .then(() => {
        handleGetUsers()
      })
      .catch(error => {
        handleLogError(error)
      })
  }

  const handleSearchUser = () => {
    const user = authContext.getUser()

    const username = userUsernameSearch
    orderApi.getUsers(user, username)
      .then(response => {
        const data = response.data
        const users = data instanceof Array ? data : [data]
        setUsers(users)
      })
      .catch(error => {
        handleLogError(error)
        setUsers([])
      })
  }

  const handleGetOrders = () => {
    const user = authContext.getUser()

    setIsOrdersLoading(true)
    orderApi.getOrders(user)
      .then(response => {
        setOrders(response.data)
      })
      .catch(error => {
        handleLogError(error)
      })
      .finally(() => {
        setIsOrdersLoading(false)
      })
  }

  const handleDeleteOrder = (isbn: string) => {
    const user = authContext.getUser()

    orderApi.deleteOrder(user, isbn)
      .then(() => {
        handleGetOrders()
      })
      .catch(error => {
        handleLogError(error)
      })
  }

  const handleCreateOrder = () => {
    const user = authContext.getUser()

    const parsedOrderDescription = orderDescription.trim()
    if (!parsedOrderDescription) {
      return
    }

    const order = { description: parsedOrderDescription }
    orderApi.createOrder(user, order)
      .then(() => {
        handleGetOrders()
        setOrderDescription('')
      })
      .catch(error => {
        handleLogError(error)
      })
  }

  const handleSearchOrder = () => {
    const user = authContext.getUser()

    orderApi.getOrders(user, orderTextSearch)
      .then(response => {
        setOrders(response.data)
      })
      .catch(error => {
        handleLogError(error)
        setOrders([])
      })
  }

  const redirect = () => (
    <Redirect to='/' />
  )

  const main = () => (
    <Container>
      <AdminTab
        isUsersLoading={isUsersLoading}
        users={users}
        userUsernameSearch={userUsernameSearch}
        handleDeleteUser={handleDeleteUser}
        handleSearchUser={handleSearchUser}
        isOrdersLoading={isOrdersLoading}
        orders={orders}
        orderDescription={orderDescription}
        orderTextSearch={orderTextSearch}
        handleCreateOrder={handleCreateOrder}
        handleDeleteOrder={handleDeleteOrder}
        handleSearchOrder={handleSearchOrder}
        handleInputChange={handleInputChange}
      />
    </Container>
  )

  return <>
    {isAdmin ? main() : redirect()}
  </>
};

export default AdminPage