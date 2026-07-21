import axios from "axios";
import { BACKEND_URL } from '../components/config'; 
const api = axios.create({
  baseURL: BACKEND_URL + '/rest/',
  withCredentials: true,
  withXSRFToken: true,  
  xsrfCookieName: 'XSRF-TOKEN', 
  xsrfHeaderName: 'X-XSRF-TOKEN'
});

export const apiLogout = axios.create({
  baseURL: BACKEND_URL,
  withCredentials: true,
  withXSRFToken: true,  
  xsrfCookieName: 'XSRF-TOKEN', 
  xsrfHeaderName: 'X-XSRF-TOKEN'
});

export default api;