import React, { ChangeEvent, useContext, useEffect, useState } from 'react'
import { Redirect } from 'react-router-dom'
import { Container } from 'semantic-ui-react'
import AuthContext from '../context/AuthContext'
import { orderApi } from '../../api/OrderApi'
import AdminTab from './AdminTab'
import { handleLogError } from '../../util/Helpers'
import { ApiUser } from '../../types/user'
import { Order, OrderCreate } from '../../types/order'
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
    setIsUsersLoading(true)
    const getUsers = async () => {
      const user = authContext.getUser()
      const usersResponse = await orderApi.getUsers(user)
      setUsers(usersResponse.data)
    }

    getUsers()
      .catch(error => handleLogError(error))
      .finally(() => setIsUsersLoading(false))
  }

  const handleDeleteUser = (username: string) => {
    const deleteUser = async () => {
      const user = authContext.getUser()
      await orderApi.deleteUser(user, username)
      handleGetUsers()
    }

    deleteUser()
      .catch(error => handleLogError(error))
  }

  const handleSearchUser = () => {
    const searchUser = async () => {
      const user = authContext.getUser()
      const searchResponse = await orderApi.getUsers(user, userUsernameSearch)
      const data = searchResponse.data
      const users = data instanceof Array ? data : [data]
      setUsers(users)
    }

    searchUser()
      .catch(error => {
        handleLogError(error)
        setUsers([])
      })
  }

  const handleGetOrders = () => {
    setIsOrdersLoading(true)
    const getOrders = async () => {
      const user = authContext.getUser()
      const ordersResponse = await orderApi.getOrders(user)
      setOrders(ordersResponse.data)
    }

    getOrders()
      .catch(error => handleLogError(error))
      .finally(() => setIsOrdersLoading(false))
  }

  const handleDeleteOrder = (isbn: string) => {
    const deleteOrder = async () => {
      const user = authContext.getUser()
      await orderApi.deleteOrder(user, isbn)
      handleGetOrders()
    }

    deleteOrder()
      .catch(error => handleLogError(error))
  }

  const handleCreateOrder = () => {
    const user = authContext.getUser()

    const parsedOrderDescription = orderDescription.trim()
    if (!parsedOrderDescription) {
      return
    }
    const order: OrderCreate = { description: parsedOrderDescription }

    const createOrder = async () => {
      await orderApi.createOrder(user, order)
      handleGetOrders()
      setOrderDescription('')
    }

    createOrder()
      .catch(error => handleLogError(error))
  }

  const handleSearchOrder = () => {
    const searchOrder = async () => {
      const user = authContext.getUser()
      const ordersResponse = await orderApi.getOrders(user, orderTextSearch)
      setOrders(ordersResponse.data)
    }

    searchOrder()
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