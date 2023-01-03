import { CreateProduct } from "../types/product";
import { UserLocalStorage } from "../types/user";
import { axiosInstance, bearerAuth } from "./axiosConfig";

const getProducts = (user: UserLocalStorage) => {
  return axiosInstance.get("/api/products", {
    headers: { Authorization: bearerAuth(user) },
  });
};

const getProduct = (user: UserLocalStorage, id: string) => {
  return axiosInstance.get(`/api/products/${id}`, {
    headers: { Authorization: bearerAuth(user) },
  });
}

const editProduct = (user: UserLocalStorage, id: string, createProduct: CreateProduct) => {
  return axiosInstance.put(`/api/products/${id}`, createProduct, {
    headers: { 
      Authorization: bearerAuth(user),
      'Content-type': 'application/json'
    },
  });
}

const createProduct = (
  user: UserLocalStorage,
  createProduct: CreateProduct
) => {
  return axiosInstance.post("/api/products", createProduct, {
    headers: { 
      Authorization: bearerAuth(user),
      'Content-type': 'application/json'
    },
  });
};

export const productApi = {
  getProducts,
  createProduct,
  getProduct,
  editProduct
};
