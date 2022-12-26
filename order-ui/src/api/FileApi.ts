import { UserLocalStorage } from "../types/user";
import { axiosInstance, bearerAuth } from "./axiosConfig";

const upload = (
  user: UserLocalStorage,
  file: File,
  onUploadProgress: any
): Promise<any> => {
  const formData = new FormData();

  formData.append("file", file);

  return axiosInstance.post("/upload", formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
      'Authorization': bearerAuth(user),
    },
    onUploadProgress,
  });
};

const getFiles = (user: UserLocalStorage): Promise<any> => {
  return axiosInstance.get("/files", {
    headers: { Authorization: bearerAuth(user) },
  });
};

export const fileApi = {
  upload,
  getFiles,
};
