import api from "./api";
import type { Sondaggio, Votazione} from "../types";
async function getSondaggio(id: string): Promise<Sondaggio> {
    const { data } = await api.get<Sondaggio>(`/sondaggio/${id}`);
    return data;
}
export async function postVoti(voti: Votazione): Promise<string> {
    const { data } = await api.post<string>('/sondaggio/voto', voti);
    return data;
}
export default getSondaggio;