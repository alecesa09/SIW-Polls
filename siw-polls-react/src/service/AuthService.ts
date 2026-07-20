import api from "./api";
import type { Utente } from "../types";
export const AuthService = {
  async getLog(): Promise<Utente> {
    const { data } = await api.get<Utente>(`auth`); 
    return data;
  },
};