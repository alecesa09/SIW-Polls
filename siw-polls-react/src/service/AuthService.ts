import apiRest, { api } from "./api";
import type { Utente } from "../types";

export const AuthService = {
  async getLog(): Promise<Utente> {
    const { data } = await apiRest.get<Utente>(`auth`);
    return data;
  },
};

export const Logout = {
  async Logout(): Promise<string> {
    const { data } = await api.post<string>(`logout`); 
    return data;
  },
};