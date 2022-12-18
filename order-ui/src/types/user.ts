import { Roles } from "./roles";

export type User = {
  username: string;
  password: string;
  name: string;
  email: string;
};

export type JwtDetails = {
  aud: string;
  email: string;
  exp: Date;
  iat: Date;
  iss: string;
  jti: string;
  name: string;
  preferred_username: string;
  rol: Roles[];
  sub: string;
};

export type UserLocalStorage = {
    accessToken: string,
    data: JwtDetails
};
