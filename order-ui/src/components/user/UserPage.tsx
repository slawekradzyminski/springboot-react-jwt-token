import React, { ChangeEvent, useContext, useEffect, useState } from 'react'
import { Redirect } from 'react-router-dom'
import { Container } from 'semantic-ui-react'
import OrderTable from './OrderTable'
import AuthContext from '../context/AuthContext'
import { orderApi } from '../misc/OrderApi'
import { handleLogError } from '../misc/Helpers'
import { ApiUser } from '../../types/user'
import { OrderCreate } from '../../types/order'
import { Roles } from '../../types/roles'

export const UserPage = () => {
  const [userMe, setUserMe] = useState<ApiUser | null>(null);
  const [isUser, setIsUser] = useState<boolean>(true);
  const [orderDescription, setOrderDescription] = useState<string>('')
  const authContext = useContext(AuthContext);

  useEffect(() => {
    const user = authContext.getUser()
    const isUser = user.data.rol[0] === Roles.USER
    setIsUser(isUser)
    handleGetUserMe()
  }, [])

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value
    setOrderDescription(value);
  }

  const handleGetUserMe = () => {
    const getUserMe = async () => {
      const user = authContext.getUser()
      const response = await orderApi.getUserMe(user)
      setUserMe(response.data)
    }

    getUserMe()
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
      handleGetUserMe()
      setOrderDescription('')
    }

    createOrder()
      .catch(error => handleLogError(error))
  }

  const redirect = () => (
    <Redirect to='/' />
  )

  const main = () => (
    <Container>
      <OrderTable
        orders={userMe && userMe.orders}
        orderDescription={orderDescription}
        handleCreateOrder={handleCreateOrder}
        handleInputChange={handleInputChange}
      />
    </Container>
  )

  return <>
    {isUser ? main() : redirect()}
  </>
};

export default UserPage