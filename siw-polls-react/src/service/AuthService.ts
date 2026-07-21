import api, { apiLogout } from "./api"; // Importiamo il default (api) e l'istanza nominata (apiLogout)
import type { Utente } from "../types";

export const AuthService = {
  async getLog(): Promise<Utente> {
    const { data } = await api.get<Utente>(`auth`); // Questa andrà su BACKEND_URL/rest/auth
    return data;
  },
};

export const Logout = {
  async Logout(): Promise<string> {
    const { data } = await apiLogout.post<string>(`logout`); 
    return data;
  },
};