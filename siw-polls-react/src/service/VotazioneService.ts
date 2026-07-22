import apiRest from "./api";
import type { Votazione,SondaggioDTO} from "../types";

export async function postVotazione(votazione: Votazione): Promise<string> {
    const { data } = await apiRest.post<string>('/sondaggio/votazione', votazione);
    return data;
}

export async function putVotazione(votazione: Votazione): Promise<string> {
    const { data } = await apiRest.put<string>('/sondaggio/votazione', votazione);
    return data;
}

export async function controllaPartecipazione(id: string): Promise<boolean> {
    const { data } = await apiRest.get<boolean>(`/sondaggio/partecipazione/${id}`);
    return data;
}

export async function ricercaSondaggiVotatiUtente():Promise<SondaggioDTO[]>{
    const { data } = await apiRest.get<SondaggioDTO[]>(`/sondaggi/votati/utente`);
    return data;
}

export async function getVotazioneUtente(idSondaggio:string):Promise<Votazione[]>{
    const { data } = await apiRest.get<Votazione[]>(`/sondaggio/votazione/${idSondaggio}`);
    return data;
}

export async function eliminaVotazione(idSondaggio:string):Promise<Votazione[]>{
    const { data } = await apiRest.delete<Votazione[]>(`/sondaggio/votazione/${idSondaggio}`);
    return data;
}