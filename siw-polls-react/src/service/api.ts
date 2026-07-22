import axios from "axios";
import { BACKEND_URL } from '../components/config'; 
const apiRest = axios.create({
  baseURL: BACKEND_URL + '/rest/',
  withCredentials: true,
  withXSRFToken: true,  
  xsrfCookieName: 'XSRF-TOKEN', 
  xsrfHeaderName: 'X-XSRF-TOKEN'
});

export const api = axios.create({
  baseURL: BACKEND_URL,
  withCredentials: true,
  withXSRFToken: true,  
  xsrfCookieName: 'XSRF-TOKEN', 
  xsrfHeaderName: 'X-XSRF-TOKEN'
});

export default apiRest;