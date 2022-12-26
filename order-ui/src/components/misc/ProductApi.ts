import { UserLocalStorage } from '../../types/user'
import { axiosInstance, bearerAuth } from './axiosConfig'

// const json = { 'Content-type': 'application/json' }

const getProducts = (user: UserLocalStorage) => {
  return axiosInstance.get('/api/products', {
    headers: { 'Authorization': bearerAuth(user) }
  })
}

export const productApi = {
  getProducts
}
