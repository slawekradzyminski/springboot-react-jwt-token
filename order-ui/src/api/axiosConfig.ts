import axios from "axios";
import { config } from "../Constants";
import { UserLocalStorage } from "../types/user";
import { parseJwt } from "../util/Helpers";

export const axiosInstance = axios.create({
  baseURL: config.url.API_BASE_URL,
});

axiosInstance.interceptors.request.use(
  (config) => {
    if (config.headers!.Authorization) {
      // @ts-ignore
      const token = config.headers!.Authorization.split(" ")[1];
      const data = parseJwt(token);
      if (Date.now() > data.exp * 1000) {
        window.location.href = "/login";
      }
    }
    return config
  },
  (error) => Promise.reject(error)
);

export const bearerAuth = (user: UserLocalStorage) => {
  return `Bearer ${user.accessToken}`
}