import api from "./api";
import { BACKEND_URL } from '../components/config'; 
export const AuthService = {
  async getLog(): Promise<boolean> {
    const { data } = await api.get<boolean>(`auth`); 
    return data;
  },
  
  logout: async () => {
    await fetch(BACKEND_URL + '/logout', {
      method: 'POST',
      credentials: 'include' 
    });
  }
};