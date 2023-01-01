import { CreateProduct } from "../types/product";
import { UserLocalStorage } from "../types/user";
import { axiosInstance, bearerAuth } from "./axiosConfig";

const getProducts = (user: UserLocalStorage) => {
  return axiosInstance.get("/api/products", {
    headers: { Authorization: bearerAuth(user) },
  });
};

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
  createProduct
};
