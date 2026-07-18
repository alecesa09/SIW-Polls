import api from "./api";

export const AuthService = {
  async getLog(): Promise<boolean> {
    const { data } = await api.get<boolean>(`auth`); 
    return data;
  },
  logout: async () => {
    await api.post('/logout'); 
  }
};