import api from "./api";
import type { Sondaggio } from "../types";
async function getSondaggio(id: string): Promise<Sondaggio> {
    const { data } = await api.get<Sondaggio>(`/sondaggio/${id}`);
    return data;
}

export default getSondaggio;