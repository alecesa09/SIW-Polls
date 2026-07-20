import axios from 'axios';
import { BACKEND_URL } from '../components/config'; 
const apiClient = axios.create({
  baseURL:  BACKEND_URL , 
  withCredentials: true,            
  xsrfCookieName: 'XSRF-TOKEN',     
  xsrfHeaderName: 'X-XSRF-TOKEN'    
});
export default apiClient;