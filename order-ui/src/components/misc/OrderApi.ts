import { OrderCreate } from '../../types/order'
import { User, UserLocalStorage } from '../../types/user'
import { axiosInstance } from './axiosConfig'

const json = { 'Content-type': 'application/json' }

const authenticate = (username: string, password: string) => {
  return axiosInstance.post('/auth/authenticate', { username, password }, {
    headers: json
  })
}

const signup = (user: User) => {
  return axiosInstance.post('/auth/signup', user, {
    headers: json
  })
}

const numberOfUsers = () => axiosInstance.get('/public/numberOfUsers')

const numberOfOrders = () => axiosInstance.get('/public/numberOfOrders')

const getUsers = (user: UserLocalStorage, username?: string) => {
  const url = username ? `/api/users/${username}` : '/api/users'
  return axiosInstance.get(url, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

const deleteUser = (user: UserLocalStorage, username: string) => {
  return axiosInstance.delete(`/api/users/${username}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

const getOrders = (user: UserLocalStorage, text?: string) => {
  const url = text ? `/api/orders?text=${text}` : '/api/orders'
  return axiosInstance.get(url, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

const deleteOrder = (user: UserLocalStorage, orderId: string) => {
  return axiosInstance.delete(`/api/orders/${orderId}`, {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

const createOrder = (user: UserLocalStorage, order: OrderCreate) => {
  return axiosInstance.post('/api/orders', order, {
    headers: {
      ...json,
      'Authorization': bearerAuth(user)
    }
  })
}

const getUserMe = (user: UserLocalStorage) => {
  return axiosInstance.get('/api/users/me', {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

export const orderApi = {
  authenticate,
  signup,
  numberOfUsers,
  numberOfOrders,
  getUsers,
  deleteUser,
  getOrders,
  deleteOrder,
  createOrder,
  getUserMe
}

const bearerAuth = (user: UserLocalStorage) => {
  return `Bearer ${user.accessToken}`
}