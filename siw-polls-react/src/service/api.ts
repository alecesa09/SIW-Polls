import axios from "axios";
import { BACKEND_URL } from '../components/config'; 

const apiRest = axios.create({
  baseURL: BACKEND_URL + '/rest/',
  withCredentials: true
});

export const api = axios.create({
  baseURL: BACKEND_URL,
  withCredentials: true
});

export default apiRest;